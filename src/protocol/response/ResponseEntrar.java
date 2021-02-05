package protocol.response;

import protocol.ProtocolAction;

public class ResponseEntrar extends Response {
	private String chatHost;
	private int chatPort;

	public ResponseEntrar(String chatHost, int chatPort, Status status) {
		super(ProtocolAction.JOIN, status);
		this.chatHost = chatHost;
		this.chatPort = chatPort;
	}

	public ResponseEntrar(String chatHost, int chatPort, Status status, String message) {
		super(ProtocolAction.JOIN, status, message);
		this.chatHost = chatHost;
		this.chatPort = chatPort;
	}
	
	@Override
	public String toString() {
		return "action=" + this.getAction().name() + ",status=" + this.getStatus().name() + ",chatHost=" + this.getChatHost().trim()
				+ ",chatPort=" + this.getChatPort() + ",message=\""+ this.getMessage().trim() + "\";";
	}

	public String getChatHost() {
		return chatHost;
	}

	public void setChatHost(String chatHost) {
		this.chatHost = chatHost;
	}

	public int getChatPort() {
		return chatPort;
	}

	public void setChatPort(int chatPort) {
		this.chatPort = chatPort;
	}

}
