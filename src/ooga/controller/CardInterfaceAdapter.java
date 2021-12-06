package ooga.controller;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import ooga.model.cards.CardInterface;
import ooga.model.cards.individualCards.DiscardColorCard;
import ooga.model.cards.individualCards.DrawFiveCard;
import ooga.model.cards.individualCards.DrawOneCard;
import ooga.model.cards.individualCards.DrawTwoCard;
import ooga.model.cards.individualCards.FlipCard;
import ooga.model.cards.individualCards.NumberCard;
import ooga.model.cards.individualCards.ReverseCard;
import ooga.model.cards.individualCards.SkipCard;
import ooga.model.cards.individualCards.SkipEveryoneCard;
import ooga.model.cards.individualCards.WildBlastCard;
import ooga.model.cards.individualCards.WildCard;
import ooga.model.cards.individualCards.WildDrawColorCard;
import ooga.model.cards.individualCards.WildDrawFourCard;
import ooga.model.cards.individualCards.WildDrawTwoCard;

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
