package Game;

/**
 * Player objects will track player score, whether a player is still in a round and store things
 * like player name, score etc.
 * Created by padcf & paulvincentphillips on 01/11/16.
 */
public class Player {

    String playerName;
    private int playerScore = 0;
    private int playerID = 0;
    private boolean isPlaying = true;
    private boolean inTour = true;
    private Card card1;
    private Card card2;
    private boolean playedHandmaiden = false;

    //getter and setter methods for state

    public Player(String name) {
        playerName = name;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setScore(int score) {
        playerScore = score;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int random) {
        playerID = random;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public boolean getInTour() {
        return inTour;
    }

    public void setInTour(boolean skipTour) {
        inTour = skipTour;
    }

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String name) {
        playerName = name;
    }

    public boolean getIsPlayedHandmaiden() {
        return playedHandmaiden;
    }

    public void setPlayedHandmaiden(boolean playedHandmaid) {
        this.playedHandmaiden = playedHandmaid;
    }

}
