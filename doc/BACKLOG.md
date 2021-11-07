# Use Cases

## Will

* GameState: will contain players and cards, and update at each iteration of the step function
* Automated Player: a player which plays cards automatically, that is without someone controllingit
and without causing events
* Normal Player: acts according to events in the view
* Card: can be a Number card or an Action card, has members for Number and Color
* Action Card: performs some action on its player and the game state
* Number Card: designates which cards a player can play