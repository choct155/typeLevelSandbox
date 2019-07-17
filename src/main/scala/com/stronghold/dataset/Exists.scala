package com.stronghold.dataset

import scala.annotation.implicitNotFound
import shapeless.{HList, HNil, ::}
import shapeless.LabelledGeneric
import shapeless.ops.record.Selector

@implicitNotFound(msg = "No column ${C} of type ${T} found in ${A}")
trait Exists[A, C, T]

object Exists {

  implicit def columnExists[A, ARepr <: HList, C, T](implicit gen: LabelledGeneric.Aux[A, ARepr], select: Selector.Aux[ARepr, C, T]): Exists[A, C, T] = {
    new Exists[A, C, T] {}
  }

}
