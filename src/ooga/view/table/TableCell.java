package ooga.view.table;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class to represent a cell in the table.
 */

public class TableCell {

  private Pane cell;
  private Rectangle cellBody;
  private Node contents;

  public TableCell(double cellWidth, double cellHeight) {
    cell = new StackPane();
    cellBody = new Rectangle(cellWidth, cellHeight, Table.DEFAULT_COLOR);
    cellBody.setStroke(Color.BLACK);
    contents = null;
    cell.getChildren().add(cellBody);
  }

  /**
   * Sets the contents of the cell.
   * @param newContents will be the new contents of the cell.
   */
  public void setContents(Node newContents) {
    cell.getChildren().clear();
    cell.getChildren().add(cellBody);
    cell.getChildren().add(newContents);
    contents = newContents;
  }

  /**
   * @return a displayable representation of the cell.
   */
  public Node getCell() {
    return cell;
  }

  /**
   * Change the background color of this cell.
   * @param color is the new color of the cell
   */
  public void changeColor(Color color) {
    cellBody.setFill(color);

  }

  /**
   * Get the current cell color
   */
  public Color getColor() {
    return (Color) cellBody.getFill();
  }

  /**
   * @return the contents of the cell (null if the cell does not contain anything)
   */
  public Node getContents() {
    return contents;
  }

}
