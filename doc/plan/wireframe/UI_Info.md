# About Our UI

Pictures of our UI are included in this folder.
Here is a description of how a user will play UNO:

* When the user loads up the application, they will be presented with a splash screen (see UI Image 2). The splash screen will allow them to load in a configuration file specifying what the current game will look like, or they can set up the game from the UI (number of players, rules, etc.).
The user can also load in a current game that they saved. Clicking play will launch the main game.
* In the main game itself, the user has the ability to click on cards to play them. When a card is played, it will go on top of the discard pile so that the next player can play a valid card.
* Some erroneous actions that the user could take would be to load in an invalid configuration file (i.e. not JSON format, or not containing the required keys) attempting to save the file to an invalid location or load from an invalid location on the file system. In those cases, the program would display an error message specifying what needed to be fixed. The user could click OK and then fix their error.
