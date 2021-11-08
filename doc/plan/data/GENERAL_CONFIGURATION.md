Here is the layout of a configuration file for an UNO game:

Keys:
* Version: Which game version is the party playing?
* MOD (Optional): a filepath to another JSON file that contains key-value pairs of labels and their corresponding mod images.
* Cards (Optional): a filepath to another JSON file that lists the key-value pairs of a label of a card and its corresponding count in the deck
* Players: An array containing key-value pairs of the player's name, and whether they are human or computer controlled.
* Points: The number of points required to win the game. 
* HandCount: Initial number of cards drawn by each player at the start of the game
* Stackable: value is true if the stackable rule applies (i.e. can stack +2 and +4 cards) and false if it does not apply.
