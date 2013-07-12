package sa.ai

import sa.ai.model.Card

/**
 * Entry point
 */
object DominionMain extends App
{
  val firstGameCardNames =
    Card.FirstGame.map(_.toString)

  println(s"First game cards are: ${firstGameCardNames.mkString(", ")}")
}
