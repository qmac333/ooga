package ooga.model;

public abstract class Card implements CardInterface{

    private Player owner;
    private GameState game;
    private int num;
    private String color;


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

    protected GameState getGame(){return game;}

}
