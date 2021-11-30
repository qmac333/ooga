* Added flipCards() to GameStatePlayerInterface to allow the player to use the flip card
* Also added flipHand() to PlayerInterface for the same reason
* Added skipEveryone() to GameStatePlayerInterface to allow player to use
skip player card
* Added getColor() to Player to move the color selection to the player class
* Deprecated executeAction() that takes GameState in favor of one that takes
a player
* Added enforceDraw(), flipGame(), reverseGame(), skipNextPlayer(), and 
skipEveryone() to Player interface for changing player v. card structure
* Deprecated the play that doesn't take a Player Interface in favor of one that
does
* Added discardColor() to PlayerInterface and removeColor() to hand for the
DiscardColorCard