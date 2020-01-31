package Server;

import Game.Card;
import Game.Deck;
import Game.Player;
import Log4j.SystemOutToLog4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;


public class Server {

    static { SystemOutToLog4j.enableForClass(Server.class); }

    static Logger logger = Logger.getLogger(Server.class);


    /** The port that the server listens on.*/
    private static final int PORT = 9001;
    /** Array to tokenize inputs from clients*/
    public static String[] tokens = new String[]{null, null, null};
    /** Var to hold an id of eliminated player during the tour*/
    public static int eliminated = 0;
    /** Bool holding where there an error on on card choose or not*/
    public static boolean outError = false;
    /** Var to hold a new card for second player after playing <Prince> or <King>*/
    public static String newCard;
    public static String newCard1;

    /** The set of all names of clients in the chat room.  Maintained
     * so that we can check that new clients are not registering name
     * already in use.*/
    static ArrayList<String> names = new ArrayList<>();
    /** The set of all clients inputs. Cleared after each read out.*/
    static ArrayList<String> playersInput = new ArrayList<>();
    /** Var to know when 5th players connect and start a game.*/
    private static int clientsConnected = 0;
    /** The set of all the print writers for all the clients.
     * This set is kept so we can easily broadcast messages.*/
    private static ArrayList<PrintWriter> writers = new ArrayList<>();
    private static PrintWriter[] writerOrder = new PrintWriter[5];


