package Game;

import Server.Server;

/**
 * This class creates the Prince card which contains functionality and attributes unique to this class
 * Created by padcf & paulvincentphillips on 01/11/16.
 */


public class Prince implements Card {
    private int cardValue = 5;
    private String cardName = "PRINCE";
    private String cardAbility = "Choose any player (including yourself) to discard his or her hand and draw a new card.";


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


            //unlike most other cards, the prince may target himself
            //we do not need to check if current player is still in round
            //deal target a new card, replacing current card
            if (playerChoice == (currentPlayer.getPlayerID())) {
                //check if you are discarding a princess
                //if above is true you are out of the round... numpty!
                if (currentPlayer.getCard1().getCardName().equals("PRINCE") && currentPlayer.getCard2().getCardName().equals("PRINCESS")) {
                    currentPlayer.setPlaying(false);
                    System.out.println("You've discarded a princess. You are out of the game");
                    Server.eliminated = currentPlayer.getPlayerID();
                } else if (currentPlayer.getCard2().getCardName().equals("PRINCE") && currentPlayer.getCard1().getCardName().equals("PRINCESS")) {
                    currentPlayer.setPlaying(false);
                    System.out.println("You've discarded a princess. You are out of the game");
                    Server.eliminated = currentPlayer.getPlayerID();
                } else {
                    currentPlayer.setCard1(deck[length]);
                    length--;
                    System.out.println("You have discarded your hand and drawn a " + currentPlayer.getCard1().getCardName());
                }
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
                    //deal target a new card, replacing current card
                    if (playerChoice == (targetPlayer1.getPlayerID())) {
                        //check if you have made target player discard a princess, setting isPlaying to false if true
                        if (targetPlayer1.getCard1().getCardName().equals("PRINCESS")) {
                            System.out.println(targetPlayer1.getPlayerID() + " player has discarded a princess because of " + currentPlayer.getPlayerID() +
                                    "! \n" + targetPlayer1.getPlayerID() + " player is now out of the game!");
                            targetPlayer1.setPlaying(false);
                            Server.eliminated = targetPlayer1.getPlayerID();
                        }
                        //else deal player a new card
                        else {
                            targetPlayer1.setCard1(deck[length]);
                            length--;
                            System.out.println(targetPlayer1.getPlayerID() + " has discarded their hand and drawn a new one from the deck:" + targetPlayer1.getCard1().getCardName());
                            Server.newCard = targetPlayer1.getCard1().getCardName();
                        }
                    }
                }
                break;
            }
            //check to see if targetPlayer2 is still in the round and do targetPlayer2 stuff
            else if (playerChoice == (targetPlayer2.getPlayerID())) {

                if (!targetPlayer2.getIsPlaying()) {
                    System.out.println(targetPlayer2.getPlayerID() + "  player is already out of the round");
                    Server.outError = true;
                    break;
                } else if (targetPlayer2.getIsPlayedHandmaiden()) {
                    Server.outError = true;
                    break;
                } else {
                    //deal target a new card, replacing current card
                    if (playerChoice == (targetPlayer2.getPlayerID())) {
                        //check if you have made target player discard a princess, setting isPlaying to false if true
                        if (targetPlayer2.getCard1().getCardName().equals("PRINCESS")) {
                            System.out.println(targetPlayer2.getPlayerID() + " player has discarded a princess because of " + currentPlayer.getPlayerID() +
                                    "! \n" + targetPlayer2.getPlayerID() + " player is now out of the game!");
                            targetPlayer2.setPlaying(false);
                            Server.eliminated = targetPlayer2.getPlayerID();
                        }
                        //else deal player a new card
                        else {
                            targetPlayer2.setCard1(deck[length]);
                            length--;
                            System.out.println(targetPlayer2.getPlayerID() + " has discarded their hand and drawn a new one from the deck: " + targetPlayer2.getCard1().getCardName());
                            Server.newCard = targetPlayer2.getCard1().getCardName();
                        }
                    }
                }
                break;
            }
            //check to see if targetPlayer3 is still in the round and do targetPlayer3 stuff
            else if (playerChoice == (targetPlayer3.getPlayerID())) {

                if (!targetPlayer3.getIsPlaying()) {
                    System.out.println(targetPlayer3.getPlayerID() + "  player is already out of the round");
                    Server.outError = true;
                    break;
                } else if (targetPlayer3.getIsPlayedHandmaiden()) {
                    System.out.println(targetPlayer3.getPlayerID() + "  player has played the handmaid and is immune until their next turn");
                    break;
                } else {
                    //deal target a new card, replacing current card
                    if (playerChoice == (targetPlayer3.getPlayerID())) {
                        //check if you have made target player discard a princess, setting isPlaying to false if true
                        if (targetPlayer3.getCard1().getCardName().equals("PRINCESS")) {
                            System.out.println(targetPlayer3.getPlayerID() + " player has discarded a princess because of " + currentPlayer.getPlayerID() +
                                    "! \n" + targetPlayer3.getPlayerID() + " player is now out of the game!");
                            targetPlayer3.setPlaying(false);
                            Server.eliminated = targetPlayer3.getPlayerID();
                        }
                        //else deal player a new card
                        else {
                            targetPlayer3.setCard1(deck[length]);
                            length--;
                            System.out.println(targetPlayer3.getPlayerID() + " has discarded their hand and drawn a new one from the deck: " + targetPlayer3.getCard1().getCardName());
                            Server.newCard = targetPlayer3.getCard1().getCardName();
                        }
                    }
                }
                break;
            }
            //check to see if targetPlayer4 is still in the round and do targetPlayer3 stuff
            else if (playerChoice == (targetPlayer4.getPlayerID())) {

                if (!targetPlayer4.getIsPlaying()) {
                    System.out.println(targetPlayer4.getPlayerID() + "  player is already out of the round");
                    Server.outError = true;
                    break;
                } else if (targetPlayer4.getIsPlayedHandmaiden()) {
                    System.out.println(targetPlayer4.getPlayerID() + "  player has played the handmaid and is immune until their next turn");
                    break;
                } else {
                    //deal target a new card, replacing current card
                    if (playerChoice == (targetPlayer4.getPlayerID())) {
                        //check if you have made target player discard a princess, setting isPlaying to false if true
                        if (targetPlayer4.getCard1().getCardName().equals("PRINCESS")) {
                            System.out.println(targetPlayer4.getPlayerID() + " player has discarded a princess because of " + currentPlayer.getPlayerID() +
                                    "! \n" + targetPlayer4.getPlayerID() + " player is now out of the game!");
                            targetPlayer4.setPlaying(false);
                            Server.eliminated = targetPlayer4.getPlayerID();
                        }
                        //else deal player a new card
                        else {
                            targetPlayer4.setCard1(deck[length]);
                            length--;
                            System.out.println(targetPlayer4.getPlayerID() + " has discarded their hand and drawn a new one from the deck: " + targetPlayer4.getCard1().getCardName());
                            Server.newCard = targetPlayer4.getCard1().getCardName();
                        }
                    }
                }
                break;
            }
        }
        return length;
    }
}
