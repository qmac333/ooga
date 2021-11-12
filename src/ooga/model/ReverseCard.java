package ooga.model;

public class ReverseCard extends ActionCard{
    public ReverseCard(GameState g) {
        super(g);
    }

    @Override
    public void executeAction(){
        GameState myGame = super.getGame();
        myGame.reverseGamePlay();
    }
}
