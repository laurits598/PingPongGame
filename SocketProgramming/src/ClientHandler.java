import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientHandler implements Runnable {
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private ArrayList<ClientHandler> clients;

	
	public ClientHandler(Socket clientSocket) throws IOException {
		this.client = clientSocket;
		this.clients = clients;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream(), true);
	}

	public void run() {
		try {
			while (true) {
				String request = in.readLine();
				if (request.contains("name")) {
					out.println(Server.getRandomName());
				} else if (request.startsWith("say")) {
					int firstSpace = request.indexOf(" ");
					if (firstSpace != -1) {
						outToAll(request.substring(firstSpace+1));
					}
				} else if (request.contains("clients")) {
					out.println(Server.getClients());
				} else if (request.contains("num")) {
					out.println(Server.getNumOfClients());
				}
				else {
					out.println("Type 'tell me a name' to get a random name");
				}
			}
		} catch (IOException e) {
			System.err.println("IO Exception in client handler");
			System.err.println(e.getStackTrace());
		} finally {
			out.close();
			try {
				in.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void outToAll(String msg) {
		for (ClientHandler aClient : clients) {
			aClient.out.println(msg);
		}
		
	}
}



