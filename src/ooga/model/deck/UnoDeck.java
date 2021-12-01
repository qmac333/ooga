package ooga.model.deck;

import java.util.*;
import java.util.function.Supplier;
import ooga.model.CardFactory;
import ooga.model.cards.CardInterface;
import ooga.model.cards.OneSidedCard;
import ooga.model.cards.TwoSidedCard;

public class UnoDeck extends CardPile{

    private CardFactory myCardFactory;

    public UnoDeck(String version, Map<String, Supplier<String>> map){
        myCardFactory = new CardFactory();
        createDeck(version, map);
    }


    private void createDeck(String version, Map<String, Supplier<String>> map){
        ResourceBundle deckProperties = ResourceBundle.getBundle(
                "ooga.model.gameState." + version + "Deck");

        List<String> colors = List.of(deckProperties.getString("Colors").split(","));
        List<String> actionCards = List.of(deckProperties.getString("ActionCards").split(","));
        int numActionCards = Integer.parseInt(deckProperties.getString("NumberOfAction"));

        List<String> numberCards = List.of(deckProperties.getString("NumberCards").split(","));
        int numNumberCards = Integer.parseInt(deckProperties.getString("NumberOfNumber"));

        List<String> wildCards = List.of(deckProperties.getString("WildCards").split(","));
        int numWildCards = Integer.parseInt(deckProperties.getString("NumberOfWild"));

        List<String> twoSidedCardFronts;
        List<String> twoSidedCardBacks;
        int numTwoSidedCards;
        try{
            twoSidedCardFronts = List.of(deckProperties.getString("TwoSidedCardFronts").split(","));
            twoSidedCardBacks = List.of(deckProperties.getString("TwoSidedCardBacks").split(","));
            numTwoSidedCards = Integer.parseInt(deckProperties.getString("NumberOfTwoSided"));
        }
        catch(MissingResourceException e){
            twoSidedCardFronts = null;
            twoSidedCardBacks = null;
            numTwoSidedCards = -1;
        }
        List<CardInterface> cards = new ArrayList<CardInterface>();

        createCardsFromData(colors, numActionCards, actionCards, cards, map);
        createCardsFromData(colors, numNumberCards, numberCards, cards, map);
        createCardsFromData(colors, numWildCards, wildCards, cards, map);
        if(numTwoSidedCards != -1){
            createCardsFromData(colors, numTwoSidedCards, twoSidedCardFronts, cards, map);
        }
        Collections.shuffle(cards);

        super.placeOnTop(cards);

    }

    private void createCardsFromData(List<String> colorList,
                                     int numCards,
                                     List<String> cardTypeList,
                                     List<CardInterface> deckList,
                                     Map<String,
                                             Supplier<String>> map){

        for(String type : cardTypeList){
            for(int i = 0; i < numCards; i++){
                OneSidedCard newCard;
                for(String color : colorList){

                    try{
                        int n = Integer.parseInt(type);
                        newCard = myCardFactory.makeCard("Number", n, color);
                    }
                    catch(NumberFormatException e){
                        newCard = myCardFactory.makeCard(type, -1, color);
                    }
                    deckList.add(newCard);
                }
            }
        }
    }

    private void createCardsFromData(List<String> colorList,
                                     int numCards,
                                     List<String> cardFronts,
                                     List<String> cardBacks,
                                     List<CardInterface> deckList,
                                     Map<String,
                                             Supplier<String>> map){
        String type1;
        String type2;
        for(int i = 0; i < cardFronts.size(); i++){
            type1 = cardFronts.get(i);
            type2 = cardBacks.get(i);
            for(int j = 0; j < numCards; j++){
                OneSidedCard newFront;
                OneSidedCard newBack;
                for(String color : colorList){

                    newFront = getOneSidedCard(color, type1);
                    newBack = getOneSidedCard(color, type2);
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
