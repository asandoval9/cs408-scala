import scala.io.StdIn._

object Project3 {
  def main(args: Array[String]) {
    print("Select One\n1 : Dice GUI\n2 : Dice Game in Console\n")
    val selection = readInt

    if (selection == 2) {
      val player1 = new DiceHand();
      val p1rank = playerTurn(player1, "Player 1")
      println("\t" + HandRank(p1rank) + "\n" + ("-" * 50))

      val player2 = new DiceHand();
      val p2rank = playerTurn(player2, "Player 2")
      println("\t" + HandRank(p2rank))
      
      p1rank.compareTo(p2rank) match {
        case x if(x > 0)  => println("Winner: Player 1 wins with " + HandRank(p1rank))
        case 0 => println("Tie: Players tie with " + HandRank(p1rank))
        case _ => println("Winner: Player 2 wins with " + HandRank(p2rank))
      }
    } else if (selection == 1) {
      val diceUI = new DiceGUI
      diceUI.visible = true
    }
    
    def playerTurn(p : DiceHand, pName : String) : Int = {
      p.roll
      println(pName + ": " + p)
      println("Choose Dice 1 - 5 to reroll, seperated by a space. Leave blank if none.")
      p.reRoll(readLine)
      println(pName + ": " + p)
      p.evalHand() 
    }
  }
}