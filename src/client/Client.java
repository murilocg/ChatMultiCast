package client;

import java.io.IOException;

import config.ConfigServer;
import connection.UDPConnection;
import protocol.request.Request;

public class Client {

	private String username;
	private UDPConnection udp;
	private ReadClient reader;
	private String chatroom;

	public Client(String username) throws IOException {
		this.setUsername(username);
		this.udp = new UDPConnection();
	}

	public String send(Request req) throws IOException {
		this.udp.send(req.toString(), ConfigServer.UDP_HOST, ConfigServer.UDP_PORT);
		return this.udp.receive();
	}

	public void start(String chatHost, int chatPort) throws IOException {
		this.reader = new ReadClient(chatHost, chatPort, this.username);
		this.reader.joinGroup();
		Thread t = new Thread(this.reader);
		t.start();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	protected void finalize() throws Throwable {
		this.close();
	}

	public void close() throws IOException {
		this.udp.close();
		this.reader.close();
	}

	public ReadClient getReader() {
		return reader;
	}

	public void setReader(ReadClient reader) {
		this.reader = reader;
	}

	public String getChatroom() {
		return chatroom;
	}

	public void setChatroom(String chatroom) {
		this.chatroom = chatroom;
	}
}
