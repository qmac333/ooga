## Description

Missed Uno Pop-up not appearing when a human player forgets to call UNO before they play their second to last card

## Expected Behavior

If the button "Call UNO" that appears when a human player is taking their turn is not pressed before the player plays their second to last card,
a pop-up message should appear, stating that the player forgot to call UNO and will take a penalty in which they have to draw a certain number of cards.

## Current Behavior

If the button is not pressed when the player has two cards, and they go to play a card, the model correctly applies the draw penalty so that they have to draw two extra cards, but no pop-up appears on the screen
making the penalty clear to the player, and more importantly why they got the penalty.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

* Press the 'U' key on a human player's turn to put two cards in their hand.
* Click on a card to play it WITHOUT pressing the "Called Uno" button. No pop-up is displayed bringing the penalty to the user's attention.

## Failure Logs

N/A

## Hypothesis for Fixing the Bug

To test if the bug is fixed, simply check to see if there is a DialogPane in the scene after playing the second to last card without pressing the "Called UNO" button.
Code to fix the bug will include updating the callback function that is run when a valid card is clicked. The callback function will check the model to see if a penalty needs to be applied for not calling UNO, and if so display a pop up before calling the playTurn() function in GameState and playing the card.
