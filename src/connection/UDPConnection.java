package connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import config.ConfigServer;

public class UDPConnection {

	private DatagramSocket socket;
	private DatagramPacket pack;

	public UDPConnection(int port) throws SocketException {
		this.socket = new DatagramSocket(port);
	}
	
	public UDPConnection() throws SocketException {
		this.socket = new DatagramSocket();
	}

	public void send(String content) throws IOException {
		send(content, pack.getAddress().getHostAddress(), pack.getPort());
	}
	
	public void send(String content, String host, int port) throws IOException {
		byte[] message = content.getBytes();
		InetAddress aHost = InetAddress.getByName(host);
		DatagramPacket request = new DatagramPacket(message, message.length, aHost, port);
		socket.send(request);
	}

	public String receive() throws IOException {
		byte[] buffer = new byte[ConfigServer.MESSAGE_MAX_SIZE];
		setPack(new DatagramPacket(buffer, buffer.length));
		this.socket.receive(getPack());
		return new String(getPack().getData()).trim();
	}

	public void close() {
		if(!this.socket.isClosed())
			this.socket.close();
	}

	public DatagramPacket getPack() {
		return pack;
	}

	public void setPack(DatagramPacket pack) {
		this.pack = pack;
	}
}
