object Project3 {
  def main(args : Array[String]) {
    val list = List(1,2,3,4,5,6,7,8,9,10)
    val updatedList = list map {_ * 3} filter {_ % 2 == 0}
    println(updatedList)
    println("Hello World!");
  }
}