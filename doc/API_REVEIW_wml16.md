# Will - API Review

CardPile 

## Part 1

Q: How does your API encapsulate implementation decisions?
* Implements private methods to maintain itself without giving up too much data.

Q: What abstractions are your API built on?
* This API deals with CardInterface API instances which represent actual cards
in the game.

Q: What about your API is intended to be flexible?
* The CardPile can contain any sort of instance of a CardInterface, and thus has no
problem being used in multiple different game types.

Q: What exceptions might occur in your API and how do you handle them?
* One exception that might occur is if there are not enough cards to deal to all the players.
This would be an issue with the ResourceBundle containing game information for a particular game type,
but ultimately, the root cause of this would have to be handled in the controller.


## Part 2

Q: How do you justify that your API is easy to learn?
* It is well commented and the methods are named well.

Q: How do you justify that API leads to readable code?
* All methods take in and return simple data structures even though they might execute complicated actions behind the scenes.

Q: How do you justify that API is hard to misuse?
* Each method is designed to work with the highest abstraction of any valid input to it.

Q: Why do you think your API design is good?
* I would say it essentially checks all the boxes: it encapsulates implementations details and methods are simple and readable.

