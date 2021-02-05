package protocol.request;

import protocol.ProtocolAction;
import protocol.Unpacker;

public class RequestUnpacker {

	private Unpacker unpacker;

	public RequestUnpacker(String input) {
		this.unpacker = new Unpacker(input);
	}
	
	public Request unpack() {
		String actionName = unpacker.getNextItem(0, 0);
		String username = unpacker.getNextItem(1, 1);
		String chatroom = unpacker.getNextItem(1, 1);

		ProtocolAction action = ProtocolAction.valueOf(actionName);
		
		if (action.equals(ProtocolAction.SEND))
			return unpackRequestSend(action, username, chatroom);
		return new Request(action, username, chatroom);
	}

	private RequestSend unpackRequestSend(ProtocolAction action, String username, String chatroom) {
		String message = unpacker.getNextItem(1, 1);
		return new RequestSend(chatroom, username, message);
	}
}
