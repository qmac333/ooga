# OOGA Design Final
### Names:

Will Long, Quentin MacFarlane, Drew Peterson, Andrew Sander, Paul Truitt

## Team Roles and Responsibilities

 * Team Member #1

 * Team Member #2

 * Team Member #3


Will Long - Back End

 * Team Member #4: Andrew

Develop the main game display that players would use to play a Uno game. This included the DeckDisplay class (used to show information about the deck and discard pile), as well as
the TurnInfoDisplay (info about all of the players, including how many cards and points they have, as well as which player is currently taking their turn as well as who is next).
Also, primarily responsible for connecting the view to the model (determining which model APIs to call when clicking on cards and the draw button) and creating the main game loop.


 * Team Member #5


## Design goals

#### What Features are Easy to Add


One feature that was easy to add was new variations of games.  This is primarily due to 
the model's reliance on abstractions of card implementations, so new cards corresponding to new 
games were easily integrated.

=======
We wanted the UI for the main Uno game to be able to:

* Allow players to click on a card they want to play for their turn.
* Highlight cards that players are able to play, to make it easy to determine which cards the user can play.
* Provide the player with the ability to draw a card if they do not want to/cannot play a card on their turn.
* Be required to call Uno before playing their second-to-last card, or else face a draw penalty.


## High-level Design

View:

* Splash Screen (Quentin)

* Main Display

The goal of the view during a Uno Game is to display information relevant to the current player taking their turn, as well as provide methods for users to perform actions for their turn such as playing cards, drawing a card, or calling Uno.
Each of the classes in the view can read the information from the gameState class (accessed through the interface ViewGameStateInterface so that the view can only read relevant information about the game state, and NOT modify it) and update its displayed information accordingly.
The update is done through calling the public update() API, which takes no parameters and updates all nodes shown to the display to reflect the current game state.
For example, when update() is called for the TurnInfoDisplay, the table of players is changed to highlight the current player taking their turn, as well as the number of cards in each person's hand.
When update() is called on a handListDisplay instance, the class updates the cards that are shown to the screen, which ones are highlighted by asking the model which ones are valid to play that turn, as well as displaying options for drawing and calling Uno if it is a human's turn.

The class BasicUnoDisplay (or Blast or Flip, depending on the game type) contains instances of each of these subdisplays with update() methods, and calls update() on each of the subdisplays after the current turn ends (or if a cheat key is pressed).

The main display tells the model to play a turn by calling the playTurn() method, which in turn contains suppliers to the HandList display class to recieve user input for the index of the card that was clicked on, as well as the color selected for a wild card.
For a computer player, playTurn() is called when the "Play Turn" button is pressed.
For a human player, playTurn() is called when a valid card is clicked. The index of the card being clicked is first recorded before running playTurn(), such that when the model calls the supplier method to get the index of the card the human player used, the JavaFX thread does not have to block waiting for user input;
it can simply just return the index of the card that was previously clicked as the card to play that turn. Since the color selection is done by picking an option from a pop-up, for which the user can select options while the JavaFX thread is waiting, the color does not have to be predeterminated before clicking on a wild card;
from the view's perspective, it doesn't have to care if it is a wild card or not, the model will use the supplier to get a color input if needed through the pop-up.

#### Core Classes


Model
* GameState
* PlayerGroup
* Player
* DeckWrapper
* RuleSet
* DrawRuleSet

=======
View:

* Quentin: talk about classes you designed (i.e. SplashScreen, LanguageScreen, and CardDisplay).

The BasicUnoDisplay is the main class that creates the scene for a Uno game. It contains instances of smaller displays DeckDisplay, HandListDisplay, and TurnInfoDisplay, that are all updated
in the game loop of BasicUnoDisplay. The game loop is run whenever a card is clicked for a human player or the "Play Turn" button is clicked to play a computer player's turn. Each game variation has a class that extends
the BasicUnoDisplay, most notably the BlastUnoDisplay which adds the visual display of the "Blaster" that accumulates cards
as drawn cards are placed into the blaster.


## Assumptions that Affect the Design

View:

* A notable assumption made is that on a human player's turn, when playTurn() is called, the supplier method selectCard() (implemented in HandListDisplay but will be called on a turn by the model through a supplier functional interface),
must return the index of the card that will be played without waiting on any UI input. Earlier implementations
of the playTurn() API allowed the supplier to block, but this ended up freezing the UI and preventing the user from providing the input that the supplier was waiting on. In order not to block, the supplier method must know which index to return when it is called by setting the variable selectedIndex appropriately.
So from the model's perspective, it can be assumed that this supplier method does not block.


#### Features Affected by Assumptions

View:

Supporting the assumption that the selectCard() supplier will return the index of the card selected by the player for their turn without blocking was definitely though. The final implementation of the feature
had the index of the card that was clicked being saved off into a variable, such that the selectCard() method would return that index, and THEN calling playTurn(), but this definitely took some iterations.

Notably, at first, the assumption was that the supplier method had to wait for user input for getting the index of the card played, which led to any front end implementations requiring a showAndWait() method to be able to get the user input
while waiting. Unfortunately, methods of getting the index input this way were very unintuitive, such as through selecting the index from a list in the DialogChooser, and not desirable, especially for players just learning the game.


## Significant differences from Original Plan


In the model, we ended up redistributing a lot of responsibility away from the GameState class.
For example, Paul made a new PlayerGroup class that held all the players in a game and managed their
interactions.  Originally, all the players were held in the GameState.
=======
On the main display wireframe (UI-Image_1), one change was adding in a button that allowed human players to draw cards on their turn.
In addition, it is also notable that a "Play Turn" button would appear on a computer player's turn, in which the user would click the button to play the computer's turn, as opposed to clicking on cards.
We justified this choice by thinking that it wouldn't make sense to show the computer player's cards on their turn, since that would be considered "cheating", nor would a human player want to play a card for a computer player, so there had to be some other easy way to play their turn.


## New Features HowTo

For the view: modify the update() function of the appropriate display (i.e. HandDisplay which displays the cards in the curent player's hand, TurnInfoDisplay which displays information about each of the players, etc.) that you would like to change.


#### Easy to Add Features

* New Mods, or "skins" for each of the cards. Adding in a new set of images to display on the cards can be done easily, by adding the type of mod to each of the different language resource files in src.resources.mods, and then
creating a resource files, with keys for each of the card types required to play the games, and values corresponding to the image locations on the machine for each of those card types.

* New information to display about each player in the TurnDisplayTable, such as the number of times they have called Uno. This can be done by adding the information to display as a getter in the ViewPlayerInterface,
and adding a column in the table that contains the information from the getter when a new player is added to the table.

#### Other Features not yet Done

* We had an idea for a feature that displayed a pop-up window when the blaster went off in the Uno Blaster game.
When the blaster went off (i.e. a player drew a card, put it in the blaster, and the game engine decided that the blaster should shoot out all of its cards based on RNG), we wanted to display a pop up window that made it clear to the user that the blaster went off.
Right now, when it would go off, all the displayed cards in the blaster section would be removed, and the card count of the current player would increase, but ideally we would want it to be more clear to newer players that the reason they had to draw cards was because the blaster went off.
