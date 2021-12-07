package ooga.model.deck;

import java.lang.reflect.Array;
import java.util.*;
import ooga.model.instanceCreation.CardFactory;
import ooga.model.cards.CardInterface;
import ooga.model.cards.OneSidedCard;
import ooga.model.cards.TwoSidedCard;

public class UnoDeck extends CardPile{

    private CardFactory myCardFactory;
    private String deckVersion;

    public UnoDeck(String version){
        deckVersion = version;
        myCardFactory = new CardFactory();
        createDeck();
    }


    private void createDeck(){
        ResourceBundle deckProperties = ResourceBundle.getBundle(
                "ooga.model.gameState." + deckVersion + "Deck");

        List<String> colors = List.of(deckProperties.getString("Colors").split(","));
        List<String> actionCards = List.of(deckProperties.getString("ActionCards").split(","));
        int numActionCards = Integer.parseInt(deckProperties.getString("NumberOfAction"));

        List<String> numberCards = List.of(deckProperties.getString("NumberCards").split(","));
        int numNumberCards = Integer.parseInt(deckProperties.getString("NumberOfNumber"));

        List<String> wildCards = List.of(deckProperties.getString("WildCards").split(","));
        int numWildCards = Integer.parseInt(deckProperties.getString("NumberOfWild"));

        List<String> twoSidedCardFronts;
        List<String> twoSidedCardBacks;
        List<String> immutableTwoSidedCardBacks;
        int numTwoSidedCards;
        try{
            twoSidedCardFronts = List.of(deckProperties.getString("TwoSidedCardFronts").split(","));
            immutableTwoSidedCardBacks = List.of(deckProperties.getString("TwoSidedCardBacks").split(","));
            twoSidedCardBacks = new ArrayList<String>();
            for(String card : immutableTwoSidedCardBacks){
                twoSidedCardBacks.add(card);
            }
            Collections.shuffle((ArrayList) twoSidedCardBacks);
            numTwoSidedCards = Integer.parseInt(deckProperties.getString("NumberOfTwoSided"));
        }
        catch(MissingResourceException e){
            twoSidedCardFronts = null;
            twoSidedCardBacks = null;
            numTwoSidedCards = -1;
        }
        List<CardInterface> cards = new ArrayList<CardInterface>();

        createCardsFromData(colors, numActionCards, actionCards, cards);
        createCardsFromData(colors, numNumberCards, numberCards, cards);
        createCardsFromData(List.of("Black"), numWildCards, wildCards, cards);
        if(numTwoSidedCards != -1){
            createCardsFromData(colors, numTwoSidedCards,
                    twoSidedCardFronts, twoSidedCardBacks,
                    cards);
        }
        Collections.shuffle(cards);

        super.placeOnTop(cards);

    }

    private void createCardsFromData(List<String> colorList,
                                     int numCards,
                                     List<String> cardTypeList,
                                     List<CardInterface> deckList){

        for(String type : cardTypeList){
            for(int i = 0; i < numCards; i++){
                OneSidedCard newCard;
                for(String color : colorList){

                    newCard = getOneSidedCard(color, type);
                    deckList.add(newCard);
                }
            }
        }
    }

    private void createCardsFromData(List<String> colorList,
                                     int numCards,
                                     List<String> cardFronts,
                                     List<String> cardBacks,
                                     List<CardInterface> deckList){
        String type1;
        String type2;
        for(int i = 0; i < cardFronts.size(); i++){
            type1 = cardFronts.get(i);
            type2 = cardBacks.get(i);
            for(int j = 0; j < numCards; j++){
                OneSidedCard newFront;
                OneSidedCard newBack;
                for(String color : colorList){

                    if(type1.startsWith("Wild")) {
                        newFront = getOneSidedCard("Black", type1);
                    }
                    else{
                        newFront = getOneSidedCard(color, type1);
                    }
                    if(type2.startsWith("Wild")) {
                        newBack = getOneSidedCard("Black", type2);
                    }
                    else{
                        newBack = getOneSidedCard(color, type2);
                    }

                    TwoSidedCard newCard = new TwoSidedCard(newFront, newBack);
                    deckList.add(newCard);
                }
            }

        }
    }

    private OneSidedCard getOneSidedCard(String color, String type) {
        OneSidedCard newCard;
        try{
            int n = Integer.parseInt(type);
            newCard = myCardFactory.makeCard("Number", n, color);
        }
        catch(NumberFormatException e){
            newCard = myCardFactory.makeCard(type, -1, color);
        }
        return newCard;
    }
}
