ooga
====

This project implements a player for multiple related games.

Names: Paul Truitt, Andrew Sander, Quentin Macfarlane, Drew Peterson, Will Long


### Timeline

Start Date: 11/1/21

Finish Date: 12/7/21

Hours Spent:

### Primary Roles

Paul Truitt: Model. Implementation of Uno Rules, Flip and Blast Cards, Player and Group Interaction.
Creation of Draw Logic.

Will Long: Initial backend design. Deck creation and deck classes.

Quentin MacFarlane: View. Splash Screen creation. Languages and Themes.

Andrew Sander: View. Uno Game screen. Model View Communication.

Drew Peterson: Parsing of Data files. Saving and loading of games using json. Error Checking.


### Resources Used

Images:

[Ultra Board Games](https://www.ultraboardgames.com/uno/variants.php) for images relating to Basic Uno, Uno Flip, and Uno Blast

[Wizarding World](https://www.wizardingworld.com) website for Harry Potter related mod images

[Harry Potter Fandom](https://harrypotter.fandom.com/wiki/Main_Page) for more Harry Potter related mod images

[Duke Basketball Players Images](https://goduke.com/sports/mens-basketball/roster) for the Duke Basketball mod

### Running the Program

Main class: Main.java

Data files needed: None needed. Optional data files in data/configuration_files/Example Files


Features implemented: Uno game that can be played from either data files or from home
screen. There are UnoFlip and UnoBlast variations. We also have a Harry Potter mod version of
the game. There are both Human and computer players, and you can play with as many as you'd like.
Lastly, users can save the game in progress and reload it to the exact same position.

For adding more mods, modify the following configuration files in the resources.mods package:

* (Language Input).properties for specifying each of the different types of mods, where (Language Input) is a language to display a list of mod types with on the Splash Screen
* (Name Of Mod).properties for specifying the image paths of a particular type of mod, where (Name Of Mod) is a key in the (Language Input).properties files that is a type of mod

### Notes/Assumptions

Assumptions or Simplifications: One simplification would be in the drawing of the cards into the blaster.
If a user needs to draw 4 (not ever an occurrence in the Blast game), the game would draw four cards
and then insert them together in the blaster rather than one at a time.

Interesting data files: I would suggest /doc/presentation2/non-stacking.json and stacking.json
to explore the differences and potential agonies with the stacking rule on or off.

Known Bugs: Currently the program crashes when the deck runs out of cards.

Challenge Features: Loading and Saving game, Computer Players, Game Area Editor.


### Impressions

This was a fun project to implement and watch come to life. The workload was heavy but 
fulfilling. We learned a lot about design in the process.

### Cheat Keys

r g b y - changes all the colored cards to that type (r for red, y for yellow, etc.)

n - changes the hand to be seven yellow ones

w - adds a wild card to the hand

d - adds a +4 card to the hand

x - put a blue 1 on the discard pile, change the current player's hand to contain a single blue card, and set that player's points equal to (number of points to win - 1)

u - put a blue 1 on the discard pile, change the current player's hand to contain two blue cards

l - adds a blaster card to someone's hand

f - adds a flip card

s - adds a skip card

