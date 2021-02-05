package protocol.request;

import protocol.ProtocolAction;

public class RequestPacker {

	private String req;

	public RequestPacker(String req) {
		this.req = req;
	}

	public Request pack(String username, String chatroom) {
		ProtocolAction action = getAction();
		if (action.equals(ProtocolAction.JOIN))
			chatroom = req;
		
		if (action.equals(ProtocolAction.SEND))
			return new RequestSend(chatroom, username, req);
		return new Request(action, username, chatroom);
	}

	private ProtocolAction getAction() {
		ProtocolAction[] values = ProtocolAction.values();
		for (ProtocolAction action: values) {
			if(req.startsWith(action.name())) {
				req = req.replaceFirst(action.name(), "");
				return action;
			}
		}
		return ProtocolAction.SEND;
	}
}
