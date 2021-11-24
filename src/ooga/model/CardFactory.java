package ooga.model;

import java.util.function.Supplier;
import ooga.model.cards.Card;
import ooga.model.cards.NumberCard;


/**
 * This class serves to create a new instance of a card given a card type, an integer for the card,
 * and a color for the card.  It should be used when initializing all the new cards for a game.
 */
public class CardFactory {


    public Card makeCard(String type, int n, String c)  {
        return makeCard(type, n, c, null);
    }

    public Card makeCard(String type, int n, String c, Supplier<String> supplier) {
        if(type == "Number"){
            return new NumberCard(c, n);
        }
        else{
            String cardType = "ooga.model.cards." + type + "Card";
            try {
                Class<?> cardClass = Class.forName(cardType);
                Card newCard = (Card) cardClass.getDeclaredConstructor(String.class, Supplier.class).newInstance(c, supplier);
                return newCard;
            } catch (Exception e) {
                System.out.println("issue with creating new ActionCard");
            }
        }
        return null;
    }
}
