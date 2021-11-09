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
For the last two cases, one could check how the size of each Collection changes.

### Display Testing
The test class for the display will use JavaFX testing to simulate users interacting with the display
* When a user clicks on a button, such as load file, for example, we want to check the display to look for specific components
that should have been loaded onto the display for the game
* If the user clicks on a card that is not valid to be played, we should check that an error message pops up telling
the user to choose a different card.
* We could create a test to make sure that the "player turn view" updates when a new step is taken in the program. The
next player should be highlighted.
* Test to see whether or not the hand passed up to the display from the view matches the cards displayed in the view. For 
example, if a current player's hand consists of two yellow 7's, check to see that there are two rectangles in the 
HandListDisplay and that their fill is yellow and they have a number 7 on them
* Example of a test for TurnInfoDisplay: start a game by loading a config file, then allow the model to go through one 
tick of the program, making the user play a card or draw a card. After the tick is over, check to see that the next player
in the TurnInfoDisplay is highlighted. 

