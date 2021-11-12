# Sprint Plan

## Sprint 1 (Test)

General: Have the main version of the game almost done. It's okay if there are some features that still need
to be added. Hopefully be able to play it. Good test coverage. Basic AI player.

Model: Original game logic created. Have abstractions defined where we can extend for next versions.
Determine winners, Keeps score, detect valid cards to play, rotating through players. Process changes in
player ordering. Running out of cards.

View: Show cards for current player. Discard pile, draw pile. Selecting card. Other players cards.
Home Screen. General configuration (type of game, rule selections, Card info, Number of players, Order
of players, Types of players (Maybe sprint 2))

Controller: Parsing files with separate parser class. Initializing controller based on parameters retrieved from configuration 
file. Setting up interaction between model and view through consumer interfaces. Running one step of the game by calling 
on the model.

## Sprint 2 (Basic)

General: Finalize/polish off the main version. Have some work done on other versions (At least the MOD version).
Rule changing support. Moderate AI.

Model: Everything we couldn't get in sprint 1. Draw card logic.

View: Supporting MOD. Changing of rules (Blocking #/Color, stacking, playing multiple cards).
Passing screen.

Controller: Saving files. More complicated step function that determines whether the model needs to wait for user input
before running the game. Additional services needed by the view. Refactor design.

## Sprint 3 (Complete)

General: Have everything working.

Model: Logic in for the other versions. All tested. Advanced AI player

View: Blast animation. Card flipping.

Controller: Use reflection to create the correct model-type depending on the game-type (Traditional, Blast, or Duo) indicated
in the configuration file. Any additional services needed by the view. Final refactoring and complete testing.