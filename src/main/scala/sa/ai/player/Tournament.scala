package sa.ai.player

import sa.ai.rule.Ruleset
import sa.ai.model.Game

/**
 *
 */
object Tournament
{
  def play(
    players  : Seq[Player],
    playouts : Int,
    ruleset  : Ruleset)
    : TournamentOutcome = {

    var winTotal : Seq[Int] =
      Seq.fill(players.size)(0)

    for(a <- 1 to playouts) {
      val winners : Set[Int] =
        Playout.playToCompletion(Game.twoPlayerRestrictedInitialState, ruleset, players).winners

      assert(winners.size <= players.size)
      for (winner <- winners) {
        winTotal = winTotal.updated(winner, winTotal(winner) + 1)
      }

    }
    TournamentOutcome(winTotal.indexOf(winTotal.max))

  }
}
