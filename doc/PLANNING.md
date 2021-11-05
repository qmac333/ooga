# OOGA Plan Discussion
### un05
### Paul Truitt, Will Long, Quentin MacFarlane, Drew Peterson, Andrew Sander


## Project goals

Multiple working versions of Uno. Well-designed. We want the user to have a good experience (not
deal with unfortunate issues).

## Project Emphasis

More focused on backend. Extending well to handle multiple game types.


## Project Extensions
* Basic: Loading and Saving games
* Mild: Artificial Players
* Challenging: Loading and Saving from the web, Game area modification

## Project Progress

#### Sprint 1 (Test)

General: Have the main version of the game almost done. It's okay if there are some features that still need
to be added. Hopefully be able to play it. Good test coverage. Basic AI player.

Model: Original game logic created. Have abstractions defined where we can extend for next versions.
Determine winners, Keeps score, detect valid cards to play, rotating through players. Process changes in 
player ordering. Running out of cards.

View: Show cards for current player. Discard pile, draw pile. Selecting card. Other players cards.
Home Screen. General configuration (type of game, rule selections, Card info, Number of players, Order 
of players, Types of players (Maybe sprint 2))

Controller: Parsing files.

#### Sprint 2 (Basic)

General: Finalize/polish off the main version. Have some work done on other versions (At least the MOD version).
Rule changing support. Moderate AI.

Model: Everything we couldn't get in sprint 1. Draw card logic.

View: Supporting MOD. Changing of rules (Blocking #/Color, stacking, playing multiple cards).
Passing screen.

Controller:

#### Sprint 3 (Complete)

General: Have everything working.

Model: Logic in for the other versions. All tested. Advanced AI player

View: Blast animation. Card flipping.

Controller: 

## Project Roles

Backend: Paul, Will

Controller: Drew

Front-End: Andrew, Quentin