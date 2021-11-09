# Initial Design Plan

## Model

* The model will depict the state of the game, represented by a GameState class, at each iteration 
of the step function.
* The GameState will include as members Players and Cards, each of their own classes, which will we organized
so that the order of play is preserved and so that cards will
be categorized according to whether they are playable and which players hold them.
* At every iteration of the step function, the player whose turn it is 
will act and change some feature of the GameState.  Therefore, the GameState class acts like
a data structure which has functions applied to it.
* After every iteration of step function, the new state of the game is passed up to the controller
via getters in a way that does not compromise any private data structures.

We are going to have classes such as Player and Card that will be abstracted using interfaces. The Card will be open
to extension and will have subclasses detailing the different types of cards in the game, such as number cards and 
action cards. 

## Display

* The Display will be used to create a visual representation of all of the logic going on in the background.
* The Display will have a separate class to display the specific cards that are in the current player's hand.
* In the API for the current player's hand display, there is a method that takes an update to the player's hand
when they play or draw a card, and then redraws the hand on the display. 
* The Display retrieves a consumer that is sent straight from the model/logic classes containing the information
for what is supposed to be shown on the display for the current hand. 
* The display will communicate with a controller which should tell the display a lot of information such as 
whose turn it is, what direction the game is being played in, each player's score, whether or not someone won the 
game, etc. 
* The display's job is just to display whatever the controller and model tells it to.
* The display's API also includes a method to show an error in the form of a pop-up when the user does something that
they are not supposed to. This method will be sent to the controller for it to handle errors. 
* Whenever a card is clicked in the display, it sends the information of which card was clicked back down to the controller
so that the controller can process and tell the model to remove that card from the player's hand. 

## Controller

* Overview: The controller's purpose is to serve as a middle-man between the view and the model. Specifically, it will
receive information from the view, parse it, and pass relevant parameters/updates to the model. As part of this, the controller
must be able to save/load configuration files and it will utilize a separate parser class to accomplish this. If loading,
once the parser returns the parameters, the controller must create a new model object using those parameters. It should also
be able to set up the direction interaction between the model and view by passing the view's consumer/interface to the model,
enabling the model to call methods of that interface in order to notify the view of changes in its state. The controller also must be
able to properly enact a step of the game depending on whether it is the user's turn (and the game must wait for user input)
or whether it is the computer's turn. As for extendability, the main controller class should be extendable in order to make
it easy to add new features, but the core functionalities should be present and uneditable. Furthermore, the parser should
be easily extendable to accommodate new parameter types in case the base format of the configuration file changes (although
the file type should stay constant as agreed upon by the group)

* Details: The controller has clear interactions with the model and the view. It provides public methods for the view to
call depending on the user's input, and it calls methods from the model's API. Again, the main services it provides to the
view are loading configuration files/setting up the model, saving configuration files, and enacting a single step of the
game (by telling the model to run one step of its AI if it is the computer's turn or by passing it the index of the card
the user selected to play if it is the user's turn). On the other hand, when interacting with the model, the controller 
should only ever need to interact with the main GameState class (as opposed to also initializing the Cards and Players). 
The controller will pass the model relevant information on the game cards, players, etc., but the model will be in charge 
of handling these initializations.

* Considerations: a big consideration for the controller/view interaction is how the controller should handle running a step
of the game when it is the user's turn vs. the computer's turn. If the view has no knowledge of the game state, then the
controller will have to know whose turn it is and potentially pause the JavaFX TimeLine that continually calls the game
step function until the user has selected a card. Another solution would be for the controller to always call the same method
from the model regardless of whose turn it is and leave it up to the model to call a method from the view's consumer interface
in order to retrieve the index of the user's chosen card when needed.