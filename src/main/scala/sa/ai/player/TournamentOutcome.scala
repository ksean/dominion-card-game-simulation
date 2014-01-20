package sa.ai.player


case class TournamentOutcome(
  wins : Seq[Int])
{
  def winner : Int =
    wins.indexOf(wins.max)
  
  def victoryMargin : Double =
    wins.max.toDouble / wins.sum


  def plus(addend : TournamentOutcome) : TournamentOutcome = {
    assert(wins.size == addend.wins.size)
    
    val sum : Seq[Int] =
      wins.zip(addend.wins)
        .map((playerWins: (Int, Int)) => playerWins._1 + playerWins._2)
    
    TournamentOutcome(sum)
  }
}