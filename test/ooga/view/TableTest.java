package ooga.view;

import javafx.scene.text.Text;
import ooga.view.table.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class used to test the functionality of the table API.
 */
public class TableTest {

  private Table testTable;

  @BeforeEach
  public void setup() {
    testTable = new Table(2, 2, 50, 20);
  }

  @Test
  public void testSimpleTable() {
    Text entry00 = new Text("Andrew");
    Text entry10 = new Text("Quentin");
    Text entry01 = new Text("Will");

    testTable.setCell(0, 0, entry00);
    testTable.setCell(1, 0, entry10);
    testTable.setCell(0, 1, entry01);

    assertEquals(entry00, (Text)testTable.getCell(0, 0));
    assertEquals(entry10, (Text)testTable.getCell(1, 0));
    assertEquals(entry01, (Text)testTable.getCell(0, 1));
    assertEquals(null, testTable.getCell(1, 1));
  }

  @Test
  public void testOutOfBounds() {
    int thrown = 0;
    try {
      testTable.getCell(2, 3); // out of bounds
    }
    catch (IndexOutOfBoundsException e) {
      thrown = 1;
    }

    assertEquals(1, thrown);
  }

  @Test
  public void testAddRow() {
    testTable.addRow();
    assertEquals(3, testTable.getNumRows());
    assertEquals(2, testTable.getNumCols());
  }

  @Test
  public void testDeleteRow() {
    Text entry00 = new Text("Andrew");
    Text entry10 = new Text("Quentin");
    Text entry01 = new Text("Will");

    testTable.setCell(0, 0, entry00);
    testTable.setCell(1, 0, entry10);
    testTable.setCell(0, 1, entry01);

    testTable.deleteRow(0);
    assertEquals(1, testTable.getNumRows());
    assertEquals(2, testTable.getNumCols());

    assertEquals(entry01, (Text)testTable.getCell(0, 0));
  }

  @Test
  public void testDeleteRowOutofBounds() {
    int thrown = 0;
    try {
      testTable.deleteRow(3);
    }
    catch (IndexOutOfBoundsException e) {
      thrown = 1;
    }

    assertEquals(1, thrown);
  }


}
