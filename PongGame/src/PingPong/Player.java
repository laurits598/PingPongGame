package PingPong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.jspace.RemoteSpace;

public class Player {
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 8080;
	public static String USERNAME = null;
	public static int USER_ID = 0;
	public static String HOST = null; 
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
				try {
			
					BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

					// Set the URI of the chat space
					// Default value
					System.out.print("Enter URI of the chat server or press enter for default: ");
					String uri = input.readLine();
					// Default value
					if (uri.isEmpty()) { 
						uri = "tcp://127.0.0.1:9001/chat?keep";
					}
		
					// Connect to the remote chat space 
					System.out.println("Connecting to chat space " + uri + "...");
					RemoteSpace chat = new RemoteSpace(uri);
		
					// Read user name from the console			
					System.out.print("Enter your name: ");
					String name = input.readLine();
		
					// Keep sending whatever the user types
					System.out.println("Start chatting...");
					while(true) {
						String message = input.readLine();
						chat.put(name, message);
					}			


				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
		UsernameGUI gui = new UsernameGUI();
		gui.start();
		
		while (USERNAME == null) {
			System.out.println("> ");
			String command = keyboard.readLine();
			
			if (command.equals("quit")) {
				break;
			}
			out.println(command);
			
			String serverResponse = input.readLine();
			System.out.println("Server says: " + serverResponse);
		}
		out.println(USERNAME);
		
		socket.close();
		System.exit(0);

	}
	public void setUsername(String username) {
		this.USERNAME = username;
	}
	
	public void setUserID(int userID) {
		this.USER_ID = userID;
	}
	
	public String getHost() {
        return this.HOST;
    }
}



