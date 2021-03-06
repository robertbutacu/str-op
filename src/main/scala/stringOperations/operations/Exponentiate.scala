package stringOperations.operations

import scala.annotation.tailrec

/**
  * Created by Robert-PC on 9/22/2017.
  */

private[stringOperations] object Exponentiate {
  def apply(x: String, y: String): String = {
    @tailrec
    def computeExponentiation(x: String, y: String, product: String): String = {
      y match {
        case "0" => "1"
        case "1" => Multiply(x, product)
        case _ => computeExponentiation(
          x,
          Subtract(y, "1"),
          Multiply(x, product)
        )
      }
    }

    computeExponentiation(x, y, "1")
  }
}
