package sa.ai.model.card

/**
 * 13/07/13 5:24 PM
 */
trait Pile {
  def cards : Seq[Card]
}

case class UniformPile(
    card : Card,
    size : Int
)
  extends Pile
{
  def cards =
    List.fill(size)(card)
}

object Pile {
  def apply(cardType : Card, pileSize : Int):Pile =
    UniformPile(cardType, pileSize)
}