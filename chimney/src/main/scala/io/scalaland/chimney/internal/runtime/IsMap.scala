package io.scalaland.chimney.internal.runtime

import scala.annotation.{implicitNotFound, unused}

@implicitNotFound(
  "Expected map (type extending scala.collection.immutable.Map which has scala.collection.compat.Factory instance), got ${M}"
)
sealed trait IsMap[M] {
  type Key
  type Value
}
object IsMap {
  @implicitNotFound(
    "Expected map (type extending scala.collection.immutable.Map which has scala.collection.compat.Factory instance), got ${M}"
  )
  type Of[M, K, V] = IsMap[M] { type Key = K; type Value = V }

  private object Impl extends IsMap[Nothing]

  // build-in Chimney support for maps assumes that they are BOTH Map and have a Factory
  implicit def scalaMapIsMap[K, V, M <: Map[K, V]](implicit
      @unused ev: scala.collection.compat.Factory[(K, V), M]
  ): IsMap.Of[M, K, V] = Impl.asInstanceOf[IsMap.Of[M, K, V]]
}
