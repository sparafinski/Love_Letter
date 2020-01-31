package Game;

/**
 * This class creates the Handmaid card which contains functionality and attributes unique to this class
 * Created by padcf & paulvincentphillips on 01/11/16.
 */

public class Handmaiden implements Card {
    private int cardValue = 4;
    private String cardName = "HANDMAIDEN";
    private String cardAbility = "Until your next turn, ignore all effects from other players' cards.";


    @Override
    public int getCardValue() {
        return this.cardValue;
    }

    @Override
    public String getCardAbility() {
        return this.cardAbility;
    }

    @Override
    public String getCardName() {
        return this.cardName;
    }

    @Override
    public int specialFunction(Player currentPlayer, Player targetPlayer1, Player targetPlayer2, Player targetPlayer3, Player targetPlayer4, int length, Card[] deck) {
        currentPlayer.setPlayedHandmaiden(true);
        System.out.println(currentPlayer.getPlayerName() + " id: " + currentPlayer.getPlayerID() + " is immune this until his next turn");
        return length;
    }
}
