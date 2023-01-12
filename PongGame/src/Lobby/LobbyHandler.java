package Lobby;

import org.jspace.RemoteSpace;

import PingPong.GameServer;

public class LobbyHandler {

    private static LobbyUpdate lobbyUpdate;   //will handle lobby events; such as connect, dis-||-, gamestart, ...
    
    private LobbyModel lobbyModel;
    private String username;
    private Thread lobbyUpdaterThread;
    private RemoteSpace game;    
    private boolean isHost;  //check whether players is a host or not
    private int numberOfPlayers = 2;  //constant
    private Thread serverThread;  
    private GameServer server;
    private boolean gameStarted;  //if true, we exit lobby
    private boolean lobbyClosed;   //if gameStarted == true, we exit and close the lobby
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
