package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import config.ConfigServer;
import connection.MulticastConnection;
import connection.UDPConnection;

public class Server {

	private List<String> clientes;
	private MulticastConnection multicast;
	private UDPConnection udp;

	public Server() throws IOException {
		this.clientes = new ArrayList<String>();
		this.multicast = new MulticastConnection(ConfigServer.MULTICAST_HOST, ConfigServer.MULTICAST_PORT);
		this.udp = new UDPConnection(ConfigServer.UDP_PORT);
	}

	public Chatroom joinChat(String username, String chatroom) throws IOException {
		if(this.clientes.contains(username)) return null;
		this.clientes.add(username);
		String host = ConfigServer.MULTICAST_HOST;
		int port = ConfigServer.MULTICAST_PORT;
		return new Chatroom(host, port);
	}

	public boolean leaveChat(String username, String chatroom) throws IOException {
		int index = this.clientes.indexOf(username);
		if(index == -1) return false;
		this.clientes.remove(username);
		return true;
	}

	public boolean sendMessage(String username, String message) throws IOException {
		if(!this.clientes.contains(username)) return false;
		this.multicast.send(formatt(username, message));
		return true;
	}
	
	private String formatt(String username, String message) {
		return username + ": " + message;
	}

	public void finalize() throws IOException {
		this.multicast.close();
		this.udp.close();
		this.clientes = null;
	}

	public String receive() throws IOException {
		return this.udp.receive();
	}

	public void reply(String data) throws IOException {
		this.udp.send(data);
	}
}
