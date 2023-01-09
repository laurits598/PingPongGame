package PingPong;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class PlayerHandler implements Runnable{
	private Socket player;
	private BufferedReader in;
	private PrintWriter out;
	private ArrayList<PlayerHandler> players;
	public static PlayerHandler player1;
	public static PlayerHandler player2;

	public PlayerHandler(Socket playerSocket) throws IOException {
		this.player = playerSocket;
		this.player1 = player1;
		this.player2 = player2;
		in = new BufferedReader(new InputStreamReader(player.getInputStream()));
		out = new PrintWriter(player.getOutputStream(), true);
	}

	@Override
	public void run() {
		
	}
}



