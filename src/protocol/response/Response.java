package protocol.response;

import protocol.ProtocolAction;

public class Response {

	private ProtocolAction action;
	private Status status;
	private String message;

	public Response(ProtocolAction action, Status status, String message) {
		this.setAction(action);
		this.setStatus(status);
		this.setMessage(message);
	}
	
	public Response(ProtocolAction action, Status status) {
		this.setAction(action);
		this.setStatus(status);
		this.setMessage("");
	}
	
	@Override
	public String toString() {
		return "action=" + this.getAction().name() + ",status=" + this.getStatus().name() + ",message=\""+ this.getMessage().trim() + "\";";
	}

	public ProtocolAction getAction() {
		return action;
	}

	public void setAction(ProtocolAction action) {
		this.action = action;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
