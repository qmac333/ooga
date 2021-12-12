# OOGA Design Final
### Names

## Team Roles and Responsibilities

 * Team Member #1

 * Team Member #2

 * Team Member #3

Will Long - Back End

 * Team Member #4

 * Team Member #5


## Design goals

#### What Features are Easy to Add

One feature that was easy to add was new variations of games.  This is primarily due to 
the model's reliance on abstractions of card implementations, so new cards corresponding to new 
games were easily integrated.


## High-level Design

#### Core Classes

Model
* GameState
* PlayerGroup
* Player
* DeckWrapper
* RuleSet
* DrawRuleSet


## Assumptions that Affect the Design

#### Features Affected by Assumptions


## Significant differences from Original Plan

In the model, we ended up redistributing a lot of responsibility away from the GameState class.
For example, Paul made a new PlayerGroup class that held all the players in a game and managed their
interactions.  Originally, all the players were held in the GameState.

## New Features HowTo

#### Easy to Add Features

#### Other Features not yet Done

