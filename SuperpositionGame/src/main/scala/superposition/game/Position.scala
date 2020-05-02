package superposition.game

import com.badlogic.ashley.core.Component
import superposition.math.{Vector2d, Vector2i}
import superposition.quantum.{MetaId, StateId}

private class Position(val absolute: MetaId[Vector2d],
                       val cell: StateId[Vector2i],
                       var relative: Vector2d) extends Component
