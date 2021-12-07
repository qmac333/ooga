package ooga.model.drawRule.blaster;

import java.util.Collection;
import java.util.List;

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
   * @return actual version of all cards in the blaster - used by the Save File feature
   */
  List<CardInterface> getCardList();

  /**
   * @return If the blaster just went off
   */
  boolean blasted();

  /**
   * Insert the given cards into the Blaster - used by the Load File feature
   */
  void setBlaster(List<CardInterface> cards);
}
