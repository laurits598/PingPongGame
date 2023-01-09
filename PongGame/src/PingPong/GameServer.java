package PingPong;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
	private static final int PORT = 8080;
	private static ArrayList<PlayerHandler> players = new ArrayList<>();
	private static final int MAX_PLAYERS = 2;
	private static ExecutorService pool = Executors.newFixedThreadPool(MAX_PLAYERS);
	public static String player1 = "";
	public static String player2 = "";


	
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
}













