package superposition.quantum

import scalaz.std.option._
import scalaz.syntax.functor._
import superposition.math._

/** A universe represents a basis state, its corresponding probability amplitude, and additional arbitrary metadata.
  *
  * @param amplitude the probability amplitude of the universe
  * @param state the classical values of each qudit in the universe
  * @param meta arbitrary metadata for the universe
  */
final case class Universe(
    amplitude: Complex = Complex(1),
    state: DependentMap[StateId[_]] = DependentMap.empty,
    meta: DependentMap[MetaId[_]] = DependentMap.empty) {

  /** Increases the probability amplitude by `c`.
    *
    * @param c the number to add to the probability amplitude
    * @return the universe with probability amplitude increased by `c`
    */
  def +(c: Complex): Universe = copy(amplitude = amplitude + c)

  /** Decreases the probability amplitude by `c`.
    *
    * @param c the number to subtract from the probability amplitude
    * @return the universe with probability amplitude decreased by `c`
    */
  def -(c: Complex): Universe = copy(amplitude = amplitude - c)

  /** Multiplies the probability amplitude by `c`.
    *
    * @param c the number to multiply the probability amplitude by
    * @return the universe with probability amplitude multiplied by `c`
    */
  def *(c: Complex): Universe = copy(amplitude = amplitude * c)

  /** Divides the probability amplitude by `c`.
    *
    * @param c the number to divide the probability amplitude by
    * @return the universe with probability amplitude divided by `c`
    */
  def /(c: Complex): Universe = copy(amplitude = amplitude / c)

  /** Replaces the value of a qudit.
    *
    * @param id the qudit to update
    * @param value the new value of the qudit
    * @return the updated universe
    */
  def updatedState(id: StateId[_])(value: id.Value): Universe = copy(state = state.updated(id)(value))

  /** Maps the value of a qudit.
    *
    * @param id the qudit to update
    * @param updater a function that maps the value of the qudit to the new value
    * @return the updated universe
    */
  def updatedStateWith(id: StateId[_])(updater: id.Value => id.Value): Universe =
    copy(state = state.updatedWith(id)(updater.lift))

  /** Replaces the value of a piece of metadata.
    *
    * @param id the metadata to update
    * @param value the new value of the metadata
    * @return the updated universe
    */
  def updatedMeta(id: MetaId[_])(value: id.Value): Universe = copy(meta = meta.updated(id)(value))

  /** Maps the value of a piece of metadata.
    *
    * @param id the metadata to update
    * @param updater a function that maps the value of the metadata to the new value
    * @return the updated universe
    */
  def updatedMetaWith(id: MetaId[_])(updater: id.Value => id.Value): Universe =
    copy(meta = meta.updatedWith(id)(updater.lift))
}

/** An opaque identifier corresponding to a qudit of a particular type.
  *
  * @tparam A the type of the qudit
  */
final class StateId[A] extends DependentKey {
  type Value = A
}

/** An opaque identifier corresponding to metadata of a particular type.
  *
  * @tparam A the type of the metadata
  */
final class MetaId[A] extends DependentKey {
  type Value = A
}
