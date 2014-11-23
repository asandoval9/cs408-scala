import scala.swing._
import scala.swing.event._
import javax.swing.ImageIcon
import java.awt.Color

class DiceGUI extends MainFrame {
	title = "Dice"
	preferredSize = new Dimension(450,300)
	peer.setLocationRelativeTo(null)
	val diceGame = new DiceHand
	
	val rollBtn = new Button { text = "Roll!" }
	val reRollBtn = new Button { text = "Reroll Selected!"; enabled = false }
	val resultsLbl = new Label { text = "Results: "}
	
	val diceBtns = new Array[ToggleButton](5)
	for(i <- 0 until diceBtns.length) {
	  diceBtns(i) = new ToggleButton { 
	    icon = new ImageIcon(DiceHand.diceMap(i+1)_2)
	    background = Color.BLUE
	  }
	}
	
	val playerDiceGrid = new GridPanel(1,5) {
	  diceBtns.foreach { contents += _ }
	}
	
	contents = new FlowPanel {
	  contents += playerDiceGrid
	  contents += rollBtn
	  contents += reRollBtn
	  contents += resultsLbl
	}
	
	listenTo(rollBtn, reRollBtn)
	
	reactions += {
	  case ButtonClicked(comp) if comp == rollBtn =>
	    resultsLbl.text = "Results: "
	    diceGame.roll
	    val curHand = diceGame.hand
	    for(i <- 0 until diceBtns.length) {
	      diceBtns(i).icon = new ImageIcon(DiceHand.diceMap(curHand(i))_2)
	    }
	    reRollBtn.enabled = true;
	    rollBtn.enabled = false;
	  case ButtonClicked(comp) if comp == reRollBtn =>
	    val selBtns = for { x <- 0 until diceBtns.length if(diceBtns(x).selected) } yield x+1
	    diceGame.reRoll(selBtns.mkString(" "))
	    val curHand = diceGame.hand
	    for(i <- selBtns) {
	      diceBtns(i-1).icon = new ImageIcon(DiceHand.diceMap(curHand(i-1))_2)
	    }
	    reRollBtn.enabled = false;
	    rollBtn.enabled = true;
	    resultsLbl.text = "Results: " + HandRank(diceGame.evalHand())
	    diceBtns.foreach {_.selected = false}
	}
}
