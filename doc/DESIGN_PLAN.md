###Controller

The controller takes in a file and there will be a class that parses the file. The rules will be 

step function in controller, affects model, model does its logic and then sends data back to controller 
which sends it to the view for the view to display

click on something, have a method in a controller to do something in the view

splash screen, and it stays up after you hit start game

when a player plays a card, the view controller has to pass down the index of the card that was played
down to the model so that the model can remove it from the current player's hand



view holds controller, which holds model

model: parameters to specify next player

abstract class implements player interface: collection of cards

interface to be implemented by game/model class

each iteration, a player plays a card 

abstract game class, blaster would extend it to have its own unique implementations