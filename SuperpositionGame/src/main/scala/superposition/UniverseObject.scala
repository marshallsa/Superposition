package superposition

import engine.core.Behavior.{Component, Entity}
import extras.physics.PositionComponent

/**
 * A universe object is any object that exists within a particular universe.
 * <p>
 * Universe objects <em>must</em> perform all drawing in their [[superposition.Drawable#draw]] method. This makes it
 * possible for the multiverse to draw copies of an object from different universes in superposition.
 *
 * @param entity    the entity for this component
 * @param universe  the universe this object belongs to
 * @param id        the ID of this object
 * @param _cell     the initial grid cell of this object
 * @param collision whether this object collides with other objects in the universe (excluding walls)
 */
private final class UniverseObject(entity: Entity with Copyable[_ <: Entity] with Drawable,
                                   var universe: Universe,
                                   val id: UniversalId,
                                   private var _cell: Cell,
                                   var collision: Boolean = false) extends Component(entity) {
  /**
   * The position component of this object.
   */
  lazy val position: PositionComponent = get(classOf[PositionComponent])

  /**
   * The multiverse this object belongs to.
   */
  val multiverse: Multiverse = universe.multiverse

  /**
   * The grid cell of this object.
   */
  def cell: Cell = _cell

  def cell_=(value: Cell): Unit = {
    _cell = value
    // position.value = new Vec2d(value.column + 0.5, value.row + 0.5)
  }
}