package protocol.request;

import protocol.ProtocolAction;

public class RequestSend extends Request {

	private String message;

	public RequestSend(String chatroom, String username, String message) {
		super(ProtocolAction.SEND, username, chatroom);
		this.setMessage(message);
	}

	@Override
	public String toString() {
		return "action=" + this.getAction().name() + ",username=\"" + this.getUsername().trim() + "\",chatroom=\""
				+ this.getChatroom().trim() + "\",message=\"" + this.getMessage().trim() + "\";";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
