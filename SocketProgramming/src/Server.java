import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
//import javax.xml.stream.events.Namespace;

import java.util.concurrent.ExecutorService;

public class Server {
	private static String[] names = {"Lau", "Joe", "CJ", "Rick"};
	private static String[] adjs = {"the menace", "the plug", "the slider", "the gun-slinger"};

	
	private static final int PORT = 8080;
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	private static ExecutorService pool = Executors.newFixedThreadPool(4);
	
	public static void main(String[] args) throws IOException {
		ServerSocket listener = new ServerSocket(PORT);
		
		while (true) {
			System.out.println("[Server] Waiting for client connection...");
			Socket client = listener.accept();
			System.out.println("[Server] Connected to client!");
			ClientHandler clientThread = new ClientHandler(client);
			clients.add(clientThread);
			
			pool.execute(clientThread);
		}
		
	}
	
	public static String getRandomName() {
		String name = names[(int) (Math.random() * names.length)];
		String adj = adjs[(int) (Math.random() * adjs.length)];

		return name + " " + adj;
	}
	
	public static ArrayList<ClientHandler> getClients() {
		return clients;
	}
	
	public static int getNumOfClients() {
		return clients.size();
	}
	
}



