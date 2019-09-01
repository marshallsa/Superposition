package superposition

import engine.core.Behavior.Entity
import engine.core.Game
import engine.util.math.Vec2d

import scala.collection.immutable.HashMap
import scala.math.pow

/**
 * A game universe.
 *
 * Universes contain objects in a particular (definite) state that can interact with each other, but not with objects
 * from other universes. It corresponds to a basis vector with a particular amplitude (coefficient) in a quantum state.
 */
private class Universe extends Entity {
  /**
   * The probability amplitude of this universe.
   */
  var amplitude: Complex = Complex(1)

  private var _objects: Map[UniversalId, UniverseObject] = new HashMap[UniversalId, UniverseObject]()

  private var _qubits: Map[UniversalId, Qubit] = new HashMap[UniversalId, Qubit]()

  /**
   * The objects in this universe.
   */
  def objects: Map[UniversalId, UniverseObject] = _objects

  /**
   * The qubits in this universe.
   */
  def qubits: Map[UniversalId, Qubit] = _qubits

  /**
   * The state of this universe, given by Σ,,i,, q,,i,, · 2^i^, where q,,i,, is the state of the ith qubit.
   */
  def state: Int =
    qubits.values.map(q => if (q.on) pow(2, q.universeObject.id.value).toInt else 0).sum

  /**
   * Creates a copy of this universe and all of its objects.
   *
   * @return a copy of this universe
   */
  def copy(): Universe = {
    val universe = new Universe()
    universe.amplitude = amplitude
    for (o <- objects.values) {
      val copy = o.entity.copy()
      copy.get(classOf[UniverseObject]).universe = universe
      Game.create(copy)
    }
    universe
  }

  /**
   * Adds the object to this universe.
   *
   * @param universeObject the object to add
   */
  def add(universeObject: UniverseObject): Unit = {
    require(!objects.contains(universeObject.id), "ID has already been used")
    _objects += (universeObject.id -> universeObject)
  }

  /**
   * Adds the qubit to this universe.
   *
   * @param qubit the qubit to add
   */
  def add(qubit: Qubit): Unit = {
    require(!qubits.contains(qubit.universeObject.id), "ID has already been used")
    _qubits += (qubit.universeObject.id -> qubit)
  }

  override protected def onDestroy(): Unit = objects.values.foreach(uo => Game.destroy(uo.entity))
}
