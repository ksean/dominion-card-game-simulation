package sa.ai.rule

/**
 * 15/07/13 9:26 PM
 */
sealed class Move(val playerIndex : Int)

case class Shuffle(override val playerIndex : Int) extends Move(playerIndex)
case class Pickup(override val playerIndex : Int) extends Move(playerIndex)

