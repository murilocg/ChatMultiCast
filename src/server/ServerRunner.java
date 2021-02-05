package server;

import java.io.IOException;

import config.ConfigServer;
import protocol.ProtocolAction;
import protocol.request.Request;
import protocol.request.RequestSend;
import protocol.request.RequestUnpacker;
import protocol.response.Response;
import protocol.response.ResponseEntrar;
import protocol.response.Status;

public class ServerRunner {

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		System.out.println("Server runnig on port: " + ConfigServer.UDP_PORT);
		while (true) {
			String data = server.receive();
			System.out.println("Request: " + data);
			
			RequestUnpacker unpacker = new RequestUnpacker(data);
			Request req = unpacker.unpack();
			
			Response res = handleRequest(req, server);
			String resFormat = res.toString();
			System.out.println("Response:" + resFormat);
			server.reply(resFormat);
		}
	}

	public static Response handleRequest(Request req, Server server) throws IOException {
		switch (req.getAction()) {
		case JOIN:
			return handleRequestJoin(req, server);
		case LEAVE:
			return handleRequestLeave(req, server);
		case SEND:
			return handleRequestSend(req, server);
		default:
			return new Response(ProtocolAction.INVALID, Status.ERROR, "Invalid protocol action");
		}
	}
	
	public static Response handleRequestJoin(Request req, Server server) throws IOException {
		Chatroom chatroom = server.joinChat(req.getUsername(), req.getChatroom());
		if(chatroom == null) 
			return new Response(ProtocolAction.JOIN, Status.ERROR, "there is already a user with that name in the chat");
		return new ResponseEntrar(chatroom.getHost(), chatroom.getPort(), Status.OK);
	}
	
	public static Response handleRequestLeave(Request req, Server server) throws IOException {
		boolean removed = server.leaveChat(req.getUsername(), req.getChatroom());
		if(!removed) 
			return new Response(ProtocolAction.LEAVE, Status.ERROR, "You're not in any chat");
		return new Response(ProtocolAction.LEAVE, Status.OK);
	}
	
	public static Response handleRequestSend(Request req, Server server) throws IOException {
		RequestSend reqSend = (RequestSend) req;
		boolean sended = server.sendMessage(reqSend.getUsername(), reqSend.getMessage());
		if(!sended)
			return new Response(ProtocolAction.SEND, Status.ERROR, "You need to join in a chatroom to send messages.");
		return new Response(ProtocolAction.SEND, Status.OK);
	}

}
