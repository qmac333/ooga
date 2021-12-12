## Description

Interactive text not updating after cheat keys are pressed.

## Expected Behavior

For a human's turn, if the human presses a cheat key before they play a card, the interactive text appearing above the cards in the hand should change depending on whether the player can play a card from their new hand, or if they have to draw a card.

## Current Behavior

Pressing a cheat key updates the cards in the player's hand, but not the text prompting them to either play a card or draw. So for example, if a player has all red ones in their hand, and a blue two is on the discard pile,
the text displayed above the hand still states that the user cannot play their turn even if the user presses a cheat key that changes all of the cards in their hand to be blue.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

* Create a situation in which a human player cannot play cards in their hand.
* Now press a cheat key such that they hve at least one card to play after their hand is updated.
* Notice that the interactive text does not change, even though the player can play a card.

## Failure Logs

N/A

## Hypothesis for Fixing the Bug

To test if the bug is fixed, first place a red two on top of the discard pile, and then change the current player's hand to contain only blue ones by pressing the 'n' cheat key followed by the 'b' key.
At this point, the text on the screen should read that the user cannot play a card.
Then, press the 'r' key to change all cards in the player's hand to be red. The text should change to read that the player can click on a card to play it.

This bug should be easily fixable by changing the render() function to include the changeInteractiveInput() private function, which updates the text on the screen prompting the user to play a card, or draw. Before, changeInteractiveInput() was only being called at the end of a player's turn (i.e. in finishTurn()), but by putting it in render(), it will be called when a cheat key is pressed since the screen renders new changes after a cheat key press.

