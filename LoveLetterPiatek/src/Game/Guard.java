package Game;

import Server.Server;

/**
 * This class creates the Guard card which contains functionality and attributes unique to this class
 * Created by padcf & paulvincentphillips on 01/11/16.
 */

public class Guard implements Card {

    private int cardValue = 1;
    private String cardName = "GUARD";
    private String cardAbility = "Name a non-Guard card and choose another player. \nIf that player has that card, he or she is out of the round.";


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
            String cardChoice = Server.tokens[2];

            var b = cardChoice.equals("PRIEST") || cardChoice.equals("BARON") || cardChoice.equals("HANDMAIDEN") || cardChoice.equals("PRINCE") ||
                    cardChoice.equals("KING") || cardChoice.equals("COUNTESS") || cardChoice.equals("PRINCESS");

            //check to see if targetPlayer1 is still in the round and do targetPlayer1 stuff
            if (playerChoice == (targetPlayer1.getPlayerID())) {
                if (!targetPlayer1.getIsPlaying()) {
                    System.out.println("This player is already out of the round");
                    Server.outError = true;
                } else if (targetPlayer1.getIsPlayedHandmaiden()) {
                    System.out.println("This player has played the handmaid and is immune until their next turn");
                } else {
                    System.out.println("Name a card to guess");
                    System.out.println(cardChoice);
                    if (cardChoice.equals(targetPlayer1.getCard1().getCardName())) {
                        System.out.println("You've guessed correctly! Player " + targetPlayer1.getPlayerName() + " is out of the round!");
                        targetPlayer1.setPlaying(false);
                        Server.eliminated = targetPlayer1.getPlayerID();
                    } else if (b) {
                        System.out.println("Player " + targetPlayer1.getPlayerName() + " does not have that card.");
                    } else {
                        System.out.println("Sorry, please chose your card again");
                        Server.outError = true;
                    }
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
                    System.out.println("Name a card to guess");
                    System.out.println(cardChoice);
                    if (cardChoice.equals(targetPlayer2.getCard1().getCardName())) {
                        System.out.println("You've guessed correctly! Player " + targetPlayer2.getPlayerName() + " is out of the round!");
                        targetPlayer2.setPlaying(false);
                        Server.eliminated = targetPlayer2.getPlayerID();
                        //System.out.println(targetPlayer2.getPlayerName() + "'s isPlaying variable is set to " + targetPlayer2.getIsPlaying());
                    } else if (b) {
                        System.out.println("Player " + targetPlayer2.getPlayerName() + " does not have that card.");
                    } else {
                        System.out.println("Sorry, please chose your card again");
                        Server.outError = true;
                    }
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
                    System.out.println("Name a card to guess");
                    System.out.println(cardChoice);
                    if (cardChoice.equals(targetPlayer3.getCard1().getCardName())) {
                        System.out.println("You've guessed correctly! Player " + targetPlayer3.getPlayerName() + " is out of the round!");
                        targetPlayer3.setPlaying(false);
                        Server.eliminated = targetPlayer3.getPlayerID();
                    } else if (b) {
                        System.out.println("Player " + targetPlayer3.getPlayerName() + " does not have that card.");
                    } else {
                        System.out.println("Sorry, please chose your card again");
                        Server.outError = true;
                    }
                }
                break;
            }
            //check to see if targetPlayer4 is still in the round and do targetPlayer4 stuff
            else if (playerChoice == (targetPlayer4.getPlayerID())) {
                if (!targetPlayer4.getIsPlaying()) {
                    System.out.println("This player is already out of the round");
                    Server.outError = true;
                } else if (targetPlayer4.getIsPlayedHandmaiden()) {
                    System.out.println("This player has played the handmaid and is immune until their next turn");
                } else {
                    System.out.println("Name a card to guess");
                    System.out.println(cardChoice);
                    if (cardChoice.equals(targetPlayer4.getCard1().getCardName())) {
                        System.out.println("You've guessed correctly! Player " + targetPlayer4.getPlayerName() + " is out of the round!");
                        targetPlayer4.setPlaying(false);
                        Server.eliminated = targetPlayer4.getPlayerID();
                    } else if (b) {
                        System.out.println("Player " + targetPlayer4.getPlayerName() + " does not have that card.");
                    } else {
                        System.out.println("Sorry, please chose your card again");
                        Server.outError = true;
                    }
                }
                break;
            } else if (playerChoice != (targetPlayer1.getPlayerID()) || playerChoice != (targetPlayer2.getPlayerID()) ||
                    playerChoice != (targetPlayer3.getPlayerID()) || playerChoice != (targetPlayer4.getPlayerID()) ||
                    playerChoice != (currentPlayer.getPlayerID())) {
                System.out.println("What?!");
                Server.outError = true;
                break;
            }
        }

        return length;
    }
}