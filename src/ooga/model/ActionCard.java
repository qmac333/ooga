package ooga.model;

public abstract class ActionCard extends Card{

    private final String type;

    public ActionCard(GameState g, String cardType) {
        super(g);
        type = cardType;
    }

    @Override
    public String getType(){
        return type;
    }


}
