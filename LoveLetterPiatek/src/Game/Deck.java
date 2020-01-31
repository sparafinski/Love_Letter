package Game;

/**
 * This is the Deck class. This will create and organise the various cards
 * in an array.
 * You will be able to randomise the array, etc.
 * Created by padcf & paulvincentphillips on 01/11/16.
 */


public class Deck {

    //instantiate the deck
    Card[] deck = new Card[25];

    //getter method for the deck
    public Card[] getDeck() {
        return deck;
    }


    //shuffle the deck randomly
    public void shuffleDeck() {
        for (int i = 0; i < deck.length; i++) {
            int ranNum = (int) (Math.random() * deck.length);
            Card temp;
            temp = deck[i];
            deck[i] = deck[ranNum];
            deck[ranNum] = temp;
        }
    }

    //a method to populate the deck.
    public void populateDeck() {

        //10 guards
        for(int i=0; i<10; i++)             //0-9
        {
            deck[i] = new Guard();
        }

        //3 priests
        for(int i=10; i<13; i++)            //9-12
        {
            deck[i] = new Priest();
        }

        //5 Barons
        for(int i=13; i<18; i++)            //12-17
        {
            deck[i] = new Baron();
        }

        //2 Handmaids                       /18-19
        for(int i=18; i<20; i++)
        {
            deck[i] = new Handmaiden();
        }

        //2 princes
        for(int i=20; i<22; i++)            //20-21
        {
            deck[i] = new Prince();
        }

        //One King, Countess and Princess
        deck[22] = new King();              //22
        deck[23] = new Countess();          //23
        deck[24] = new Princess();          //24
//
//        //2 princes
//        for(int i=0; i<10; i++)            //20-21
//        {
//            deck[i] = new Prince();
//        }
//        for (int i = 0; i < 25; i++)            //20-21
//        {
//            deck[i] = new Princess();
//        }
    }

}
