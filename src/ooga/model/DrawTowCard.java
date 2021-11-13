package ooga.model;

public class DrawTowCard extends ActionCard{

    public DrawTowCard(GameState g, String cardType) {
        super(g, cardType);
    }
    
    @Override
    public void executeAction(){
        GameState game = super.getGame();
        game.setNextPlayerDrawTwo(true);
    }
}
