## Usecases: ##
 
* A human player clicks on a 7 to play it, causing the display to update showing that the player now has 6 cards, and it is now a new player's turn.

```java
// Assume these subdisplays are already initialized

HandListDisplay handListDisplay; // shows cards in the current player's hand
TurnInfoDisplay turnInfoDisplay; // shows information about each player, including how many cards they have
    
    finishTurn() {
      handListDisplay.update();
      turnInfoDisplay.update();
    }
```

