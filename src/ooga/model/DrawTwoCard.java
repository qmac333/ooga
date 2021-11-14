package ooga.model;

public class DrawTwoCard extends ActionCard{
    private final int DRAW_AMOUNT = 2;

    public DrawTwoCard(GameState g, String cardType) {
        super(g, cardType);
    }

    @Override
    public void executeAction(){
        GameState game = super.getGame();
        game.addDraw(DRAW_AMOUNT);
    }
}
