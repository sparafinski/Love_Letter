package Game;

import Server.Server;

/**
 * This class creates the Priest card which contains functionality and attributes unique to this class
 * Created by padcf & paulvincentphillips on 01/11/16.
 */

public class Priest implements Card {
    private int cardValue = 2;
    private String cardName = "PRIEST";
    private String cardAbility = "Selected player skip next turn";


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
                System.out.println("Player " + currentPlayer.getPlayerID() + " will skip his turn!");
                currentPlayer.setInTour(false);
                break;
            }
            //check to see if targetPlayer1 is still in the round and do targetPlayer1 stuff
            else if (playerChoice == (targetPlayer1.getPlayerID())) {
                if (!targetPlayer1.getIsPlaying()) {
                    System.out.println(targetPlayer1.getPlayerID() + " This player is already out of the round");
                    Server.outError = true;
                    break;
                } else if (targetPlayer1.getIsPlayedHandmaiden()) {
                    System.out.println(targetPlayer1.getPlayerID() + " This player has played the handmaid and is immune until their next turn");
                    break;
                } else {
                    System.out.println("Player " + targetPlayer1.getPlayerID() + " will skip his turn!");
                    targetPlayer1.setInTour(false);
                }
                break;
            }
            //check to see if targetPlayer2 is still in the round and do targetPlayer2 stuff
            else if (playerChoice == (targetPlayer2.getPlayerID())) {
                if (!targetPlayer2.getIsPlaying()) {
                    System.out.println(targetPlayer2.getPlayerID() + " This player is already out of the round");
                    Server.outError = true;
                    break;
                } else if (targetPlayer2.getIsPlayedHandmaiden()) {
                    System.out.println(targetPlayer2.getPlayerID() + "This player has played the handmaid and is immune until their next turn");
                    break;
                } else {
                    System.out.println("Player " + targetPlayer2.getPlayerID() + " will skip his turn!");
                    targetPlayer2.setInTour(false);
                }
                break;
            }
            //check to see if targetPlayer3 is still in the round and do targetPlayer3 stuff
            else if (playerChoice == (targetPlayer3.getPlayerID())) {
                if (!targetPlayer3.getIsPlaying()) {
                    System.out.println(targetPlayer3.getPlayerID() + " This player is already out of the round");
                    Server.outError = true;
                    break;
                } else if (targetPlayer3.getIsPlayedHandmaiden()) {
                    System.out.println(targetPlayer3.getPlayerID() + " This player has played the handmaid and is immune until their next turn");
                    break;
                } else {
                    System.out.println("Player " + targetPlayer3.getPlayerID() + " will skip his turn!");
                    targetPlayer3.setInTour(false);
                    //System.out.println(targetPlayer1.getPlayerID() + "'s isPlaying variable is set to " + targetPlayer1.getIsPlaying());
                }
                break;
            }
            //check to see if targetPlayer3 is still in the round and do targetPlayer4 stuff
            else if (playerChoice == (targetPlayer4.getPlayerID())) {
                if (!targetPlayer4.getIsPlaying()) {
                    System.out.println(targetPlayer4.getPlayerID() + " This player is already out of the round");
                    Server.outError = true;
                    break;
                } else if (targetPlayer4.getIsPlayedHandmaiden()) {
                    System.out.println(targetPlayer4.getPlayerID() + " This player has played the handmaid and is immune until their next turn");
                    break;
                } else {
                    System.out.println("Player " + targetPlayer4.getPlayerID() + " will skip his turn!");
                    targetPlayer4.setInTour(false);
                    //System.out.println(targetPlayer1.getPlayerID() + "'s isPlaying variable is set to " + targetPlayer1.getIsPlaying());
                }
                break;
            } else if (playerChoice != (targetPlayer1.getPlayerID()) || playerChoice != (targetPlayer2.getPlayerID()) ||
                    playerChoice != (targetPlayer3.getPlayerID()) || playerChoice != (targetPlayer4.getPlayerID()) ||
                    playerChoice != (currentPlayer.getPlayerID())) {
                System.out.println("What?! no <Player ID> ");
                Server.outError = true;
                break;
            }
        }
        return length;
    }
}