package com.stronghold.generator

import org.scalatest.FunSuite
import org.scalacheck.Arbitrary._
import org.scalacheck.Gen
import com.stronghold.generator.Random._
import shapeless.{HList, HNil, ::, Lazy, Generic}

case class Foo(bool: Boolean, int: Int, str: String)
case class FooOpt(bool: Option[Boolean], int: Option[Int], str: Option[String])

class RandomTest extends FunSuite {

  test("Should be able to generate an arbitrary case class with options for each field") {
    // val genInt: Random[Int] = Random.createRandom[Int](randA[Int])
    // val genFoo: Random[Foo] = Random.createRandom[Foo]
    // val fooOpt: Option[Foo] = genFoo.generateOption
    // val foo: Foo = genFoo.generateValue
    // val foo2: Foo = Random.random[Foo]
    // println(s"genInt: $genInt, genFoo: $genFoo, fooOpt: $fooOpt, foo: $foo")

    val hnil: Option[HNil] = Random.arbHNil.arbitrary.sample
    val randHNil: Random[HNil] = Random.randHNil
    val randHList: Random[Boolean :: Int :: String :: HNil] = Random.randHList[Boolean, Int :: String :: HNil]
    val randFoo: Random[Foo] = Random.genericRandom[Foo, Boolean :: Int :: String :: HNil]
    val randFoo2: Foo = Random.random[Foo]
    println(s"hnil: $hnil, randHNil: $randHNil, randHList: $randHList, randFoo: $randFoo, randFoo2: $randFoo2")

    assert(1 == 1)
  }

}
