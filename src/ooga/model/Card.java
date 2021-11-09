package ooga.model;

public class Card implements CardInterface{

    private Player owner;
    private GameState game;

    public Card(GameState g){
        game = g;
    }

    @Override
    public void executeAction() {
    }

    @Override
    public void setPlayer(Player p) {
        owner = p;
        p.addCard(this);
    }
}
