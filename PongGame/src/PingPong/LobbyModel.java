package PingPong;

public class LobbyModel {

    private String username;
    private int userID;
    private int MaxPlayers;
    private String host;
    private String ip;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public int getUserID() {
    	return userID;
    }
    
    public void setUserID(int userID) {
    	this.userID = userID;
    }
    
    public int getMaxPlayers() {
        return MaxPlayers;
    }

    public void setMaxPlayers(int MaxPlayers) {
        this.MaxPlayers = MaxPlayers;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
