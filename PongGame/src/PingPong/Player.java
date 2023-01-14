package PingPong;

import java.io.IOException;
import java.net.UnknownHostException;

import org.jspace.RemoteSpace;

public class Player {
	
	public Frame frame;
	public boolean isPlayerOne;
	public String uriP1;
	public String uriP2;
	public String uriBall;
	
	public Player(boolean isPlayerOne) {
		try {
			uriP1 = "tcp://127.0.0.1:9001/playerOneMovement?keep";
			uriP2 = "tcp://127.0.0.1:9001/playerTwoMovement?keep";
			uriBall = "tcp://127.0.0.1:9001/ballMovement?keep";;
			RemoteSpace playerOneMovement = new RemoteSpace(uriP1);
			RemoteSpace playerTwoMovement = new RemoteSpace(uriP2);
			RemoteSpace ballMovement = new RemoteSpace(uriBall);
			this.isPlayerOne = isPlayerOne;
			frame = new Frame(this.isPlayerOne, playerOneMovement, playerTwoMovement, ballMovement);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


