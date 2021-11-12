package ooga.model;

import javax.swing.*;

public class SkipCard extends ActionCard {
    public SkipCard(GameState g) {
        super(g);
    }

    @Override
    public void executeAction(){
        GameState myGame = super.getGame();
        myGame.skipNextPlayer();
    }
}
