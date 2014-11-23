import scala.io.StdIn.readLine

object Project3 {
  def main(args : Array[String]) {
    val player1 = new DiceHand();
    player1.roll
    println("Player 1:  " + player1)
    println("Choose Dice 1 - 5 to reroll, seperated by a space. Leave blank if none.")
    player1.reRoll(readLine)
    println("Player 1: " +  player1)
    val p1rank = player1.evalHand()
    println("\t" + HandRank(p1rank) + "\n" + ("-" * 50))
    
    val player2 = new DiceHand();
    player2.roll
    println("Player 2:  " + player2)
    println("Choose Dice 1 - 5 to reroll, seperated by a space. Leave blank if none.")
    player2.reRoll(readLine)
    println("Player 2: " +  player2)
    val p2rank = player2.evalHand()
    println("\t" + HandRank(p2rank))
    
    if(p1rank > p2rank)
      println("Winner: Player 1 wins with " + HandRank(p1rank))
    else if(p1rank == p2rank)
      println("Tie: Players tie with " + HandRank(p1rank))
    else
      println("Winner: Player 2 wins with " + HandRank(p2rank))
  }
}