package ooga.controller.moshiParsing;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import ooga.model.cards.*;

public class CardInterfaceAdapter {
    @FromJson
    CardInterface cardInterfaceFromJson(String json){
        String[] cardParameters = json.split("_");
        String cardFrontType = cardParameters[0];
        String cardFrontColor = cardParameters[1];
        int cardFrontNumber = Integer.parseInt(cardParameters[2]);
        OneSidedCard cardFront = createOneSidedCard(cardFrontType, cardFrontColor, cardFrontNumber);
        if(cardParameters.length == 6){
            String cardBackType = cardParameters[3];
            String cardBackColor = cardParameters[4];
            int cardBackNumber = Integer.parseInt(cardParameters[5]);
            OneSidedCard cardBack = createOneSidedCard(cardBackType, cardBackColor, cardBackNumber);
            return new TwoSidedCard(cardFront, cardBack);
        }
        return cardFront;
    }

    private OneSidedCard createOneSidedCard(String cardType, String cardColor, int cardNumber) {
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
        String cardFrontType = myCardInterface.getType();
        String cardFrontColor = myCardInterface.getMyColor();
        int cardFrontNumber = myCardInterface.getNum();
        String cardFrontJson = cardFrontType + "_" + cardFrontColor + "_" + cardFrontNumber;
        if(myCardInterface.isTwoSided()){
            myCardInterface.flip();
            String cardBackType = myCardInterface.getType();
            String cardBackColor = myCardInterface.getMyColor();
            int cardBackNumber = myCardInterface.getNum();
            String cardBackJson = cardBackType + "_" + cardBackColor + "_" + cardBackNumber;
            return cardFrontJson + "_" + cardBackJson;
        }
        return cardFrontJson;
    }
}
