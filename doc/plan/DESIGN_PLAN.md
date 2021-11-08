# Initial Design Plan

We are going to have classes such as Player and Card that will be abstracted using interfaces. The Card will be open
to extension and will have subclasses detailing the different types of cards in the game, such as number cards and 
action cards. 

##Controller

* The controller takes in a file and there will be a class that parses the file. The controller will call the method
in the file parser class. 

* There should be a step method in controller, which affects model, causing model to do its logic and then sends 
data back to controller which sends it to the view for the view to display. 

* When the user clicks on something in the view, need a method in the controller to update something in the view.

* The controller only interacts with a specified "Game" class from the model side of things. There is no reason for the 
controller to interact with certain "Player" or "Card" classes. 

when a player plays a card, the view controller has to pass down the index of the card that was played
down to the model so that the model can remove it from the current player's hand

model needs a public setConsumer method so controller can set consumer and model can keep track of consumer
and view implements consumer interface

abstract class implements player interface: collection of cards

interface to be implemented by game/model class

abstract game class, blaster would extend it to have its own unique implementations