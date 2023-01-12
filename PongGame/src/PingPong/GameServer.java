package PingPong;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jspace.Space;
import org.jspace.SpaceRepository;
import org.jspace.SequentialSpace;

//import PingPong2.ActualField;
//import PingPong2.FormalField;
//import PingPong2.LobbyRequestReceiver;
//import PingPong2.SequentialSpace;
//import PingPong2.SpaceRepository;
//import PingPong2.UserPinger;

public class GameServer implements Runnable{
	private static final int PORT = 8080;
	private static ArrayList<PlayerHandler> players = new ArrayList<>();
	private static final int MAX_PLAYERS = 2;
	private static ExecutorService pool = Executors.newFixedThreadPool(MAX_PLAYERS);
	public static String player1 = "";
	public static String player2 = "";
	public static SpaceRepository gameRepository;  //check whether it's public and static--- maybe on a playerhost
	public static String gate;           //-------||---------
	public static Thread thread;
	public static Space gameSpace;
	public static boolean hostAvailable = true;
	public static boolean joinAvailable = true;

//Should we add a constructor?
    
	
	public static void main(String[] args) throws IOException {
		ServerSocket listener = new ServerSocket(PORT);
		/*
		while (players.size() != MAX_PLAYERS) {
			System.out.println("[Server] Waiting for players to connect...");
			Socket player = listener.accept();
			System.out.println("[Server] Connected to client!");
			PlayerHandler playerThread = new PlayerHandler(player);
			players.add(playerThread);
			pool.execute(playerThread);
		}
		*/
		Player user = new Player();
		while (user.USERNAME == null) {
			System.out.println("[Server] Waiting for players to connect...");
			Socket player = listener.accept();
			System.out.println("[Server] Connected to client!");
			PlayerHandler playerThread = new PlayerHandler(player);
			players.add(playerThread);
			pool.execute(playerThread);
		}
		Frame frame = new Frame();
	}
	
	public void run() {
        try {
            //Create game serverSpace
            gameRepository = new SpaceRepository();

            //Space where serverSpace threads communicate
            SequentialSpace serverSpace = new SequentialSpace();

            //Keep track of connected players
            serverSpace.put("numberOfPlayers", 0);

            //Setting up URI
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ip = inetAddress.getHostAddress();
            int port = 11445;   //not necessairly 
            gate = "tcp://" + ip + ":" + PORT + "?keep";
            gameSpace.put("IPPort", ip + ":" + PORT);
            System.out.println("A game is hosted on IP: " + ip + ":" + PORT);

            // Opening gate at given URI
            gameRepository.add("game", gameSpace);
            gameRepository.addGate(gate);
            
            
            
            
            
            
            
            
            
            
//-----------------------------Until here is reliable------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------
            //Look for players connecting
            //LobbyRequestReceiver lobbyRequestReceiver = new LobbyRequestReceiver(this, gameSpace, serverSpace);
            //startThread(lobbyRequestReceiver); //Don't need thread object since it closes automatically

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}













