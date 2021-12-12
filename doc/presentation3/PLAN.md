run the program from the master branch through a planned series of steps/interactions that shows at least the following features:
choosing a game to play, playing a game, winning or losing a game, choosing a different game to play and starting that one

* Start BasicUno
* Play through till one player runs out of cards, showing the different features of the game
* Then press the 'x' cheat key that allows you to win
* Then do the same for Blast and Flip, playing the blast and flip cards as appropriate

playing two different games at the same time

* Drew shows saving a game, then creating a new one, saving that one, and then loading the old one

any Extension features implemented 

* Paul explains AI
* Quentin explains the Splash Screen GUI

show an example of each kind of data file used by the program and describe which are essential (e.g., internal resources) and which can be user created (e.g., external or example data)
show three examples of making a change in a data file and then seeing that change reflected when the program is run

* View: show the data files related to mods
* Take away a key from the mods data file, show the effect on the drop down menu

Design. Revisit the design from the original plan and compare it to your current version (as usual, focus on the behavior and communication between modules, not implementation details):
revisit the design's goals: is it as flexible/open as you expected it to be and how have you closed the core parts of the code in a data driven way?
describe two APIs in detail:
show the public methods for the API

* Update()

* Controller API
  * Show the UnoController diagram
  * As a service, the Controller's API provides the view's classes with user input handling (setting initial parameters, 
  loading/saving files), game flow control ("Back" and "Play" buttons), access to the GameState's view-specific interface,
  and information about the game's current parameters (specifically after loading a file on the splash screen to update
  the manual parameter editor)
  * The API is defined in class-specific interfaces that prevent the view from accessing irrelevant or restricted state
  and methods
  * To extend the API, all one has to do is add a method signature to the interface, which immediately gives the view access
    (means you can add a previously private method to the API with only a single added line of code)
  * The API supports the view team in their effort to write readable, well-designed code because each method in the API
  generally corresponds to a unique UI command (there is one method to handle loading, saving, clicking play, setting
  manual game parameters, etc.). In this way, it is hard to misuse the API. It also supports extension because if the view
  team wants to add a user option/capability to the view that needs to ultimately be handled by the controller, they can
  do so by adding a single method to the relevant controller interface
  * Luckily, the controller interface has remained relatively constant throughout, with the only big change coming from 
  model step control being moved full-time to the view (no timeline to call a .step() method in the controller)

Called in each of the classes in the subdisplays module
Updates the particular display to reflect the current properties of the game (ex. for HandListDisplay, the current player's hand, plus some buttons for drawing cards and calling UNO)
MainDisplay class calls update() as part of the game loop (i.e. after someone takes their turn)

* Allows view programmers to work in separate classes for different sections of the display, modularize code
* update() is extremely generic- any JavaFX object can be used to display data, and the update call doesn't have to change
* Classes changed from implementing Consumer interface - as more features are added on, easier for the view to call model APIs rather than for the Consumer to keep taking in more arguments as more features need to be displayed


how does it provide a service that is open for extension to support easily adding new features?
how does it support users (your team mates) to write readable, well design code, and encourage extensions?
how has it changed during the Sprints (if at all)?
show two Use Cases implemented in Java code in detail that show off how to use each of the APIs described above

describe two designs
one that has remained stable during the project

* One design that remained stable over the project is the separation of Rules into its own class.
  * Seperated into its own classes
  * Talk about Rule Group and how it and all rules implement the Rule interface
  * Talk about extendability
  * Customizable
* Changing Design:
  * The decks
  * Initial stacks
  * encapsulation into Deck and Discard Piles
  * Issue with that
  * Creation of deckWrapper to solve this.



Suppliers:
* When the game requires a user's input (i.e. to select the index of the card being played), the model class that requires that information will call the view through a Supplier interface, which will return the required information depending on the type of supplier.
* Example: Supplier in the HumanPlayer class that returns the index of the card that the user selected
* Allows for generic APIs for playing turns (playTurn() doesn't have to take any arguments), and the model will only call the view for information when it needs to
* (i.e. the view doesn't need to select an index to play for a computer player, or ask for a color when a non-wild card is played)


one that has changed significantly based on your deeper understanding of the project: how were those changes discussed and what trade-offs ultimately led to the changes

Team. Present what your team and, you personally, learned from managing this project:

contrast the completed project with where you planned it to be in your initial Wireframe and the initial planned priorities and Sprints with the reality of when things were implemented
Individually, share one thing each person learned from using the Agile/Scrum process to manage the project.
show a timeline of at least four significant events (not including the Sprint deadlines) and how communication was handled for each (i.e., how each person was involved or learned about it later)

* Figuring out how to display cards (colors, images)
* Figuring out how to implement clicking on cards in order to play them


Individually, share one thing each person learned from trying to manage a large project yourselves.
describe specific things the team actively worked to improve on during the project and one thing that could still be improved
* Creating "safe", immutable APIs in which game data can be accessed by the view, but not updated
* Example: information about player card counts, names, etc.

Individually, share one thing each person learned about creating a positive team culture.
revisit your Team Contract to assess what parts of the contract are still useful and what parts need to be updated (or if something new needs to be added)
Individually, share one thing each person learned about how to communicate and solve problems collectively, especially ways to handle negative team situations.