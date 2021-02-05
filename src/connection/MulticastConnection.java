package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import config.ConfigServer;

public class MulticastConnection {
	
	private MulticastSocket socket;
	private String host;
	private int port;
	
	public MulticastConnection(String host, int port) throws IOException {
		this.socket = new MulticastSocket(port);
		this.setHost(host);
		this.port = port;
	}
	
	public MulticastConnection(int port) throws IOException {
		this.socket = new MulticastSocket(port);
		this.port = port;
	}

	public void send(String message) throws IOException {
		InetAddress groupId = InetAddress.getByName(this.getHost());
		byte[] buf = message.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, groupId, this.port);
		this.socket.send(packet);
	}

	public String receive() throws IOException {
		byte[] buffer = new byte[ConfigServer.MESSAGE_MAX_SIZE];
		DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
		this.socket.receive(messageIn);
		return new String(messageIn.getData()).trim();
	}

	public void joinGroup() throws IOException {
		InetAddress groupIp = InetAddress.getByName(getHost());
		this.socket = new MulticastSocket(port);
		this.socket.joinGroup(groupIp);
	}
	
	@Override
	protected void finalize() throws Throwable {
		this.close();
	}

	public void close() throws IOException {
		if(this.socket.isClosed()) return;
		this.socket.leaveGroup(InetAddress.getByName(getHost()));
		this.socket.close();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
