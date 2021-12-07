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

  @Test
  public void blastedChangesCorrectly(){
    myBlaster = new Blaster(0);
    myBlaster.insert(List.of(new SkipCard("red")));
    assertFalse(myBlaster.blasted());
    myBlaster.setProbabilityOfBlast(1);
    myBlaster.insert(List.of(new SkipCard("red")));
    assertTrue(myBlaster.blasted());
    myBlaster.insert(List.of(new SkipCard("red")));
    assertTrue(myBlaster.blasted());
    myBlaster.setProbabilityOfBlast(0);
    myBlaster.insert(List.of(new SkipCard("red")));
    assertFalse(myBlaster.blasted());
  }

  @Test
  public void checkingCardsInBlaster(){
    myBlaster = new Blaster(0);
    myBlaster.insert(List.of(new SkipCard("red")));
    assertEquals(1, myBlaster.getCards().size());
    myBlaster.insert(List.of(new SkipCard("red")));
    assertEquals(2, myBlaster.getCards().size());
    myBlaster.setProbabilityOfBlast(1);
    myBlaster.insert(List.of(new SkipCard("red")));
    assertEquals(0, myBlaster.getCards().size());
  }
}
