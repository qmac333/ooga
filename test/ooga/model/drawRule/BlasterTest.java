package ooga.model.drawRule;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import ooga.model.cards.NumberCard;
import ooga.model.cards.ReverseCard;
import ooga.model.cards.SkipCard;
import ooga.model.drawRule.blaster.Blaster;
import org.junit.jupiter.api.Test;

public class BlasterTest {
  Blaster myBlaster;

  @Test
  public void aBlasterWithZeroProbWontShoot(){
    myBlaster = new Blaster(0);
    assertTrue(myBlaster.insert(List.of(new SkipCard("red", null))).isEmpty());
    assertTrue(myBlaster.insert(List.of(new SkipCard("red", null))).isEmpty());
    assertTrue(myBlaster.insert(List.of(new SkipCard("red", null))).isEmpty());
    assertTrue(myBlaster.insert(List.of(new SkipCard("red", null))).isEmpty());
  }

  @Test
  public void aBlasterWithOneProbWillAlwaysShoot(){
    myBlaster = new Blaster(1);
    assertFalse(myBlaster.insert(List.of(new SkipCard("red", null))).isEmpty());
    assertFalse(myBlaster.insert(List.of(new SkipCard("red", null))).isEmpty());
    assertFalse(myBlaster.insert(List.of(new SkipCard("red", null))).isEmpty());
    assertFalse(myBlaster.insert(List.of(new SkipCard("red", null))).isEmpty());
  }

  @Test
  public void addingMultipleCardsShootsThemAll(){
    myBlaster = new Blaster(1);
    assertEquals(3, myBlaster.insert(List.of(new SkipCard("red", null), new ReverseCard("green", null), new NumberCard("red", 5))).size());
  }

  @Test
  public void theNumberOfCardsBuilds(){
    myBlaster = new Blaster(0);
    myBlaster.insert(List.of(new SkipCard("red", null)));
    myBlaster.insert(List.of(new SkipCard("red", null)));
    myBlaster.insert(List.of(new SkipCard("red", null)));
    myBlaster.setProbabilityOfBlast(1);
    assertEquals(4, myBlaster.insert(List.of(new SkipCard("red", null))).size());
  }

  @Test
  public void blasterSizeResets(){
    myBlaster = new Blaster(0);
    myBlaster.insert(List.of(new SkipCard("red", null)));
    myBlaster.insert(List.of(new SkipCard("red", null)));
    myBlaster.insert(List.of(new SkipCard("red", null)));
    myBlaster.setProbabilityOfBlast(1);
    assertEquals(4, myBlaster.insert(List.of(new SkipCard("red", null))).size());
    assertEquals(1, myBlaster.insert(List.of(new SkipCard("red", null))).size());
  }
}
