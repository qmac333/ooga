package ooga.model.drawRule;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import ooga.model.cards.individualCards.NumberCard;
import ooga.model.cards.individualCards.ReverseCard;
import ooga.model.cards.individualCards.SkipCard;
import ooga.model.drawRule.blaster.Blaster;
import org.junit.jupiter.api.Test;

public class BlasterTest {
  Blaster myBlaster;

  @Test
  public void aBlasterWithZeroProbWontShoot(){
    myBlaster = new Blaster(0);
    assertTrue(myBlaster.insert(List.of(new SkipCard("red"))).isEmpty());
    assertTrue(myBlaster.insert(List.of(new SkipCard("red"))).isEmpty());
    assertTrue(myBlaster.insert(List.of(new SkipCard("red"))).isEmpty());
    assertTrue(myBlaster.insert(List.of(new SkipCard("red"))).isEmpty());
  }

  @Test
  public void aBlasterWithOneProbWillAlwaysShoot(){
    myBlaster = new Blaster(1);
    assertFalse(myBlaster.insert(List.of(new SkipCard("red"))).isEmpty());
    assertFalse(myBlaster.insert(List.of(new SkipCard("red"))).isEmpty());
    assertFalse(myBlaster.insert(List.of(new SkipCard("red"))).isEmpty());
    assertFalse(myBlaster.insert(List.of(new SkipCard("red"))).isEmpty());
  }

  @Test
  public void addingMultipleCardsShootsThemAll(){
    myBlaster = new Blaster(1);
    assertEquals(3, myBlaster.insert(List.of(new SkipCard("red"), new ReverseCard("green"), new NumberCard("red", 5))).size());
  }

  @Test
  public void theNumberOfCardsBuilds(){
    myBlaster = new Blaster(0);
    myBlaster.insert(List.of(new SkipCard("red")));
    myBlaster.insert(List.of(new SkipCard("red")));
    myBlaster.insert(List.of(new SkipCard("red")));
    myBlaster.setProbabilityOfBlast(1);
    assertEquals(4, myBlaster.insert(List.of(new SkipCard("red"))).size());
  }

  @Test
  public void blasterSizeResets(){
    myBlaster = new Blaster(0);
    myBlaster.insert(List.of(new SkipCard("red")));
    myBlaster.insert(List.of(new SkipCard("red")));
    myBlaster.insert(List.of(new SkipCard("red")));
    myBlaster.setProbabilityOfBlast(1);
    assertEquals(4, myBlaster.insert(List.of(new SkipCard("red"))).size());
    assertEquals(1, myBlaster.insert(List.of(new SkipCard("red"))).size());
  }
}
