## Usecases: ##
 
* A human player clicks on a 7 to play it, causing the display to update showing that the player now has 6 cards, and it is now a new player's turn.

```java
// Assume these subdisplays are already initialized

HandListDisplay handListDisplay; // shows cards in the current player's hand
TurnInfoDisplay turnInfoDisplay; // shows information about each player, including how many cards they have
    
    finishTurn() {
      playTurn();
      handListDisplay.update();
      turnInfoDisplay.update();
    }
```

* A player has to draw a card for their turn, causing the number of cards from the deck to decrement by one.

```java

// Assume these subdisplays are already initialized
DeckDisplay deckDisplay; // shows the number of cards in the deck, and the card on the top of the discard pile

  discardCard() {
    playTurn();
    deckDisplay.update();
    }


```