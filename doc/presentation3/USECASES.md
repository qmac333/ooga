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

```java
  @Override
  public void playTurn() {
    try {
      myPlayerGroup.playTurn();
    } catch (Exception e) {
      logError(e.getMessage());
    }
    PlayerGameInterface player = myPlayerGroup.getCurrentPlayer();
    if (player.getHandSize() == 0) {
      myPlayerGroup.countAndAwardPoints();
      endGame = player.getPoints() >= pointsToWin;
      for (PlayerGameInterface p : myPlayerGroup) {
        p.dumpCards();
      }
      cardContainer = new DeckWrapper(new UnoDeck(version), new CardPile());
      cardContainer.discard(cardContainer.getTopCard());
      dealCards();
    } else {
      myPlayerGroup.loadNextPlayer();
    }
  }
```

```java
  @Override
  public void playTurn() throws ReflectionErrorException, IOException {
    myPlayers.get(myCurrentPlayer).playCard();
    checkUno();
  }
```
```java
  @Override
  public void playCard() throws IOException {
    int index = super.getMyIntegerSupplier().get();
    try {
      if (index < 0){
        super.addCards(getMyGroup().noPlayDraw());
      } else {
        getMyHand().play(index, super.getMyGroup(), this);
      }
    } catch (Exception e){
      Log log = new Log(LOG_FILE, MethodHandles.lookup().lookupClass().toString());
      log.getLogger().setLevel(Level.WARNING);
      log.getLogger().warning(e.getMessage());
    }
  }
```

```java
  @Override
  public void play(int indexOfCard, PlayerGroupPlayerInterface group, PlayerCardInterface player)
      throws InvalidCardSelectionException {
    if (indexOfCard >= myCards.size()) {
      throw new InvalidCardSelectionException(
          String.format(handResources.getString(TOO_LARGE), indexOfCard));
    }
    myCards.get(indexOfCard).executeAction(player);
    group.discardCard(myCards.get(indexOfCard));
    myCards.remove(indexOfCard);
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

```java
  @Override
  public void playTurn() throws ReflectionErrorException, IOException {
    myPlayers.get(myCurrentPlayer).playCard();
    checkUno();
  }
```
```java
  @Override
  public void playCard() throws IOException {
    int index = super.getMyIntegerSupplier().get();
    try {
      if (index < 0){
        super.addCards(getMyGroup().noPlayDraw());
      } else {
        getMyHand().play(index, super.getMyGroup(), this);
      }
    } catch (Exception e){
      Log log = new Log(LOG_FILE, MethodHandles.lookup().lookupClass().toString());
      log.getLogger().setLevel(Level.WARNING);
      log.getLogger().warning(e.getMessage());
    }
  }
```

```java
 @Override
  public Collection<CardInterface> noPlayDraw() throws ReflectionErrorException {
    return myGame.noPlayDraw();
  }
```

```java
  @Override
  public Collection<CardInterface> noPlayDraw() throws ReflectionErrorException {
    int oldDraw = impendingDraw;
    impendingDraw = 0;
    if (oldDraw == 0) {
      return myDrawRule.noPlayDraw(this);
    } else if (oldDraw < 0) {
      return ReflectionHandlerInterface.performSpecialDraw(
          gameStateResources.getString(String.format(gameStateResources.getString(DRAW_BASE),
              oldDraw)), this,
          cardContainer.getLastCard().getMyColor(), myDrawRule);
    }
    return myDrawRule.forcedDraw(this, oldDraw);
  }
```

```java
  @Override
  public Collection<CardInterface> noPlayDraw(GameStateDrawInterface game) {
    return List.of(game.getNextCard());
  }
```