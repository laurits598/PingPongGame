package PingPong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;


public class Server {

	public static void main(String[] args) {
		try {
			
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

			// Create a repository 
			SpaceRepository repository = new SpaceRepository();

			// Create two spaces for player movement
			SequentialSpace playerOneMovement = new SequentialSpace();
			SequentialSpace playerTwoMovement = new SequentialSpace();
			SequentialSpace ballMovement = new SequentialSpace();

			// Add the space to the repository
			repository.add("playerOneMovement", playerOneMovement);
			repository.add("playerTwoMovement", playerTwoMovement);
			repository.add("ballMovement", ballMovement);
			
			// Set the URI of the chat space
			System.out.print("Enter URI of the chat server or press enter for default: ");
			String uri = input.readLine();
			// Default value
			if (uri.isEmpty()) { 
				uri = "tcp://127.0.0.1:9001/?keep";
			}

			// Open a gate
			URI myUri = new URI(uri);
			String gateUri = "tcp://" + myUri.getHost() + ":" + myUri.getPort() +  "?keep" ;
			System.out.println("Opening repository gate at " + gateUri + "...");
			repository.addGate(gateUri);

			// Keep reading chat messages and printing them 
			/*while (true) {
				Object[] t = playerTwoMovement.get(new FormalField(Integer.class), new FormalField(Integer.class), new FormalField(Integer.class));
				System.out.println(t[0] + ":" + t[1]+":" + t[2]);
			}*/

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}










