package stringOperations.operators

import stringOperations.operations._
import stringOperations.utils.Utils._


case class Positive(integerPart: String = "0", fractionalPart: String = "0") extends StringNumber {
  require(integerPart.forall{ p => p.isDigit } && fractionalPart.forall(_.isDigit))

  val n: String = integerPart.slice(0, integerPart.length - 1).dropWhile{d => d == '0' } + integerPart.last
  val m: String = fractionalPart

  override def *(that: StringNumber): StringNumber =
    that match {
      case Positive(i, f) => Positive(Mul(this.n, i))
      case Negative(i, f) => Negative(Mul(this.n, i))
    }


  override def +(that: StringNumber): StringNumber =
    that match {
      case Positive(i, f) => Positive(Addi(this.n, i), f)
      case Negative(i, f) =>
        if (isBigger(this, that)) Positive(Sub(this.n, i), f)
        else Negative(Sub(i, this.n), f)
    }


  override def -(that: StringNumber): StringNumber = {
    that match {
      case Positive(i, f) =>
        if(isBigger(this, that)) Positive(Sub(this.n, i), f)
        else Negative(Sub(i, this.n), f)
      case Negative(i, f) => Positive(Addi(this.n, i), f)
    }
  }


  override def %(that: StringNumber): StringNumber = {
    require(that.integerPart != "0")

    if (isBigger(this, that))
      that match {
        case Positive(i, f) => Positive(Mod(this.n, i), f)
        case Negative(i, f) => Negative(Mod(this.n, i), f)
      }
    else
      that match {
        case Negative(_, _) => Negative(this.n)
        case _ => this
      }
  }


  override def /(that: StringNumber): StringNumber = {
    require(that.integerPart != "0")

    that match {
      case Positive(i, f) => Positive(Div(this.n, i), f)
      case Negative(i, f) => Negative(Div(this.n, i), f)
    }
  }


  override def ++ : StringNumber = Positive(Inc(this.n), this.fractionalPart)


  override def -- : StringNumber =
    if(this.integerPart == "0") Negative("1", this.fractionalPart)
    else Positive(Dec(this.n), this.fractionalPart)


  override def ^(other: StringNumber): StringNumber = {
    require(other match { case Positive(_, _) => true; case _ => false})

    Positive(FastExp(this.n, other.integerPart), this.fractionalPart)
  }

  override def square: StringNumber = Positive(Sq(this.n))

  override def ==(other: StringNumber): Boolean =
    other match {
      case _: Positive => this.n == other.integerPart && this.m == other.fractionalPart
      case _: Negative => false

    }
}
