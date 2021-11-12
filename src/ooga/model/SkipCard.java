package ooga.model;

import javax.swing.*;

public class SkipCard extends ActionCard {
    public SkipCard(GameState g, String cardType) {
        super(g, cardType);
    }

    @Override
    public void executeAction(){
        GameState myGame = super.getGame();
        myGame.skipNextPlayer();
    }

    @Override
    public String getType() {
        return super.getType();
    }
}
