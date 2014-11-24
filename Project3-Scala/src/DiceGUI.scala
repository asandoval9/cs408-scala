import scala.swing._
import scala.swing.event._
import javax.swing.ImageIcon
import java.awt.Color
import javax.swing.UIManager._
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel

class DiceGUI extends MainFrame {
  for (laf <- getInstalledLookAndFeels)
    if ("Metal".equals(laf.getName)) 
      setLookAndFeel(laf.getClassName)

  title = "Dice"
  preferredSize = new Dimension(450, 300)
  peer.setLocationRelativeTo(null)
  val p1Hand = new DiceHand
  val compHand = new DiceHand

  val rollBtn = new Button { text = "Roll!" }
  val reRollBtn = new Button { text = "Reroll Selected!"; enabled = false }
  val resultsLbl = new Label { text = "Results: " }

  val diceBtns = new Array[ToggleButton](5)
  val diceCompBtns = new Array[ToggleButton](5)
  for (i <- 0 until diceBtns.length) {
    diceBtns(i) = new ToggleButton {
      icon = new ImageIcon(DiceHand.diceMap(i + 1)_2)
      background = Color.BLUE
    }
    diceCompBtns(i) = new ToggleButton {
      icon = new ImageIcon(DiceHand.diceMap(6 - i)_2)
      background = Color.BLUE
    }
  }

  val rollBtnPanel = new BoxPanel(Orientation.Horizontal) {
    contents += Swing.HStrut(100)
    contents += rollBtn
    contents += Swing.HStrut(20)
    contents += reRollBtn
    contents += Swing.HStrut(100)
  }

  contents = new FlowPanel {
    contents += new Label("Computer")
    contents += new GridPanel(1, 5) { diceCompBtns.foreach { contents += _ } }
    contents += new Label("Player 1")
    contents += new GridPanel(1, 5) { diceBtns.foreach { contents += _ } }
    contents += rollBtnPanel
    contents += resultsLbl
  }

  listenTo(rollBtn, reRollBtn)

  reactions += {
    case ButtonClicked(comp) if comp == rollBtn =>
      resultsLbl.text = "Results: "
      p1Hand.roll
      updateDice(p1Hand, diceBtns)
      compHand.roll
      updateDice(compHand, diceCompBtns)
      rollBtn.enabled = !rollBtn.enabled
      reRollBtn.enabled = !rollBtn.enabled
    case ButtonClicked(comp) if comp == reRollBtn =>
      val selBtns = for { x <- 0 until diceBtns.length if (diceBtns(x).selected) } yield x + 1
      p1Hand.reRoll(selBtns.mkString(" "))
      updateDice(p1Hand, diceBtns)
      val p1Rank = p1Hand.evalHand()
      val compRank = compHand.evalHand()
      
      if (p1Rank > compRank)
        resultsLbl.text += "Winner: Player 1 wins with " + HandRank(p1Rank)
      else if (p1Rank == compRank)
        resultsLbl.text += "Tie: Players tie with " + HandRank(p1Rank)
      else
        resultsLbl.text += "Winner: Player 2 wins with " + HandRank(compRank)
      diceBtns.foreach { _.selected = false }
      rollBtn.enabled = !rollBtn.enabled
      reRollBtn.enabled = !rollBtn.enabled
  }

  def updateDice(dice: DiceHand, btns : Array[ToggleButton]): Unit = {
    val curHand = dice.hand
    for (i <- 0 until dice.hand.length)
      btns(i).icon = new ImageIcon(DiceHand.diceMap(curHand(i))_2)
  }
}
