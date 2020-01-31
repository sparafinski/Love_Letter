package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client2 {
    String name = "PLAYER2";

    final static int ServerPort = 9001;
    final static String ServerAddress = "localhost";
    static ArrayList<String> cards= new ArrayList<>();
    static String[] guardCards = new String[]{"PRIEST", "BARON", "HANDMAIDEN", "PRINCE", "KING", "COUNTESS", "PRINCESS"};
    static ArrayList<String > playersAlive = new ArrayList<>();
    static boolean round = true;

    BufferedReader in;
    PrintWriter out;

    /**
     * Runs the client as an application with a closeable frame.
     */
    public static void main(String[] args) throws Exception {
        Client2 client = new Client2();
        client.run();
    }

    /**
     * Connects to the server then enters the processing loop.
     */
    private void run() throws IOException {

        Socket socket = new Socket(ServerAddress, ServerPort);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        String fromServer;
        //CONNECT
        System.out.println(in.readLine());
        //NAME
        out.println("LOGIN " + name);
        System.out.println("C: LOGIN "+name);
        //OK
        System.out.println(in.readLine());

        while(round){
            fromServer = in.readLine();
            if(fromServer.startsWith("START ")){
                System.out.println("S: " + fromServer);
                cards.add(fromServer.substring(8));
                String iD = fromServer.substring(6,8).trim();
                for (int i=1;i<=5;i++){
                    if(i!=Integer.parseInt(iD)) playersAlive.add(Integer.toString(i));
                }
                System.out.println(playersAlive.toString());
                System.out.println("Card_1: "+cards.toString());
            }
            else if(fromServer.startsWith("YOUR MOVE ")){
                System.out.println("S: "+ fromServer);
                System.out.println(playersAlive);
                takeRound(fromServer);
            }
            else if(fromServer.startsWith("TRADE ") ){
                System.out.println("S: "+ fromServer);
                cards.remove(0);
                cards.add(fromServer.substring(6));
            }
            else if(fromServer.startsWith("NEW CARD")){
                System.out.println("S: "+ fromServer);
                cards.remove(0);
                cards.add(fromServer.substring(9));
            }
            else if (fromServer.startsWith("ELIMINATED ")){
                playersAlive.remove(fromServer.substring(11));
                System.out.println("S: "+ fromServer);
            }
            else if(fromServer.startsWith("GAME WINNER ")){
                System.out.println("S: "+ fromServer);
                System.exit(0);

            }
            else if(fromServer.startsWith("ROUND RESULTS")){
                playersAlive.clear();
                cards.clear();
                System.out.println("S: "+ fromServer);
            }
            else System.out.println("S: "+ fromServer);
        }
    }

    private void takeRound(String fromServer) throws IOException {

        String choose = "CHOOSE ";
        String output;

        //YOUR MOVE <CARD NAME>
        cards.add(fromServer.substring(10));
        System.out.println(cards.toString());

        while (true){
            if(cards.get(0).equals("GUARD")){
                int index = (int)(Math.random() * 7);
                output = choose + cards.get(0) + " " + playersAlive.get(0) +" " + guardCards[index];
                out.println(output);
                System.out.println("C: "+output);
                fromServer=in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Guard: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(0);
                break;
            }
            if(cards.get(1).equals("GUARD")){
                int index = (int)(Math.random() * 7);
                output = choose + cards.get(1) + " " + playersAlive.get(0) +" " + guardCards[index];
                out.println(output);
                System.out.println("C: "+output);
                fromServer=in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Guard: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(1);
                break;
            }
            else if (cards.get(0).equals("COUNTESS")) {
                output = choose + cards.get(0);
                out.println(output);
                System.out.println("C: "+output);
                fromServer = in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Countess1: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(0);
                break;
            }
            else if (cards.get(1).equals("COUNTESS")) {
                output = choose + cards.get(1);
                out.println(output);
                System.out.println("C: "+output);
                fromServer = in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Countess2: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(1);
                break;
            }
            else if(cards.get(0).equals("PRIEST")){
                output = choose + cards.get(0) + " " + playersAlive.get(0);
                out.println(output);
                System.out.println("C: "+output);
                fromServer = in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Priest: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(0);
                break;
            }
            else if(cards.get(1).equals("PRIEST")){
                output = choose + cards.get(1) + " " + playersAlive.get(0);
                out.println(output);
                System.out.println("C: "+output);
                fromServer = in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Priest: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(1);
                break;
            }
            else if(cards.get(0).equals("BARON")){
                output = choose + cards.get(0) + " " + playersAlive.get(0);
                out.println(output);
                System.out.println("C: "+output);
                fromServer = in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Baron: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(0);
                break;
            }
            else if(cards.get(1).equals("BARON")){
                output = choose + cards.get(1) + " " + playersAlive.get(0);
                out.println(output);
                System.out.println("C: "+output);
                fromServer = in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Baron: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(1);
                break;
            }
            else if(cards.get(0).equals("HANDMAIDEN")){
                output = choose + cards.get(0);
                out.println(output);
                System.out.println("C: "+output);
                fromServer = in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Handmaiden: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(0);
                break;
            }
            else if(cards.get(1).equals("HANDMAIDEN")){
                output = choose + cards.get(1);
                out.println(output);
                System.out.println("C: "+output);
                fromServer = in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Handmaiden: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(1);
                break;
            }
            else if(cards.get(0).equals("PRINCE")){
                output = choose + cards.get(0) + " " + playersAlive.get(0);
                out.println(output);
                System.out.println("C: "+output);
                fromServer = in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Prince: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(0);
                break;
            }
            else if(cards.get(1).equals("PRINCE")){
                output = choose + cards.get(1) + " " + playersAlive.get(0);
                out.println(output);
                System.out.println("C: "+output);
                fromServer = in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Prince: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(1);
                break;
            }
            else if(cards.get(0).equals("KING")) {
                output = choose + cards.get(0) + " " + playersAlive.get(0);
                out.println(output);
                System.out.println("C: "+output);
                fromServer = in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("King: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(0);
                break;
            }
            else if(cards.get(1).equals("KING")) {
                output = choose + cards.get(1) + " " + playersAlive.get(0);
                out.println(output);
                System.out.println("C: "+output);
                fromServer = in.readLine();
                if (!fromServer.startsWith("OK")) {
                    System.out.println("King: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(1);
                break;
            }
            else if(cards.get(0).equals("PRINCESS")){
                output = choose + cards.get(0);
                out.println(output);
                fromServer = in.readLine();
                System.out.println("C: "+ output);
                if (!fromServer.startsWith("OK")) {
                    System.out.println("Princess: " + fromServer);
                }
                else System.out.println(fromServer);
                cards.remove(0);
                break;
            }
        }
    }
}
