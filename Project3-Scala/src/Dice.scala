import java.util.Random
import scala.util.control.Breaks._

object HandRank extends Enumeration {
  val  Low, Pair, TwoPair, ThreeOfKind, Straight, FullHouse, FourOfKind, FiveOfKind = Value
}

case class DiceHand(hand : Array[Int] = new Array[Int](5)) {
  private val rand = new Random(System.currentTimeMillis())
  def roll : Unit = { 
    for(i <- 0 until hand.length)
      hand(i) = rand.nextInt(6) + 1
  }
  
  def evalHand() : Int = {
    val list = hand.toList.sortWith(_ < _)
    var pairCount = 0;
    var rank : Int = HandRank.Low.id
    
    breakable {
      if(list == (2 to 6) || list == (1 to 5)) {rank = HandRank.Straight.id; break }
      for(List(a,b,c,d,e) <- list.sliding(5) if(a==b&&b==c&&c==d&&d==e)) { rank = HandRank.FiveOfKind.id; break}
      for(List(a,b,c,d) <- list.sliding(4) if(a == b && b == c && c == d)) { rank = HandRank.FourOfKind.id; break}
      for(List(a,b,c) <- list.sliding(3) if(a == b && b == c)) {
        if(list(0) != list(1) || list(3) != list(4))
          rank = HandRank.ThreeOfKind.id
        else
          rank = HandRank.FullHouse.id
        break
      }
      for(List(l,r) <- list.sliding(2) if(l == r)) pairCount += 1;
      if(pairCount == 1)
        rank = HandRank.Pair.id
      else if(pairCount == 2)
        rank = HandRank.TwoPair.id
    }
    rank
  }
  override def toString() : String = {
    var s = ""
    for(i <- 0 until hand.length)
      s += hand(i) + " "
    s
  }
}