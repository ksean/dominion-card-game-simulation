package sa.ai.view

import scalafx.scene.Node
import scalafx.scene.layout.{VBox, HBox}
import scalafx.scene.control.Label

/**
 *
 */
object ViewUtils
{
  def topIndent(node : Node, indent : Int) : Node =
    new VBox {
      content = Seq(
        new HBox { prefHeight = indent },
        node
      )
    }

  def leftIndent(node : Node, indent : Int) : Node =
    new HBox {
      content = Seq(
        new HBox { prefWidth = indent },
        node
      )
    }

  def bottomIndent(node : Node, indent : Int) : Node =
    new VBox {
      content = Seq(
        node,
        new HBox { prefHeight = indent }
      )
    }

  def rightIndent(node : Node, indent : Int) : Node =
    new HBox {
      content = Seq(
        node,
        new HBox { prefWidth = indent }
      )
    }

  def indent(node : Node, top : Int = 0, left : Int = 0, bottom : Int = 0, right : Int = 0) : Node =
    topIndent(
      leftIndent(
        bottomIndent(
          rightIndent(
            node, right), bottom), left), top)

  def row(nodes : Seq[Node]) : Node =
    new HBox { content =
      nodes.map(
        indent(_, right = 10))
    }

  def column(nodes : Seq[Node]) : Node =
    new VBox { content =
      nodes.map(
        indent(_, bottom = 10))
    }
  

  def labeled(label : String, node : Node) : Node =
    new VBox {
      content = Seq(
        new Label(label),
        indent(node, left = 10)
      )
    }
}
