package client;

import java.io.IOException;

import connection.MulticastConnection;

public class ReadClient implements Runnable {

	private MulticastConnection multicast;
	private boolean read;
	private String username;

	public ReadClient(String chatHost, int chatPort, String username) throws IOException {
		this.multicast = new MulticastConnection(chatHost, chatPort);
		this.read = true;
		this.username = username;
	}

	@Override
	public void run() {
		while (this.read) {
			try {
				String message = this.multicast.receive();
				System.out.println(message.replaceFirst(this.username + ":", "Você:"));
			} catch (IOException e) {}
		}
	}
	
	public void joinGroup() throws IOException {
		this.multicast.joinGroup();
		this.multicast.send(username + " joined the chat");
	}
	
	@Override
	protected void finalize() throws Throwable {
		this.close();
	}

	public void close() throws IOException {
		this.read = false;
		this.multicast.send(username + " left the chat");
		this.multicast.close();
	}
}
