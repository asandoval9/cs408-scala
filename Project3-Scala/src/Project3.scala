object Project3 {
  def main(args : Array[String]) {
    
    val player1 = DiceHand();
    player1.roll
    println("Player 1:  " + player1) //Shows the hand
    val p1rank = player1.evalHand() //Rank of the hand
    println("\t" + HandRank(p1rank))
    
    val player2 = DiceHand();
    player2.roll
    println("Player 2:  " + player2)
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