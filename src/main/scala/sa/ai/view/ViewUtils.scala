package sa.ai.view

import scalafx.scene.Node
import scalafx.scene.layout.HBox

/**
 *
 */
object ViewUtils
{

  def row(tiles : Seq[Node]) : Node =
    new HBox { content =
      tiles.flatMap(tile =>
        Seq(
          tile,
          new HBox {prefWidth = 10}
        )
      )
    }

}
