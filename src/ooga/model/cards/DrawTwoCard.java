package ooga.model.cards;

import ooga.model.gameState.GameState;

public class DrawTwoCard extends ActionCard {
    private final int DRAW_AMOUNT = 2;

    public DrawTwoCard(GameState g, String color, String type) {
        super(g, color, type);
    }

    @Override
    public void executeAction(){
        GameState game = super.getGame();
        game.addDraw(DRAW_AMOUNT);
        game.discardCard(this);
    }
}