    /**
     * The appplication main method, which just listens on a port and
     * spawns handler threads.
     */
    public static void main(String[] args) throws Exception {

        //PropertiesConfigurator is used to configure logger from properties file
//        PropertyConfigurator.configure("log4j/log4j.properties");

        //Log in console in and log file
        //logger.debug("Log4j appender configuration is successful !!");

        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    /**
     * A handler thread class.  Handlers are spawned from the listening
     * loop and are responsible for a dealing with a single client
     * and broadcasting its messages.
     */
    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        /**
         * Constructs a handler thread, squirreling away the socket.
         * All the interesting work is done in the run method.
         */
        public Handler(Socket socket) {
            this.socket = socket;
        }

        /**
         * Services this thread's client by repeatedly requesting a
         * screen name until a unique one has been submitted, then
         * acknowledges the name and registers the output stream for
         * the client in a global set, then repeatedly gets inputs and
         * broadcasts them.
         */
        public void run() {
            try {
                // Create character streams for the socket.
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("CONNECT");

                // Request a name from this client.  Keep requesting until
                // a name is submitted that is not already used.  Note that
                // checking for the existence of a name and adding the name
                // must be done while locking the set of names.
                while (true) {
                    name = in.readLine();
                    if (name == null || !name.startsWith("LOGIN ")) {
                        out.println("LOGIN ERROR");
                    } else {
                        synchronized (names) {
                            if (!names.contains(name.substring(6))) {
                                name = name.substring(6);
                                names.add(name);
                                out.println("OK");
                                writers.add(out);
                                System.out.println("Sucesfully added new user: " + name + " " + socket);
                                Thread.currentThread().setName(name);

                                break;
                            } else {
                                out.println("NAME ERROR");
                            }
                        }
                    }
                }

                clientsConnected++;
                if (clientsConnected == 5) {
                    GameLogic.start();
                }

                /* Accept messages from this client and broadcast them.
                Ignore other clients that cannot be broadcasted to. */

                while (true) {
                    String input = in.readLine();
                    synchronized (playersInput) {
                        synchronized (GameLogic) {
                            playersInput.add(input);
                            GameLogic.notify();
                        }
                    }
                }

            } catch (IOException e) {
                System.out.println(e);
            } finally {
                /* This client is going down!  Remove its name and its print
                writer from the sets, and close its socket. */
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
//                    clientsConnected--;
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }

    static Thread GameLogic = new Thread(new Runnable() {

        //An array of cards for evaluating GUARD input
        String[] cards = new String[]{"PRIEST", "BARON", "HANDMAIDEN", "PRINCE", "KING", "COUNTESS", "PRINCESS"};
        //An array of players id's in strings
        String[] aliveId = new String[]{"1", "2", "3", "4", "5"};
        //Substring from client input
        String choice;


        @Override
        public void run() {
            Thread.currentThread().setName("GameLogic");

            //thread #1
            Player player1 = new Player(names.get(0));
            //thread #2
            Player player2 = new Player(names.get(1));
            //thread #3
            Player player3 = new Player(names.get(2));
            //thread #4
            Player player4 = new Player(names.get(3));
            //thread #5
            Player player5 = new Player(names.get(4));

            //Array to hold order of players by theirs Id
            Player[] playerOrder = new Player[5];

            //CARD SETUP
            Deck mainDeck = new Deck(); //instantiate the deck of cards
            mainDeck.populateDeck(); // add card to deck


            //game loop
            //this sets up each round of the game -- a round ends when all players' turns are finished and a player either wins a point, or wins the game
            while (true) {

                //Setup random id for players
                randomId(player1, player2, player3, player4, player5);

                // Random player order - create array to hold order of play
                playOrder(playerOrder, player1, player2, player3, player4, player5);

//                //Print order of players for debug
//                for (int i =0;i<5;i++){ System.out.print(playerOrder[i].getPlayerName() + ", "); }
//                System.out.println("");

                //shuffle the deck
                mainDeck.shuffleDeck();

                //get the deck and store it in deck variable
                Card[] deck = mainDeck.getDeck();

                int deckLength = deck.length - 1;

                //DEAL CARDS
                deckLength = burnCard(deckLength); //burn off a card before dealing

                //method to deal out the cards and also hold the new value of deckLength returned from method in deckLength variable
                //this method will only work for starting hands as method stores cards in Card1 slot only
                deckLength = dealCards(player1, player2, player3, player4, player5, deckLength, deck);


                for (int i=0;i<playerOrder.length;i++){
                    writerOrder[i].println("START " + playerOrder[i].getPlayerID() + " " +
                            playerOrder[i].getCard1().getCardName());
                }


                int turnOrder = 0;
                int turnOrder2 = 1;
                int turnOrder3 = 2;
                int turnOrder4 = 3;
                int turnOrder5 = 4;


                //game loop 2
                //this will be every turn of a round ie. each player's go
                while (true) {

                    //if current player is in the round do this - otherwise go to else statement
                    if (playerOrder[turnOrder].getIsPlaying()) {
                        System.out.println(playerOrder[turnOrder].getPlayerName() + " " + playerOrder[turnOrder].getPlayerID()
                                + " MISS HIS GO ? " + !playerOrder[turnOrder].getInTour());
                        //if current player don't miss his go do this - otherwise go to else statement

                        if (playerOrder[turnOrder].getInTour()) {

                            String card_1;
                            String card_2;

                            //reset handmaiden ability from true to false
                            playerOrder[turnOrder].setPlayedHandmaiden(false);
                            //deal 2nd card to current player
                            if(deckLength>0) deckLength = dealCard2(playerOrder[turnOrder], deckLength, deck);
                            else break;

                            if ((!playerOrder[turnOrder2].getIsPlaying() || playerOrder[turnOrder2].getIsPlayedHandmaiden()) &&
                                            (!playerOrder[turnOrder3].getIsPlaying() || playerOrder[turnOrder3].getIsPlayedHandmaiden()) &&
                                            (!playerOrder[turnOrder4].getIsPlaying() || playerOrder[turnOrder4].getIsPlayedHandmaiden()) &&
                                            (!playerOrder[turnOrder5].getIsPlaying() || playerOrder[turnOrder5].getIsPlayedHandmaiden())){
                                writerOrder[turnOrder].println("NO TARGER AVAILABLE");
                                System.out.println("NO TARGER AVAILABLE");
                            }
                            else {

                                writerOrder[turnOrder].println("YOUR MOVE " + playerOrder[turnOrder].getCard2().getCardName());

//                            //choose a card to play
//                            writerOrder[turnOrder].println(playerOrder[turnOrder].getPlayerName() + ", " +
//                                    playerOrder[turnOrder].getPlayerID() + ", " +
//                                    playerOrder[turnOrder].getCard2().getCardName());

                                System.out.println(playerOrder[turnOrder].getPlayerID() + " " + playerOrder[turnOrder].getCard1().getCardName() +
                                        " " + playerOrder[turnOrder].getCard2().getCardName());


//                            // print out player names and their cards (cards for debugging reasons as usually it's hidden information)
//                            for (int i = 0; i < 5; i++) {
//                                if (playerOrder[i].getIsPlaying() && !playerOrder[i].getIsPlayedHandmaiden()) {
//                                    writerOrder[turnOrder].println(playerOrder[i].getPlayerID() + " "
//                                                + playerOrder[i].getCard1().getCardName());
//                                    System.out.print(playerOrder[i].getPlayerID() + " ");
//                                    System.out.println(playerOrder[i].getCard1().getCardName());
//                                } else {
//                                    if ((!playerOrder[turnOrder2].getIsPlaying() || playerOrder[turnOrder2].getIsPlayedHandmaiden()) &&
//                                            (!playerOrder[turnOrder3].getIsPlaying() || playerOrder[turnOrder3].getIsPlayedHandmaiden()) &&
//                                            (!playerOrder[turnOrder4].getIsPlaying() || playerOrder[turnOrder4].getIsPlayedHandmaiden()) &&
//                                            (!playerOrder[turnOrder5].getIsPlaying() || playerOrder[turnOrder5].getIsPlayedHandmaiden())) {
//                                        writerOrder[turnOrder].println("ERROR No player can be targeted this round. Your card is discarded and your turn is over");
//                                        System.out.println("No player can be targeted this round. Your card is discarded and your turn is over");
//                                    }
//                                }
//                            }


                                // if player has countess in hand check for prince or king also in hand
                                // if above is true only allow player to choose countess
                                while (true) {

                                    card_1 = playerOrder[turnOrder].getCard1().getCardName();
                                    card_2 = playerOrder[turnOrder].getCard2().getCardName();

                                    //Take input from client and tokenize it and evaluate
                                    clientInput(playerOrder[turnOrder], turnOrder);

                                    // check for countess in card1 slot
                                    if (card_1.equals("COUNTESS")) {
                                        if (card_2.equals("PRINCE") || card_2.equals("KING")) {
                                            // if there is a prince or king in card2 slot and player has chosen card slot 2 get player to choose card until chooses card1 slot
                                            if (choice.startsWith(card_2)) {
                                                System.out.println("ERROR: You must choose the Countess. Please enter a new choice");
//                                            if (Thread.currentThread().getName().equals(playerOrder[turnOrder].getPlayerName())) {
                                                writerOrder[turnOrder].println("ERROR: You must choose the Countess. Please enter a new choice");
                                            } else {
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    }

                                    // same as above but for opposite card slots
                                    else if (card_2.equals("COUNTESS")) {
                                        if (card_1.equals("PRINCE") || card_1.equals("KING")) {
                                            if (choice.startsWith(card_1)) {
                                                System.out.println("ERROR: You must choose the Countess. Please enter a new choice");
                                                writerOrder[turnOrder].println("ERROR: You must choose the Countess. Please enter a new choice");

//                                            for (PrintWriter writer : writers) {
//                                                writer.println("ERROR:  You must choose the Countess. Please enter a new choice");
//                                            }
                                            } else {
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }

                                // if player doesn't have a countess with either a king or prince also in hand execute player choice
                                while (true) {
                                    if (tokens[0].equals(card_1)) {
                                        deckLength = playerOrder[turnOrder].getCard1().specialFunction(playerOrder[turnOrder], playerOrder[turnOrder2], playerOrder[turnOrder3], playerOrder[turnOrder4], playerOrder[turnOrder5], deckLength, deck);
                                        playerOrder[turnOrder].setCard1(playerOrder[turnOrder].getCard2());
                                    } else if (tokens[0].equals(card_2)) {
                                        deckLength = playerOrder[turnOrder].getCard2().specialFunction(playerOrder[turnOrder], playerOrder[turnOrder2], playerOrder[turnOrder3], playerOrder[turnOrder4], playerOrder[turnOrder5], deckLength, deck);
                                    }

                                    if (outError) {
                                        writerOrder[turnOrder].println("ERROR");
                                        clientInput(playerOrder[turnOrder], turnOrder);
                                    } else {
                                        writerOrder[turnOrder].println("OK");
                                        if (choice.startsWith("KING")) {
                                            System.out.println("TRADE " + newCard );
                                            System.out.println("TRADE " + newCard1);

                                            writerOrder[turnOrder].println("TRADE: " + newCard );
                                            writerOrder[Integer.parseInt(tokens[1]) - 1].println("TRADE " + newCard1);

                                        }
                                        if (tokens[0].equals("PRINCE") && newCard != null) {
                                            System.out.println("NEW CARD " + newCard);
                                            writerOrder[Integer.parseInt(tokens[1]) - 1].println("NEW CARD " + newCard);
                                        }
                                        newCard = null;
                                        newCard1 = null;
                                        outError = false;
                                        break;
                                    }
                                }

                                //Notify about move and eventual elimination
                                //eliminated holds id of eliminated player
                                for (PrintWriter writer : writers) {
                                    writer.println("MOVE " + choice);
                                    if (eliminated != 0) {
                                        writer.println("ELIMINATED " + eliminated);
                                    }
                                }

                                //Delete eleminated player from array and reset variable
//                            0 | 1 | 2 | 3 | 4
//                            1 | 2 | 3 | 4 | 5
                                if (eliminated != 0) {
                                    aliveId[eliminated - 1] = null;
                                    eliminated = 0;
                                }
                                System.out.println("#### Tabela aktywnych graczy ####");
                                System.out.println(Arrays.toString(aliveId));
                                System.out.println("#################################");
                            }

                            //check to see if 4 players are knocked out
                            int isOutCount = 0;

                            if (!player1.getIsPlaying()) {
                                isOutCount++;
                            }
                            if (!player2.getIsPlaying()) {
                                isOutCount++;
                            }
                            if (!player3.getIsPlaying()) {
                                isOutCount++;
                            }
                            if (!player4.getIsPlaying()) {
                                isOutCount++;
                            }
                            if (!player5.getIsPlaying()) {
                                isOutCount++;
                            }

                            //check to see which player is left standing -- award one point
                            if (isOutCount == 4) {
                                if (player1.getIsPlaying()) {
                                    player1.setScore(player1.getPlayerScore() + 1);
                                    System.out.println("ROUND WINNER " + player1.getPlayerID());
                                    for (PrintWriter writer : writers) {
                                        writer.println("ROUND WINNER " + player1.getPlayerID());
                                    }
                                } else if (player2.getIsPlaying()) {
                                    player2.setScore(player2.getPlayerScore() + 1);
                                    System.out.println("ROUND WINNER " + player2.getPlayerID());
                                    for (PrintWriter writer : writers) {
                                        writer.println("ROUND WINNER " + player2.getPlayerID());
                                    }
                                } else if (player3.getIsPlaying()) {
                                    player3.setScore(player3.getPlayerScore() + 1);
                                    System.out.println("ROUND WINNER " + player2.getPlayerID());
                                    for (PrintWriter writer : writers) {
                                        writer.println("ROUND WINNER " + player2.getPlayerID());
                                    }
                                } else if (player4.getIsPlaying()) {
                                    player4.setScore(player4.getPlayerScore() + 1);
                                    System.out.println("ROUND WINNER " + player4.getPlayerID());
                                    for (PrintWriter writer : writers) {
                                        writer.println("ROUND WINNER " + player4.getPlayerID());
                                    }
                                } else if (player5.getIsPlaying()) {
                                    player5.setScore(player5.getPlayerScore() + 1);
                                    System.out.println("ROUND WINNER " + player5.getPlayerID());
                                    for (PrintWriter writer : writers) {
                                        writer.println("ROUND WINNER " + player5.getPlayerID());
                                    }
                                }
                                break;
                            }

                            //check if deck is empty
                            if (deckLength == 0) {
                                System.out.println("No more cards.\nThis round is a draw!");
                                break;
                            }

                            //next player
                            if (turnOrder == 4) {
                                turnOrder = 0;
                            } else {
                                turnOrder++;
                            }

                            //shift every other player up a number
                            if (turnOrder2 == 4) {
                                turnOrder2 = 0;
                            } else {
                                turnOrder2++;
                            }

                            if (turnOrder3 == 4) {
                                turnOrder3 = 0;
                            } else {
                                turnOrder3++;
                            }

                            if (turnOrder4 == 4) {
                                turnOrder4 = 0;
                            } else {
                                turnOrder4++;
                            }

                            if (turnOrder5 == 4) {
                                turnOrder5 = 0;
                            } else {
                                turnOrder5++;
                            }
                            System.out.println("The remaining number of cards in the deck is: " + deckLength);

                        }
                        //if player is out of tour and above if statement was not executed -- update player order and move to next player
                        else {
                            playerOrder[turnOrder].setInTour(true);
                            System.out.println("Round of " + playerOrder[turnOrder].getPlayerName() + " passed his tour by <Priest>.");

                            //next player
                            if (turnOrder == 4) {
                                turnOrder = 0;
                            } else {
                                turnOrder++;
                            }

                            //shift every other player up a number
                            if (turnOrder2 == 4) {
                                turnOrder2 = 0;
                            } else {
                                turnOrder2++;
                            }

                            if (turnOrder3 == 4) {
                                turnOrder3 = 0;
                            } else {
                                turnOrder3++;
                            }

                            if (turnOrder4 == 4) {
                                turnOrder4 = 0;
                            } else {
                                turnOrder4++;
                            }

                            if (turnOrder5 == 4) {
                                turnOrder5 = 0;
                            } else {
                                turnOrder5++;
                            }
                        }
                    }
                    //if player is out of game and above if statement was not executed -- update player order and move to next turn
                    else {

                        //next player
                        if (turnOrder == 4) {
                            turnOrder = 0;
                        } else {
                            turnOrder++;
                        }

                        //shift every other player up a number
                        if (turnOrder2 == 4) {
                            turnOrder2 = 0;
                        } else {
                            turnOrder2++;
                        }

                        if (turnOrder3 == 4) {
                            turnOrder3 = 0;
                        } else {
                            turnOrder3++;
                        }

                        if (turnOrder4 == 4) {
                            turnOrder4 = 0;
                        } else {
                            turnOrder4++;
                        }

                        if (turnOrder5 == 4) {
                            turnOrder5 = 0;
                        } else {
                            turnOrder5++;
                        }
                    }
                }

                player1.setPlayedHandmaiden(false);
                player1.setPlaying(true);
                player2.setPlayedHandmaiden(false);
                player2.setPlaying(true);
                player3.setPlayedHandmaiden(false);
                player3.setPlaying(true);
                player4.setPlayedHandmaiden(false);
                player4.setPlaying(true);
                player5.setPlayedHandmaiden(false);
                player5.setPlaying(true);

                aliveId = new String[]{"1", "2", "3", "4", "5"};

                //check to see if any player has reached the goal of getting 4 points, winning the game -- break out of game loop
                if (player1.getPlayerScore() == 4) {
                    for (PrintWriter writer : writers) {
                        writer.println("GAME WINNER  " + player1.getPlayerName() + " has earned 4 points");
                    }
                    System.out.println("GAME WINNER  " + player1.getPlayerName() + " has earned 4 points");

                    break;
                } else if (player2.getPlayerScore() == 4) {
                    for (PrintWriter writer : writers) {
                        writer.println("GAME WINNER  " + player2.getPlayerName() + " has earned 4 points");
                    }
                    System.out.println("GAME WINNER  " + player2.getPlayerName() + " has earned 4 points");

                    break;
                } else if (player3.getPlayerScore() == 4) {
                    for (PrintWriter writer : writers) {
                        writer.println("GAME WINNER  " + player3.getPlayerName() + " has earned 4 points");
                    }
                    System.out.println("GAME WINNER  " + player3.getPlayerName() + " has earned 4 points");
                    break;
                } else if (player4.getPlayerScore() == 4) {
                    for (PrintWriter writer : writers) {
                        writer.println("GAME WINNER  " + player4.getPlayerName() + " has earned 4 points");
                    }
                    System.out.println("GAME WINNER  " + player4.getPlayerName() + " has earned 4 points");
                    break;
                } else if (player5.getPlayerScore() == 4) {
                    for (PrintWriter writer : writers) {
                        writer.println("GAME WINNER  " + player5.getPlayerName() + " has earned 4 points");
                    }
                    System.out.println("GAME WINNER  " + player5.getPlayerName() + " has earned 4 points");
                    break;
                }

                System.out.println("ROUND RESULTS " + player1.getPlayerName() + " " + player1.getPlayerScore() + "\n" +
                        player2.getPlayerName() + " " + player2.getPlayerScore() + "\n" + player3.getPlayerName() + " " + player3.getPlayerScore() + " " +
                        player4.getPlayerName() + " " + player4.getPlayerScore() + "\n" + player5.getPlayerName() + " " + player5.getPlayerScore());

                for (PrintWriter writer : writers) {
                    writer.println("ROUND RESULTS " + player1.getPlayerName() + " " + player1.getPlayerScore() + " " +
                            player2.getPlayerName() + " " + player2.getPlayerScore() + " " + player3.getPlayerName() + " " + player3.getPlayerScore() + " " +
                            player4.getPlayerName() + " " + player4.getPlayerScore() + " " + player5.getPlayerName() + " " + player5.getPlayerScore());
                }
            }
        }


        //burn off one card
        public int burnCard(int deckLength) {
            deckLength--;
            return deckLength;
        }

        //deal out cards
        public int dealCards(Player pOne, Player pTwo, Player pThree, Player pFour, Player pFive, int deckLength, Card[] deck) {
            pOne.setCard1(deck[deckLength]);
            deckLength--;
            pTwo.setCard1(deck[deckLength]);
            deckLength--;
            pThree.setCard1(deck[deckLength]);
            deckLength--;
            pFour.setCard1(deck[deckLength]);
            deckLength--;
            pFive.setCard1(deck[deckLength]);
            deckLength--;
            return deckLength;
        }

        //Deal second card to current player
        public int dealCard2(Player player, int deckLength, Card[] deck) {
            player.setCard2(deck[deckLength]);
            deckLength--;
            return deckLength;
        }

        //Setup random id for players
        public void randomId(Player pOne, Player pTwo, Player pThree, Player pFour, Player pFive) {
            Integer[] list = new Integer[]{1, 2, 3, 4, 5};
            List<Integer> l = Arrays.asList(list);
            Collections.shuffle(l);
            Integer[] array = l.toArray(new Integer[0]);
            pOne.setPlayerID(array[0]);
            pTwo.setPlayerID(array[1]);
            pThree.setPlayerID(array[2]);
            pFour.setPlayerID(array[3]);
            pFive.setPlayerID(array[4]);
        }

        //Random player order - create array to hold order of play
        public void playOrder(Player[] array, Player pOne, Player pTwo, Player pThree, Player pFour, Player pFive) {
            array[pOne.getPlayerID() - 1] = pOne;
            array[pTwo.getPlayerID() - 1] = pTwo;
            array[pThree.getPlayerID() - 1] = pThree;
            array[pFour.getPlayerID() - 1] = pFour;
            array[pFive.getPlayerID() - 1] = pFive;

            writerOrder[pOne.getPlayerID()-1] = writers.get(0);
            writerOrder[pTwo.getPlayerID()-1] = writers.get(1);
            writerOrder[pThree.getPlayerID()-1] = writers.get(2);
            writerOrder[pFour.getPlayerID()-1] = writers.get(3);
            writerOrder[pFive.getPlayerID()-1] = writers.get(4);
        }

        public void clientInput(Player currentPlayer, int turnOrder) {
            String input;
            while (true) {
                boolean err = false;
                String msg = null;

                synchronized (GameLogic) {
                    try {
                        if (playersInput.size() == 0) {
                            System.out.println("Waiting for input");
                            GameLogic.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                input = playersInput.get(0);
                System.out.println(currentPlayer.getPlayerName() + ": " + input);
                String playerID = Integer.toString(currentPlayer.getPlayerID());

                //IF BAD FORMAT OF INPUT
                if (input.isEmpty() || input.equals(" ") || input.trim().equals("CHOOSE") || !input.startsWith("CHOOSE ")) {
                    err = true;
                    msg = "Correct input: CHOOSE <card_name> <player id>";
                }
                //CHOOSE <CARD>
                else if (input.startsWith("CHOOSE ")) {
                    choice = input.substring(7);
                    tokens = choice.split(" ");
                    if (!tokens[0].equals(currentPlayer.getCard1().getCardName()) && !tokens[0].equals(currentPlayer.getCard2().getCardName())) {
                        err = true;
                        msg = "You don't have that card in your hand. Choose another one.";
                    }
                    //CHOOSE <Card Name> <Player Id> <Card Name>
                    else if (tokens[0].startsWith("GUARD")) {
                        //CHOOSE GUARD [blank]
                        if (tokens.length == 1) {
                            msg = (" no <Player_ID> and no <Card name> to compare.");
                            err = true;
                        }
                        //CHOOSE GUARD <Player Id>
                        else if (tokens.length == 2) {
                            //Self chosed
                            if (tokens[1].startsWith(playerID)) {
                                msg = (" You choosed yourself, can't compare one hand.");
                                err = true;
                            }
                            //<Player Id> among aliveId - if on second place is ID
                            else if (!StringUtils.equalsAny(tokens[1], aliveId) || tokens[1] == null) {
                                msg = (" no <Player ID> or no <Card name> to compare.");
                                err = true;
                            } else {
                                msg = (" no <Card name> to compare.");
                                err = true;
                            }
                        }
                        //CHOICE GUARD <Player Id> [blank]
                        else if (tokens.length == 3) {
                            //Self chosed
                            if (tokens[1].startsWith(playerID)) {
                                msg = (" You choosed yourself, can't compare one hand.");
                                err = true;
                            }
                            //<Player Id> among aliveId -> swapped order is handled too
                            else if (!StringUtils.equalsAny(tokens[1], aliveId) || tokens[1] == null) {
                                msg = (" no <Player ID> or no <Card name> to compare.");
                                err = true;
                            }
                            if (!StringUtils.equalsAny(tokens[2], cards)) {
                                if (tokens[2].startsWith("GUARD")) {
                                    System.out.println("ERROR: You cannot type <GUARD> as card type.");
                                    for (PrintWriter writer : writers) {
                                        writer.println("ERROR: You cannot type <GUARD> as card type.");
                                    }
                                } else {
                                    msg = (" no <Card name> to compare");
                                    err = true;
                                }
                            }

                        }
                    }
                    //CHOOSE <Card name> <Player Id>
                    else if (tokens[0].startsWith("PRIEST") || tokens[0].startsWith("BARON") || tokens[0].equals("PRINCE") || tokens[0].startsWith("KING")) {
                        if (tokens.length == 1) {
                            msg = (" no <Player_ID>.");
                            err = true;
                        }
                        else if (!StringUtils.equalsAny(tokens[1], aliveId) || tokens[1] == null) {
                            msg = (" no <Player_ID>.");
                            err = true;
                        }
                    }
                    //CHOOSE <Card name>
                    else if (tokens[0].startsWith("COUNTESS") || tokens[0].startsWith("HANDMAIDEN") || tokens[0].startsWith("PRINCESS")) {
                        playersInput.clear();
                        break;
                    } else {
                        err = true;
                        msg = "Correct input: CHOOSE <card_name> <player id>";
                    }
                }
                if (err) {
                    System.out.println("ERROR " + msg);
                    playersInput.clear();
                    writerOrder[turnOrder].println("ERROR: " + msg);
                } else {
                    playersInput.clear();
                    break;
                }
            }
        }
    });

}