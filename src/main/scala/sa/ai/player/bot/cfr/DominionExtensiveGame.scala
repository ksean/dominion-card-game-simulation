package sa.ai.player.bot.cfr

import ao.learn.mst.gen5.ExtensiveGame
import sa.ai.player.InfoSet
import sa.ai.model._
import sa.ai.rule._
import ao.learn.mst.gen5.node.{Decision, Terminal, ExtensiveNode}
import sa.ai.rule.OfficialRuleset
import sa.ai.rule.RandomShuffler
import sa.ai.player.InfoSet
import sa.ai.rule.OfficialRuleset
import sa.ai.rule.RandomShuffler
import sa.ai.player.InfoSet

/**
 *
 */
class DominionExtensiveGame(
    rules: Ruleset,
    val playerCount: Int,
    val initialState: Game)
    extends ExtensiveGame[Game, InfoSet, Move]
{
  override def node(state: Game): ExtensiveNode[InfoSet, Move] = {
    state.phase match {
      case BeforeTheGamePhase =>
        // StartTheGameAction is played by Chance
        ???

      case CleanupPhase =>
        // CleanupAction is played by Chance
        ???

      case AfterTheGamePhase =>
        val victoryPoints : Seq[Int] =
          (0 until playerCount).map(state.victoryPoint)

        Terminal(victoryPoints.map(_.toDouble))

      case BuyPhase =>
        Decision(
          state.nextToAct,
          state.nextInfoSet,
          rules.moves(state))
    }
  }


  override def transition(nonTerminal: Game, action: Move): Game =
    rules.transition(nonTerminal, action)
}


object DominionExtensiveGame
{
  val twoPlayerRestricted: DominionExtensiveGame =
    new DominionExtensiveGame(
      OfficialRuleset(RandomShuffler()),
      2,
      Game.twoPlayerRestrictedInitialState)
}