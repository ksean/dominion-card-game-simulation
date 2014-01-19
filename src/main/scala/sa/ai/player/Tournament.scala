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
        if (a % 2 == 0) {
          Playout.playToCompletion(Game.twoPlayerRestrictedInitialState, ruleset, players).winners
        } else {
          val swappedPlayers : Seq[Player] =
            Seq(players(1), players(0))

          val swappedWinners : Set[Int] =
            Playout.playToCompletion(Game.twoPlayerRestrictedInitialState, ruleset, swappedPlayers).winners

          val nextWinners : Set[Int] =
            swappedWinners.map(p => if (p == 0) 1 else 0)

          nextWinners
        }

      assert(winners.size <= players.size)
      for (winner <- winners) {
        winTotal = winTotal.updated(winner, winTotal(winner) + 1)
      }
    }

    TournamentOutcome(
      winTotal)
  }
}
