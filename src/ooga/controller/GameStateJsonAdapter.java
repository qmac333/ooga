package ooga.controller;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import ooga.model.cards.CardInterface;
import ooga.model.deck.CardPile;
import ooga.model.gameState.GameState;
import ooga.model.hand.Hand;

import java.util.*;

/**
 * Class used to tell Moshi how to convert between GameStateJson (dummy class that has instance variables of the exact
 * same format as the JSON file) and GameState (the actual model class)
 */
public class GameStateJsonAdapter {
    @FromJson GameState gameStateFromJson(GameStateJson myGameStateJson){
        String version = myGameStateJson.getVersion();
        Map<String, String> playerMap = myGameStateJson.getPlayerMap();
        int points = myGameStateJson.getPoints();
        boolean stackable = myGameStateJson.getStackable();

        GameState myGameState = new GameState(version, playerMap, points, stackable);
        if(myGameStateJson.getMyHands() != null){
            int currentPlayer = myGameStateJson.getCurrentPlayer();
            List<Hand> myHands = myGameStateJson.getMyHands();

            List<CardInterface> myDiscardList = myGameStateJson.getMyDiscardList();
            CardPile myDiscardPile = cardListToPile(myDiscardList);

            List<CardInterface> myDeckList = myGameStateJson.getMyDeckList();
            CardPile myDeck = cardListToPile(myDeckList);

            int impendingDraw = myGameStateJson.getImpendingDraw();
            boolean skipNext = myGameStateJson.getSkipNext();
            boolean skipEveryone = myGameStateJson.getSkipEveryone();
            int order = myGameStateJson.getOrder();
            boolean uno = myGameStateJson.getUno();
            myGameState.loadExistingGame(currentPlayer, myHands, myDiscardPile, myDeck, impendingDraw, skipNext,
                    skipEveryone, order, uno);
        }
        return myGameState;
    }

    @ToJson GameStateJson gameStateToJson(GameState myGameState){
        String version = myGameState.getVersion();
        Map<String, String> playerMap = myGameState.getPlayerMap();
        int points = myGameState.getPointsToWin();
        boolean stackable = myGameState.getStackable();

        int currentPlayer = myGameState.getCurrentPlayer();
        List<Hand> myHands = myGameState.getMyHands();

        CardPile myDiscardPile = myGameState.getMyDiscardPile();
        List<CardInterface> myDiscardList = cardPileToList(myDiscardPile);

        CardPile myDeck = myGameState.getMyDeck();
        List<CardInterface> myDeckList = cardPileToList(myDeck);

        int impendingDraw = myGameState.getImpendingDraw();
        boolean skipNext = myGameState.getSkipNext();
        boolean skipEveryone = myGameState.getSkipEveryone();
        int order = myGameState.getOrder();
        boolean uno = myGameState.getUno();

        GameStateJson myGameStateJson = new GameStateJson(version, playerMap, points, stackable, currentPlayer, myHands,
                myDiscardList, myDeckList, impendingDraw, skipNext, skipEveryone, order, uno);
        return myGameStateJson;
    }

    private List<CardInterface> cardPileToList(CardPile pile){
        Stack<CardInterface> copiedStack = (Stack<CardInterface>) pile.getStack().clone();
        List<CardInterface> cardList = new ArrayList<>();
        for(int i = 0; i < pile.getNumCards(); i++){
            cardList.add(copiedStack.pop());
        }
        return cardList;
    }

    private CardPile cardListToPile(List<CardInterface> cardList){
        CardPile pile = new CardPile();
        for(int i = cardList.size() - 1; i > -1; i--){
            pile.placeOnTop(cardList.get(i));
        }
        return pile;
    }
}
