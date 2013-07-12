package sa.ai.model

/**
 * Dominion player
 */
case class Player(
  id : Int,
  deck : List[Card],
  discard : List[Card],
  hand : List[Card]
)

object Player {

}