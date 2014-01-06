package sa.ai.player

import sa.ai.model.{AfterTheGamePhase, Game}
import sa.ai.rule.{Move, Ruleset, OfficialRuleset}
import scala.collection.mutable

/**
 * 
 */
case object Playout {
  def playToCompletion(game: Game, rules: Ruleset, players: Seq[Player]) : Outcome = {
    var moveCount : Int = 0

    val history : mutable.Buffer[Game] = mutable.Buffer()

    var nextGame : Game = game

    history.append(nextGame)

    while (nextGame.phase != AfterTheGamePhase) {
//      println(s"\nnextGame $moveCount: $nextGame")

      val nextPlayer : Player =
        players(nextGame.nextToAct)

      val nextInfo : InfoSet =
        nextGame.nextInfoSet

      val nextMoves : Set[Move] =
        rules.moves(nextGame)

//      println(s"nextMoves: $nextMoves")

      val nextMove : Move =
        nextPlayer.play(
          nextInfo, nextMoves)

//      println(s"nextMove: $nextMove")

      nextGame = rules.transition(nextGame, nextMove)

      moveCount += 1

      history.append(nextGame)
    }

    Outcome(history.toSeq)
  }
}
