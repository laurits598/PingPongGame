package GUI;

public class Main  {
	public static String player1;
	public static String player2;
	

	public static void main(String[] args) throws InterruptedException {
		
		HostOrJoin gui = new HostOrJoin();
		gui.init();
		
		while (player1 == null) {
			System.out.println("...");
			Thread.sleep(1000);
		}
		
		LobbyFrame lobby = new LobbyFrame();
		lobby.open();
		
		
	}

	
}


