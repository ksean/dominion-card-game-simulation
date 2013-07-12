package sa.ai.model

/**
 * Dominion turn
 */
case class Turn(
  id : Int,
  owner : Player,
  cards : List[Card]
)

object Turn {

}