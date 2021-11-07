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

## Drew
* 