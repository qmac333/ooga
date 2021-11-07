# Test Plan

## Testing Strategies
For making our APIs more easily testable, we could employ the following strategies:
* using smaller, more specific classes to implement interfaces
* we could mock out the interface if it's not finished


## Test Cases

### Cards
We can have a test class which provides testing for cards which have a GameState class we can use for comparison.
* We want to check that the number of cards a player can play decreases
after a number card is played
* We want to check how a game state is changed after an action card is played
* We want to check that a card handles any exceptions having to do with it

### GameState
The GameState will interact with the controller, passing data to it, so many of its public methods will be getters and setters.
* Test that getters return a valid return type each time they're called
* See how the set of undrawn cards changes after a player acts
* See how the set of discarded cards changes after a players turn
The the last two cases, one could check how the size of each Collection changes.
