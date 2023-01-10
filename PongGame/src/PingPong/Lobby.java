package PingPong;
import org.jspace.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Lobby {
    public static void main(String[] args) throws IOException, InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        
        //User decides whether to join or host a game, based on its input
        System.out.println("Choose 'HOST' or 'JOIN': ");
        String input = scanner.next();
        
        //If hosting, user joins a new space and repository
        if (input.equals("HOST")) {
            SequentialSpace game = new SequentialSpace();
            SpaceRepository repository = new SpaceRepository();

            // Setting up URI
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ip = inetAddress.getHostAddress();
            int port = 11345;  //make sure to change port num
            String URI = "tcp://" + ip + ":" + port + "?keep";
            System.out.println("A game is hosted on URI: " + URI);

            // Opening gate at given URI
            repository.addGate(URI);
            repository.add("game", game);

            //If non-host, user joins the existing space with another user
        } else if (input.equals("JOIN")) {
            System.out.println("Enter game URI... Format: tcp://<IP>:<PORT>");
            input = scanner.next();
            String hostUri = input + "/game?keep";
            RemoteSpace game = new RemoteSpace(hostUri);

        }
    }

}
