package superposition

import engine.core.Behavior.{Component, Entity}
import engine.core.Game
import engine.graphics.sprites.Sprite
import engine.util.Color
import engine.util.Color.WHITE
import engine.util.math.{Transformation, Vec2d}
import extras.physics.PositionComponent

/**
 * An object that can be drawn.
 */
private trait Drawable {
  /**
   * Draws this object.
   */
  def draw(): Unit
}

/**
 * Adds a sprite to an entity.
 * <p>
 * Note that having this component by itself does nothing; it also needs either a [[superposition.UniverseObject]] or
 * [[superposition.Draw]] component to be drawn.
 *
 * @param entity the entity for this component
 * @param sprite the sprite for this entity
 * @param scale  the scale of the sprite
 * @param color  the color of the sprite
 */
private final class DrawableSprite(entity: Entity,
                                   val sprite: Sprite,
                                   val scale: Vec2d = new Vec2d(1, 1),
                                   var color: Color = WHITE) extends Component(entity) with Drawable {
  private val position: PositionComponent = get(classOf[PositionComponent])

  /**
   * Draws this sprite.
   */
  override def draw(): Unit =
    sprite.draw(Transformation.create(position.value, 0, scale), color)
}

/**
 * Contains initialization for the draw component.
 */
private object Draw {
  /**
   * Declares the draw system.
   */
  def declareSystem(): Unit =
    Game.declareSystem(classOf[Draw], (_: Draw).draw())
}

/**
 * The draw component automatically draws an entity.
 *
 * @param entity the entity for this component
 */
private final class Draw(entity: Entity with Drawable) extends Component(entity) {
  private def draw(): Unit = entity.draw()
}