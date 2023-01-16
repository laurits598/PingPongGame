package PingPong;

import java.io.IOException;
import java.net.UnknownHostException;

import org.jspace.RemoteSpace;
import org.jspace.Space;

public class Player {
	
	public Frame frame;
	public boolean isPlayerOne;
	public String uriP1;
	public String uriP2;
	public String uriBall;
	public String uriScore;
	
	public Player(boolean isPlayerOne) {
		try {
			uriP1 = "tcp://104.248.22.64:9001/playerOneMovement?keep";
			uriP2 = "tcp://104.248.22.64:9001/playerTwoMovement?keep";
			uriBall = "tcp://104.248.22.64:9001/ballMovement?keep";
			uriScore = "tcp://104.248.22.64:9001/scoreSpace?keep";
			RemoteSpace playerOneMovement = new RemoteSpace(uriP1);
			RemoteSpace playerTwoMovement = new RemoteSpace(uriP2);
			RemoteSpace ballMovement = new RemoteSpace(uriBall);
			RemoteSpace scoreSpace = new RemoteSpace(uriBall);
			this.isPlayerOne = isPlayerOne;
			frame = new Frame(this.isPlayerOne, playerOneMovement, playerTwoMovement, ballMovement, scoreSpace);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


