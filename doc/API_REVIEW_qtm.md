# Part 1
## How does your API encapsulate your implementation decisions?
For the view, the API should be fairly small on a high level, because all we really need is have a setScene()
method for each display screen. The API for the handlist display encapsulates all of the different functions
that could be related to the hand of cards, such as choosing a color on a Wild play, or just choosing a card
to play in general. Our API in the game state encapsulates different things, since we have different interfaces. 
For example, the GameStateViewInterface is the interface that encapsulates everything that the view needs from 
the model. Meanwhile, the GameStatePlayerInterface encapsulates everything in the logic that deals with a single player. 
In the controller, we have APIs to encapsulate methods for each possible screen in the view. The language chooser screen,
the splash screen, and the main game screen.

## What abstractions is your API built on?
Our APIs are built on different abstractions, depending on where they are in our code. For example, for the hand list,
the API does not care what JavaFX component the hand list display is. Instead, we just pass up a Node to the UnoDisplay
class for the UnoDisplay to put on the screen. The UnoDisplay class does not know what hand list display is, it just
knows that it is a node and it knows where to put it. We also use suppliers in our APIs to hide some specific
implementations of our items that are being passed directly from the model to the view and the other way around.

## What about your API's design is intended to be flexible?
Our API is very specific to the rules of any UNO game. We have very general methods in our APIs such as playGame(), 
playTurn(), or getCurrentPlayers(). The methods can be applied to any game, so they are flexible. Additionally,
our view is very flexible in that the components on the splash screen and uno display are going to be the same for 
any UNO game. 

## What exceptions (error cases) might occur in your API and how are they addressed?
There might not be any players inputted into the game and thus when we try to get the players to display in the view,
there will be nothing to show. We handle this in the controller by telling the user through a pop up that they have
not inputted any parameters to the game that would allow the user to start the game. Also, in our API, the user might
try to play a card without selecting one yet in the pop up in the view. We handle this by simply not doing anything
and having the user re-choose a card without breaking the program. 

# Part 2
## How do you justify that your API is easy to learn?
We wrote our API in interfaces, so it is very easy to understand. We made the method names very intuitive so that 
users would be able to understand it easily. Also, the interfaces are laid out well in packages that describe what 
they are doing and where they are going to be sending data. 

## How do you justify that your API leads to readable code?
The methods of our API are intuitive and easy to understand. Also, they are well commented. Our API leads to readable
code because the methods are easy to implement and they do not require any dependencies. 

## How do you justify that your API is hard to misuse?
Our API has good error handling and should not break the program without telling the user what is going on.

## Why do you think your API design is good?
Our API design is good because it is well spread out between separate interfaces that each handle a specific
part of the program and send data to single points. My measure of good is that the API is easy to understand and 
the methods have very little dependencies. 