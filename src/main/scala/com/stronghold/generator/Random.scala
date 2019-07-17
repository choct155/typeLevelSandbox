package com.stronghold.generator

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import shapeless.{HList, HNil, ::, Lazy, Generic}

trait Random[A] {
  val generator: Gen[A]

  def generateOption: Option[A] = this.generator.sample
  def generateValue: A = this.genSomeValue(generator)
  def genSomeValue[A](g: Gen[A]): A = g.sample match {
    case None => genSomeValue(g)
    case Some(a) => a
  }
}

object Random {

  def random[A](implicit rand: Random[A]): A = rand.generateValue

  // TODO: Relying on existing generators in the Scalacheck library for the time being. May
  //  have to augment with custom types at some point

  // Initially, I failed to divine the point of createRandom. It is not for external use, but
  // rather deployment in the build up of random HLists within genericRandom
  implicit def createRandom[A](implicit arb: Arbitrary[A]): Random[A] = new Random[A] {
    val generator: Gen[A] = arb.arbitrary
  }

  // The main provider of a random instance of A will assume the ability to generate a random instance of
  // the HList representation of A (ARepr via rand). Whatever that random HList is will then be converted
  // to an instance of A by way of the gen
  implicit def genericRandom[A, ARepr](implicit gen: Generic.Aux[A, ARepr], rand: Lazy[Random[ARepr]]): Random[A] = {
    val someHList: ARepr = rand.value.generateValue
    val genA: Arbitrary[A] = Arbitrary(gen.from(someHList))
    createRandom[A](genA)
  }

  // We have assumed the ability to generate a random HList, but we need induction to pull this off. The
  // base case is creating the end of the list
  implicit val arbHNil: Arbitrary[HNil] = Arbitrary(Gen.const(HNil))
  implicit def randHNil: Random[HNil] = createRandom[HNil](arbHNil)

  // The inductive step is to assume an instance of Random for the head, and then force the compiler to search
  // for an analogous instance of Random for the tail. The head instance will be available via createRandom
  // which leverages an arbitrary generator of the appropriate type from randA. By contrast, the instance for
  // tail will not exist. The fallback behavior is to split the tail into a new head and tail, and start the
  // process anew.
  implicit def randHList[H, T <: HList]
    (implicit randHead: Lazy[Random[H]], randTail: Random[T]): Random[H :: T] = {
    val genHT: Gen[H :: T] = for {
      h <- randHead.value.generator
      t <- randTail.generator
    } yield h :: t
    createRandom[H :: T](Arbitrary(genHT))
  }

  // UTILITY
  // TODO: Not a great solution for always returning Some from a generator

}
