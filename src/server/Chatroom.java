package server;

public class Chatroom {

	private String host;
	private int port;

	public Chatroom(String host, int port) {
		this.setHost(host);
		this.setPort(port);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
