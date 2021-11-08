# Use Cases

## Will

* GameState: will contain players and cards, and update at each iteration of the step function
* Automated Player: a player which plays cards automatically, that is without someone controlling it
and without causing events
* Normal Player: acts according to events in the view
* Card: can be a Number card or an Action card, has members for Number and Color
* Action Card: performs some action on its player and the game state
* Number Card: designates which cards a player can play

## Quentin

* Basic UI: creating buttons for loading and saving files, calling the appropriate methods on the event
those buttons are clicked
* Card: creating the visual display for a single card. This could be a rectangle with a number, a color, and an image
* Event handling: handle event for when a card is clicked. This should pass down the index of the current player's 
card that was clicked.
* Player's hand: create a visual representation of each player's hand
* Blaster: the visual representation of the blaster for UNO Blaster and figuring out how to organize the UI when 
there is a blaster
* Splash screen: Initial menu screen to choose the number of players, starting number of cards, etc.

## Andrew

* Turn display: a display that shows all the current players of the game, how many cards they have,the direction of play (clockwise or counterclockwise) as well as the player that is currently taking their turn. 
* Deck and discard pile: create a visible display for the deck and discard pile. For the deck, this should at least include the number of cards in the deck, and for the discard pile this should display the top card in the discard pile.
* Event handling for the save game button that allows players to save the current game to a configuration (JSON) file.
* Creating a dropdown menu on the splash screen that allows the user to change the language of the game.
* Creating another window that allows users to change the rules of the game (stacks, number of points to win, etc.) during the game.
* Implementing the "card flip" animation that flips all of the player's cards over when a Card Flip card is played.
## Drew
* 