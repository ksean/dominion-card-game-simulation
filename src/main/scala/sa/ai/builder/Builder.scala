package sa.ai.builder

import sa.ai.model._
import sa.ai.model.card._
import sa.ai.model.card.DiscardPile
import scala.util.Random
import sa.ai.model.card.DiscardPile
import sa.ai.model.card.Deck

/**
 * Date: 09/09/13
 * Time: 10:00 PM
 */
case class Builder(
  nextToAct : Int,
  players : Seq[Dominion],
  basic : Basic,
  kingdom : Kingdom,
  phase : Phase
  ) {
  def randomNextToAct : Int =
    scala.util.Random.nextInt(1)

  def randomPlayers : Seq[Dominion] =
    Seq(Dominion(DiscardPile(Seq()),Deck(Seq())))

  def randomBasic : Basic =
    Basic.initialSetForTwoPlayers

  def randomKingdom : Kingdom =
    Kingdom.firstGame

  def randomPhase : Phase = {
    val rand = new Random(System.currentTimeMillis())
    val phases = Seq(ActionPhase,BuyPhase,CleanupPhase)
    val randPhase = phases(rand.nextInt(phases.length))
    randPhase
  }
}
