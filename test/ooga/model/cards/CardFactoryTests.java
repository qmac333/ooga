package ooga.model.cards;

import ooga.model.CardFactory;
import ooga.model.cards.individualCards.NumberCard;
import ooga.model.cards.individualCards.ReverseCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class CardFactoryTests extends DukeApplicationTest {

    CardFactory factory;

    @BeforeEach
    public void initializeFactory(){factory = new CardFactory();}


    @Test
    public void initializeNumberCardTest(){
        try {
            NumberCard num = (NumberCard) factory.makeCard("Number", 1, "red");
            assertEquals(1, num.getNum());
            assertEquals("red", num.getMyColor());
        }
        catch(ClassCastException e){
            System.out.println("card returned not of the right class");
            Assertions.fail();
        }
    }

    @Test
    public void initializeActionCardTest(){
        try {
            ReverseCard num = (ReverseCard) factory.makeCard("Reverse", 1, "red");
            assertEquals("red", num.getMyColor());
        }
        catch(ClassCastException e){
            System.out.println("card returned not of the right class");
            Assertions.fail();
        }
    }
}
