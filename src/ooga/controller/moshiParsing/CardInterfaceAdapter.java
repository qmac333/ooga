package ooga.controller.moshiParsing;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import ooga.model.cards.CardInterface;
import ooga.model.cards.DiscardColorCard;
import ooga.model.cards.DrawFiveCard;
import ooga.model.cards.DrawOneCard;
import ooga.model.cards.DrawTwoCard;
import ooga.model.cards.FlipCard;
import ooga.model.cards.NumberCard;
import ooga.model.cards.ReverseCard;
import ooga.model.cards.SkipCard;
import ooga.model.cards.SkipEveryoneCard;
import ooga.model.cards.WildBlastCard;
import ooga.model.cards.WildCard;
import ooga.model.cards.WildDrawColorCard;
import ooga.model.cards.WildDrawFourCard;
import ooga.model.cards.WildDrawTwoCard;

public class CardInterfaceAdapter {
    @FromJson
    CardInterface cardInterfaceFromJson(String json){
        String[] cardParameters = json.split("_");
        String cardType = cardParameters[0];
        String cardColor = cardParameters[1];
        int cardNumber = Integer.parseInt(cardParameters[2]);

        switch(cardType){
            case "DiscardColor": return new DiscardColorCard(cardColor);
            case "DrawFive" : return new DrawFiveCard(cardColor);
            case "DrawOne" : return new DrawOneCard(cardColor);
            case "DrawTwo" : return new DrawTwoCard(cardColor);
            case "Flip" : return new FlipCard(cardColor);
            case "Reverse" : return new ReverseCard(cardColor);
            case "Skip" : return new SkipCard(cardColor);
            case "SkipEveryone" : return new SkipEveryoneCard(cardColor);
            case "WildBlast" : return new WildBlastCard(cardColor);
            case "Wild" : return new WildCard(cardColor);
            case "WildDrawColor" : return new WildDrawColorCard(cardColor);
            case "WildDrawFour" : return new WildDrawFourCard(cardColor);
            case "WildDrawTwo" : return new WildDrawTwoCard(cardColor);
            default: return new NumberCard(cardColor, cardNumber);
        }
    }

    @ToJson
    String cardInterfaceToJson(CardInterface myCardInterface){
        String type = myCardInterface.getType();
        String color = myCardInterface.getMyColor();
        int number = myCardInterface.getNum();

        return type + "_" + color + "_" + number;
    }
}
