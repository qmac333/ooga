package ooga.model.deck;

import ooga.model.CardFactory;
import ooga.model.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class UnoDeck extends CardPile{

    private CardFactory myCardFactory;

    public UnoDeck(String version){
        myCardFactory = new CardFactory();
        createDeck(version);
    }


    private void createDeck(String version){
        ResourceBundle deckProperties = ResourceBundle.getBundle(
                "ooga.model.gameState." + version + "Deck");

        List<String> colors = List.of(deckProperties.getString("Colors").split(","));
        List<String> actionCards = List.of(deckProperties.getString("ActionCards").split(","));
        int numActionCards = Integer.parseInt(deckProperties.getString("NumberOfAction"));

        List<String> numberCards = List.of(deckProperties.getString("NumberCards").split(","));
        int numNumberCards = Integer.parseInt(deckProperties.getString("NumberOfNumber"));

        List<String> wildCards = List.of(deckProperties.getString("WildCards").split(","));
        int numWildCards = Integer.parseInt(deckProperties.getString("NumberOfWild"));

        List<Card> cards = new ArrayList<Card>();

        createCardsFromData(colors, numActionCards, actionCards, cards);
        createCardsFromData(colors, numNumberCards, numberCards, cards);
        createCardsFromData(colors, numWildCards, wildCards, cards);

        Collections.shuffle(cards);

        super.placeOnTop(cards);

    }

    private void createCardsFromData(List<String> colorList,
                                     int numCards,
                                     List<String> cardTypeList,
                                     List<Card> deckList){

        for(String type : cardTypeList){
            for(int i = 0; i < numCards; i++){
                Card newCard;
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
}
