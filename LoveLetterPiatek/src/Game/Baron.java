package Game;

import Server.Server;

/**
 * This class creates the Baron card which contains functionality and attributes unique to this class
 * Created by padcf & paulvincentphillips on 01/11/16.
 */

public class Baron implements Card {
    private int cardValue = 3;
    private String cardName = "BARON";
    private String cardAbility = "You and another player secretly compare hands. \nThe player with the lower value is out of the round.";


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

            //check to see if targetPlayer1 is still in the round and do targetPlayer1 stuff
            if (playerChoice == targetPlayer1.getPlayerID()) {
                if (!targetPlayer1.getIsPlaying()) {
                    System.out.println("This player is already out of the round");
                    Server.outError = true;
                } else if (targetPlayer1.getIsPlayedHandmaiden()) {
                    System.out.println("This player has played the handmaid and is immune until their next turn");
                } else {
                    if (!currentPlayer.getCard1().getCardName().equals("BARON")) {
                        System.out.println(currentPlayer.getPlayerName() + "'s card is a " + currentPlayer.getCard1().getCardName());
                        System.out.println(targetPlayer1.getPlayerName() + "'s card is a " + targetPlayer1.getCard1().getCardName());

                        if (currentPlayer.getCard1().getCardValue() > targetPlayer1.getCard1().getCardValue()) {
                            targetPlayer1.setPlaying(false);
                            Server.eliminated = targetPlayer1.getPlayerID();
                            System.out.println(targetPlayer1.getPlayerName() + " has a lower score than " + currentPlayer.getPlayerName() + ". " + targetPlayer1.getPlayerName() + " is out of this round.");
                        } else if (currentPlayer.getCard1().getCardValue() < targetPlayer1.getCard1().getCardValue()) {
                            currentPlayer.setPlaying(false);
                            Server.eliminated = currentPlayer.getPlayerID();
                            System.out.println(targetPlayer1.getPlayerName() + " has a higher score than " + currentPlayer.getPlayerName() + ". " + currentPlayer.getPlayerName() + " is out of this round.");
                        } else {
                            System.out.println("Both card scores are the same. Nothing happens");
                        }
                    } else if (!currentPlayer.getCard2().getCardName().equals("BARON")) {
                        System.out.println(currentPlayer.getPlayerName() + "'s card is a " + currentPlayer.getCard2().getCardName());
                        System.out.println(targetPlayer1.getPlayerName() + "'s card is a " + targetPlayer1.getCard1().getCardName());

                        if (currentPlayer.getCard2().getCardValue() > targetPlayer1.getCard1().getCardValue()) {
                            targetPlayer1.setPlaying(false);
                            Server.eliminated = targetPlayer1.getPlayerID();
                            System.out.println(targetPlayer1.getPlayerName() + " has a lower score than " + currentPlayer.getPlayerName() + ". " + targetPlayer1.getPlayerName() + " is out of this round.");
                        } else if (currentPlayer.getCard1().getCardValue() < targetPlayer1.getCard1().getCardValue()) {
                            currentPlayer.setPlaying(false);
                            Server.eliminated = currentPlayer.getPlayerID();
                            System.out.println(targetPlayer1.getPlayerName() + " has a higher score than " + currentPlayer.getPlayerName() + ". " + currentPlayer.getPlayerName() + " is out of this round.");
                        } else {
                            System.out.println("Both card scores are the same. Nothing happens");
                        }
                    } else if (currentPlayer.getCard1().getCardName().equals(currentPlayer.getCard2().getCardName())) {
                        System.out.println(currentPlayer.getPlayerName() + "'s card is a " + currentPlayer.getCard2().getCardName());
                        System.out.println(targetPlayer1.getPlayerName() + "'s card is a " + targetPlayer1.getCard1().getCardName());

                        if (currentPlayer.getCard2().getCardValue() > targetPlayer1.getCard1().getCardValue()) {
                            targetPlayer1.setPlaying(false);
                            Server.eliminated = targetPlayer1.getPlayerID();
                            System.out.println(targetPlayer1.getPlayerName() + " has a lower score than " + currentPlayer.getPlayerName() + ". " + targetPlayer1.getPlayerName() + " is out of this round.");
                        } else if (currentPlayer.getCard1().getCardValue() < targetPlayer1.getCard1().getCardValue()) {
                            currentPlayer.setPlaying(false);
                            Server.eliminated = currentPlayer.getPlayerID();
                            System.out.println(targetPlayer1.getPlayerName() + " has a higher score than " + currentPlayer.getPlayerName() + ". " + currentPlayer.getPlayerName() + " is out of this round.");
                        } else {
                            System.out.println("Both card scores are the same. Nothing happens");
                        }
                    }
                }
                break;
            }
            //check to see if targetPlayer2 is still in the round and do targetPlayer2 stuff
            else if (playerChoice == targetPlayer2.getPlayerID()) {
                if (!targetPlayer2.getIsPlaying()) {
                    System.out.println("This player is already out of the round");
                    Server.outError = true;
                } else if (targetPlayer2.getIsPlayedHandmaiden()) {
                    System.out.println("This player has played the handmaid and is immune until their next turn");
                } else {
                    if (!currentPlayer.getCard1().getCardName().equals("BARON")) {
                        System.out.println(currentPlayer.getPlayerName() + "'s card is a " + currentPlayer.getCard1().getCardName());
                        System.out.println(targetPlayer2.getPlayerName() + "'s card is a " + targetPlayer2.getCard1().getCardName());
                        if (currentPlayer.getCard1().getCardValue() > targetPlayer2.getCard1().getCardValue()) {
                            targetPlayer2.setPlaying(false);
                            Server.eliminated = targetPlayer2.getPlayerID();
                            System.out.println(targetPlayer2.getPlayerName() + " has a lower score than " + currentPlayer.getPlayerName() + ". " + targetPlayer2.getPlayerName() + " is out of this round.");
                        } else if (currentPlayer.getCard1().getCardValue() < targetPlayer2.getCard1().getCardValue()) {
                            currentPlayer.setPlaying(false);
                            Server.eliminated = currentPlayer.getPlayerID();
                            System.out.println(targetPlayer2.getPlayerName() + " has a higher score than " + currentPlayer.getPlayerName() + ". " + currentPlayer.getPlayerName() + " is out of this round.");
                        } else {
                            System.out.println("Both card scores are the same. Nothing happens");
                        }
                    } else if (!currentPlayer.getCard2().getCardName().equals("BARON")) {
                        System.out.println(currentPlayer.getPlayerName() + "'s card is a: " + currentPlayer.getCard2().getCardName());
                        System.out.println(targetPlayer2.getPlayerName() + "'s card is a: " + targetPlayer2.getCard1().getCardName());
                        if (currentPlayer.getCard2().getCardValue() > targetPlayer2.getCard1().getCardValue()) {
                            targetPlayer2.setPlaying(false);
                            Server.eliminated = targetPlayer2.getPlayerID();
                            System.out.println(targetPlayer2.getPlayerName() + " has a lower score than " + currentPlayer.getPlayerName() + ". " + targetPlayer2.getPlayerName() + " is out of this round.");
                        } else if (currentPlayer.getCard1().getCardValue() < targetPlayer2.getCard1().getCardValue()) {
                            currentPlayer.setPlaying(false);
                            Server.eliminated = currentPlayer.getPlayerID();
                            System.out.println(targetPlayer2.getPlayerName() + " has a higher score than " + currentPlayer.getPlayerName() + ". " + currentPlayer.getPlayerName() + " is out of this round.");
                        } else {
                            System.out.println("Both card scores are the same. Nothing happens");
                        }
                    } else if (currentPlayer.getCard1().getCardName().equals(currentPlayer.getCard2().getCardName())) {
                        System.out.println(currentPlayer.getPlayerName() + "'s card is a " + currentPlayer.getCard2().getCardName());
                        System.out.println(targetPlayer2.getPlayerName() + "'s card is a " + targetPlayer2.getCard1().getCardName());

                        if (currentPlayer.getCard2().getCardValue() > targetPlayer1.getCard1().getCardValue()) {
                            targetPlayer1.setPlaying(false);
                            Server.eliminated = targetPlayer2.getPlayerID();
                            System.out.println(targetPlayer2.getPlayerName() + " has a lower score than " + currentPlayer.getPlayerName() + ". " + targetPlayer2.getPlayerName() + " is out of this round.");
                        } else if (currentPlayer.getCard1().getCardValue() < targetPlayer2.getCard1().getCardValue()) {
                            currentPlayer.setPlaying(false);
                            Server.eliminated = currentPlayer.getPlayerID();
                            System.out.println(targetPlayer2.getPlayerName() + " has a higher score than " + currentPlayer.getPlayerName() + ". " + currentPlayer.getPlayerName() + " is out of this round.");
                        } else {
                            System.out.println("Both card scores are the same. Nothing happens");
                        }
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
                    if (!currentPlayer.getCard1().getCardName().equals("BARON")) {
                        System.out.println(currentPlayer.getPlayerName() + "'s card is a: " + currentPlayer.getCard1().getCardName());
                        System.out.println(targetPlayer3.getPlayerName() + "'s card is a: " + targetPlayer3.getCard1().getCardName());
                        if (currentPlayer.getCard1().getCardValue() > targetPlayer3.getCard1().getCardValue()) {
                            targetPlayer3.setPlaying(false);
                            Server.eliminated = targetPlayer3.getPlayerID();
                            System.out.println(targetPlayer3.getPlayerName() + " has a lower score than " + currentPlayer.getPlayerName() + ". " + targetPlayer3.getPlayerName() + " is out of this round.");
                        } else if (currentPlayer.getCard1().getCardValue() < targetPlayer3.getCard1().getCardValue()) {
                            currentPlayer.setPlaying(false);
                            Server.eliminated = currentPlayer.getPlayerID();
                            System.out.println(targetPlayer3.getPlayerName() + " has a higher score than " + currentPlayer.getPlayerName() + ". " + currentPlayer.getPlayerName() + " is out of this round.");
                        } else {
                            System.out.println("Both card scores are the same. Nothing happens");
                        }
                    } else if (!currentPlayer.getCard2().getCardName().equals("BARON")) {
                        System.out.println(currentPlayer.getPlayerName() + "'s card is a: " + currentPlayer.getCard2().getCardName());
                        System.out.println(targetPlayer3.getPlayerName() + "'s card is a: " + targetPlayer3.getCard1().getCardName());

                        if (currentPlayer.getCard2().getCardValue() > targetPlayer3.getCard1().getCardValue()) {
                            targetPlayer3.setPlaying(false);
                            Server.eliminated = targetPlayer3.getPlayerID();
                            System.out.println(targetPlayer3.getPlayerName() + " has a lower score than " + currentPlayer.getPlayerName() + ". " + targetPlayer3.getPlayerName() + " is out of this round.");
                        } else if (currentPlayer.getCard1().getCardValue() < targetPlayer3.getCard1().getCardValue()) {
                            currentPlayer.setPlaying(false);
                            Server.eliminated = currentPlayer.getPlayerID();
                            System.out.println(targetPlayer3.getPlayerName() + " has a higher score than " + currentPlayer.getPlayerName() + ". " + currentPlayer.getPlayerName() + " is out of this round.");
                        } else {
                            System.out.println("Both card scores are the same. Nothing happens");
                        }
                    } else if (currentPlayer.getCard1().getCardName().equals(currentPlayer.getCard2().getCardName())) {
                        System.out.println(currentPlayer.getPlayerName() + "'s card is a: " + currentPlayer.getCard2().getCardName());
                        System.out.println(targetPlayer3.getPlayerName() + "'s card is a: " + targetPlayer3.getCard1().getCardName());

                        if (currentPlayer.getCard2().getCardValue() > targetPlayer3.getCard1().getCardValue()) {
                            targetPlayer3.setPlaying(false);
                            Server.eliminated = targetPlayer3.getPlayerID();
                            System.out.println(targetPlayer3.getPlayerName() + " has a lower score than " + currentPlayer.getPlayerName() + ". " + targetPlayer3.getPlayerName() + " is out of this round.");
                        } else if (currentPlayer.getCard1().getCardValue() < targetPlayer3.getCard1().getCardValue()) {
                            currentPlayer.setPlaying(false);
                            Server.eliminated = currentPlayer.getPlayerID();
                            System.out.println(targetPlayer3.getPlayerName() + " has a higher score than " + currentPlayer.getPlayerName() + ". " + currentPlayer.getPlayerName() + " is out of this round.");
                        } else {
                            System.out.println("Both card scores are the same. Nothing happens");
                        }
                    }
                }
                break;
            }
            //check to see if targetPlayer4 is still in the round and do targetPlayer3 stuff
            else if (playerChoice == (targetPlayer4.getPlayerID())) {
                if (!targetPlayer4.getIsPlaying()) {
                    System.out.println("This player is already out of the round");
                    Server.outError = true;
                } else if (targetPlayer4.getIsPlayedHandmaiden()) {
                    System.out.println("This player has played the handmaid and is immune until their next turn");
                } else {
                    if (!currentPlayer.getCard1().getCardName().equals("BARON")) {
                        System.out.println(currentPlayer.getPlayerName() + "'s card is a: " + currentPlayer.getCard1().getCardName());
                        System.out.println(targetPlayer4.getPlayerName() + "'s card is a: " + targetPlayer4.getCard1().getCardName());

                        if (currentPlayer.getCard1().getCardValue() > targetPlayer4.getCard1().getCardValue()) {
                            targetPlayer4.setPlaying(false);
                            Server.eliminated = targetPlayer4.getPlayerID();
                            System.out.println(targetPlayer4.getPlayerName() + " has a lower score than " + currentPlayer.getPlayerName() + ". " + targetPlayer4.getPlayerName() + " is out of this round.");
                        } else if (currentPlayer.getCard1().getCardValue() < targetPlayer4.getCard1().getCardValue()) {
                            currentPlayer.setPlaying(false);
                            Server.eliminated = currentPlayer.getPlayerID();
                            System.out.println(targetPlayer4.getPlayerName() + " has a higher score than " + currentPlayer.getPlayerName() + ". " + currentPlayer.getPlayerName() + " is out of this round.");
                        } else {
                            System.out.println("Both card scores are the same. Nothing happens");
                        }
                    } else if (!currentPlayer.getCard2().getCardName().equals("BARON")) {
                        System.out.println(currentPlayer.getPlayerName() + "'s card is a: " + currentPlayer.getCard2().getCardName());
                        System.out.println(targetPlayer4.getPlayerName() + "'s card is a: " + targetPlayer4.getCard1().getCardName());

                        if (currentPlayer.getCard2().getCardValue() > targetPlayer4.getCard1().getCardValue()) {
                            targetPlayer4.setPlaying(false);
                            Server.eliminated = targetPlayer4.getPlayerID();
                            System.out.println(targetPlayer4.getPlayerName() + " has a lower score than " + currentPlayer.getPlayerName() + ". " + targetPlayer4.getPlayerName() + " is out of this round.");
                        } else if (currentPlayer.getCard1().getCardValue() < targetPlayer4.getCard1().getCardValue()) {
                            currentPlayer.setPlaying(false);
                            Server.eliminated = currentPlayer.getPlayerID();
                            System.out.println(targetPlayer4.getPlayerName() + " has a higher score than " + currentPlayer.getPlayerName() + ". " + currentPlayer.getPlayerName() + " is out of this round.");
                        } else {
                            System.out.println("Both card scores are the same. Nothing happens");
                        }
                    } else if (currentPlayer.getCard1().getCardName().equals(currentPlayer.getCard2().getCardName())) {
                        System.out.println(currentPlayer.getPlayerName() + "'s card is a: " + currentPlayer.getCard2().getCardName());
                        System.out.println(targetPlayer4.getPlayerName() + "'s card is a: " + targetPlayer4.getCard1().getCardName());

                        if (currentPlayer.getCard2().getCardValue() > targetPlayer4.getCard1().getCardValue()) {
                            targetPlayer4.setPlaying(false);
                            Server.eliminated = targetPlayer4.getPlayerID();
                            System.out.println(targetPlayer4.getPlayerName() + " has a lower score than " + currentPlayer.getPlayerName() + ". " + targetPlayer4.getPlayerName() + " is out of this round.");
                        } else if (currentPlayer.getCard1().getCardValue() < targetPlayer4.getCard1().getCardValue()) {
                            currentPlayer.setPlaying(false);
                            Server.eliminated = currentPlayer.getPlayerID();
                            System.out.println(targetPlayer4.getPlayerName() + " has a higher score than " + currentPlayer.getPlayerName() + ". " + currentPlayer.getPlayerName() + " is out of this round.");
                        } else {
                            System.out.println("Both card scores are the same. Nothing happens");
                        }
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