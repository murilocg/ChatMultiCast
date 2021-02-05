package protocol.request;

import protocol.ProtocolAction;

public class Request {

	private ProtocolAction action;
	private String username;
	private String chatroom;

	public Request(ProtocolAction action, String username, String chatroom) {
		this.setAction(action);
		this.setUsername(username);
		this.setChatroom(chatroom);
	}

	@Override
	public String toString() {
		return "action=" + this.getAction().name() + ",username=\"" + this.getUsername().trim() + "\",chatroom=\""
				+ this.getChatroom().trim() + "\";";
	}

	public ProtocolAction getAction() {
		return action;
	}

	public void setAction(ProtocolAction action) {
		this.action = action;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getChatroom() {
		return chatroom;
	}

	public void setChatroom(String chatroom) {
		this.chatroom = chatroom;
	}
}
