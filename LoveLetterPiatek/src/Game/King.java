package Game;

import Server.Server;

/**
 * This class creates the King card which contains functionality and attributes unique to this class
 * Created by padcf & paulvincentphillips on 01/11/16.
 */

public class King implements Card {
    private int cardValue = 6;
    private String cardName = "KING";
    private String cardAbility = "Trade hands with another player of your choice.";


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


        //having chosen guard card, we now want to choose a player to apply that card on.
        //loop around until a player has been chosen. Then do what needs to be done.

        while (true) {

            System.out.println("Current player: " + currentPlayer.getPlayerID());
            System.out.println("Choose a player");
            int playerChoice = Integer.parseInt(Server.tokens[1]);
            System.out.println(playerChoice);

            //deal with exceptional input
            if (playerChoice == (currentPlayer.getPlayerID())) {
                System.out.println("YOU FOOL - CAN'T TRADE HANDS WITH YOURSELF");
                Server.outError = true;
            }
            //check to see if targetPlayer1 is still in the round and do targetPlayer1 stuff
            else if (playerChoice == (targetPlayer1.getPlayerID())) {

                if (!targetPlayer1.getIsPlaying()) {
                    System.out.println("This player is already out of the round");
                    Server.outError = true;
                } else if (targetPlayer1.getIsPlayedHandmaiden()) {
                    System.out.println("This player has played the handmaid and is immune until their next turn");
                } else {
                    //check to see which card slot is currently occupied by king
                    //then swap card which isn't king with target player and vice versa
                    if (currentPlayer.getCard1().getCardName().equals("KING")) {
                        Card temp = targetPlayer1.getCard1();
                        targetPlayer1.setCard1(currentPlayer.getCard2());
                        currentPlayer.setCard1(temp);
                        Server.newCard = temp.getCardName();    //new card for current player
                        Server.newCard1 = currentPlayer.getCard2().getCardName();   //new card for targeted player
                    } else {
                        Card temp = targetPlayer1.getCard1();
                        targetPlayer1.setCard1(currentPlayer.getCard1());
                        currentPlayer.setCard1(temp);
                        Server.newCard = temp.getCardName();    //new card for current player
                        Server.newCard1 = currentPlayer.getCard1().getCardName();   //new card for targeted player
                    }
                    System.out.println(currentPlayer.getPlayerName() + " id:" + currentPlayer.getPlayerID() + " have traded hands with " + targetPlayer1.getPlayerID());
                }
                break;
            }
            //check to see if targetPlayer2 is still in the round and do targetPlayer2 stuff
            else if (playerChoice == (targetPlayer2.getPlayerID())) {

                if (!targetPlayer2.getIsPlaying()) {
                    System.out.println("This player is already out of the round");
                    Server.outError = true;
                } else if (targetPlayer2.getIsPlayedHandmaiden()) {
                    System.out.println("This player has played the handmaid and is immune until their next turn");
                } else {
                    //check to see which card slot is currently occupied by king
                    //then swap card which isn't king with target player and vice versa
                    if (currentPlayer.getCard1().getCardName().equals("KING")) {
                        Card temp = targetPlayer2.getCard1();
                        targetPlayer2.setCard1(currentPlayer.getCard2());
                        currentPlayer.setCard1(temp);
                        Server.newCard = temp.getCardName();    //new card for current player
                        Server.newCard1 = currentPlayer.getCard2().getCardName();   //new card for targeted player
                    } else {
                        Card temp = targetPlayer2.getCard1();
                        targetPlayer2.setCard1(currentPlayer.getCard1());
                        currentPlayer.setCard1(temp);
                        Server.newCard = temp.getCardName();    //new card for current player
                        Server.newCard1 = currentPlayer.getCard1().getCardName();   //new card for targeted player
                    }
                    System.out.println(currentPlayer.getPlayerName() + " id:" + currentPlayer.getPlayerID() + " have traded hands with " + targetPlayer2.getPlayerID());

                }
                break;
            }
            //check to see if targetPlayer3 is still in the round and do targetPlayer3 stuff
            else if (playerChoice == (targetPlayer3.getPlayerID())) {

                if (!targetPlayer3.getIsPlaying()) {
                    System.out.println("This player is already out of the round");
                    Server.outError = true;
                } else if (targetPlayer3.getIsPlayedHandmaiden()) {
                    System.out.println("This player has played the handmaid and is immune until their next turn");
                } else {
                    //check to see which card slot is currently occupied by king
                    //then swap card which isn't king with target player and vice versa
                    if (currentPlayer.getCard1().getCardName().equals("KING")) {
                        Card temp = targetPlayer3.getCard1();
                        targetPlayer3.setCard1(currentPlayer.getCard2());
                        currentPlayer.setCard1(temp);
                        Server.newCard = temp.getCardName();    //new card for current player
                        Server.newCard1 = currentPlayer.getCard2().getCardName();   //new card for targeted player
                    } else {
                        Card temp = targetPlayer3.getCard1();
                        targetPlayer3.setCard1(currentPlayer.getCard1());
                        currentPlayer.setCard1(temp);
                        Server.newCard = temp.getCardName();    //new card for current player
                        Server.newCard1 = currentPlayer.getCard1().getCardName();   //new card for targeted player
                    }
                    System.out.println(currentPlayer.getPlayerName() + " id:" + currentPlayer.getPlayerID() + " have traded hands with " + targetPlayer3.getPlayerID());
                    break;
                }
            }
            //check to see if targetPlayer4 is still in the round and do targetPlayer3 stuff
            else if (playerChoice == (targetPlayer4.getPlayerID())) {

                if (!targetPlayer4.getIsPlaying()) {
                    System.out.println("This player is already out of the round");
                    Server.outError = true;
                } else if (targetPlayer4.getIsPlayedHandmaiden()) {
                    System.out.println("This player has played the handmaid and is immune until their next turn");
                } else {
                    //check to see which card slot is currently occupied by king
                    //then swap card which isn't king with target player and vice versa
                    if (currentPlayer.getCard1().getCardName().equals("KING")) {
                        Card temp = targetPlayer4.getCard1();
                        targetPlayer4.setCard1(currentPlayer.getCard2());
                        currentPlayer.setCard1(temp);
                        Server.newCard = temp.getCardName();    //new card for current player
                        Server.newCard1 = currentPlayer.getCard2().getCardName();   //new card for targeted player
                    } else {
                        Card temp = targetPlayer4.getCard1();
                        targetPlayer4.setCard1(currentPlayer.getCard1());
                        currentPlayer.setCard1(temp);
                        Server.newCard = temp.getCardName();    //new card for current player
                        Server.newCard1 = currentPlayer.getCard1().getCardName();   //new card for targeted player
                    }
                    System.out.println(currentPlayer.getPlayerName() + " id:" + currentPlayer.getPlayerID() + " have traded hands with " + targetPlayer4.getPlayerID());
                }
            } else break;
            break;
        }
        return length;
    }
}
