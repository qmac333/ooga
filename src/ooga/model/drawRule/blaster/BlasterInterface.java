package ooga.model.drawRule.blaster;

import java.util.Collection;
import ooga.model.cards.CardInterface;
import ooga.model.cards.ViewCardInterface;

/**
 * Interface for the blaster object for the Uno Blast Version
 */
public interface BlasterInterface {

  /**
   * Insert a collection of cards and possibly fire some cards back
   *
   * @param card Cards to insert
   * @return The blasted cards if any
   */
  Collection<CardInterface> insert(Collection<CardInterface> card);

  /**
   * Changes the probability of the cards ejecting
   *
   * @param probability desired for change
   */
  void setProbabilityOfBlast(double probability);

  /**
   * @return View safe version of all cards in the blaster
   */
  Collection<ViewCardInterface> getCards();

  /**
   * @return If the blaster just went off
   */
  boolean blasted();
}
