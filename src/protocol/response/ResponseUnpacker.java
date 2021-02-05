package protocol.response;

import protocol.ProtocolAction;
import protocol.Unpacker;

public class ResponseUnpacker{
	
	private Unpacker unpacker;
	
	public ResponseUnpacker(String input) {
		this.unpacker = new Unpacker(input);
	}
	
	public Response unpack() {
		
		String actionName = unpacker.getNextItem(0, 0);		
		String statusName = unpacker.getNextItem(0, 0);
		
		ProtocolAction action = ProtocolAction.valueOf(actionName);
		Status status = Status.valueOf(statusName);

		if(status.equals(Status.OK) && action.equals(ProtocolAction.JOIN)) 
			return unpackResponseEntrar(action, status);
		return new Response(action, status, unpacker.getNextItem(1, 1));
	}
	
	private ResponseEntrar unpackResponseEntrar(ProtocolAction action, Status status) {
		String chatHost = unpacker.getNextItem(0, 0);
		int chatPort = Integer.parseInt(unpacker.getNextItem(0, 0));
		String message = unpacker.getNextItem(1, 1);
		return new ResponseEntrar(chatHost, chatPort, status, message);
	}

}
