package ooga.view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class to represent a cell in the table.
 */

public class TableCell {

  public static final Color DEFAULT_COLOR = Color.WHITE;

  private Pane cell;
  private Rectangle cellBody;

  public TableCell(double cellWidth, double cellHeight) {
    cell = new StackPane();
    cellBody = new Rectangle(cellWidth, cellHeight, DEFAULT_COLOR);
    cell.getChildren().add(cell);
  }

  /**
   * Sets the contents of the cell.
   * @param newContents will be the new contents of the cell.
   */
  public void setContents(Node newContents) {
    cell.getChildren().clear();
    cell.getChildren().add(cell);
    cell.getChildren().add(newContents);
  }

  /**
   * @return a displayable representation of the cell.
   */
  public Node getCell() {
    return cell;
  }

}
