package ooga.model;

public class ReverseCard extends ActionCard{
    public ReverseCard(GameState g, String cardType) {
        super(g, cardType);
    }

    @Override
    public void executeAction(){
        GameState myGame = super.getGame();
        myGame.reverseGamePlay();
    }

    @Override
    public String getType() {
        return super.getType();
    }
}
