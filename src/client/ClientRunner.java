package client;

import java.io.IOException;
import java.util.Scanner;

import protocol.ProtocolAction;
import protocol.request.Request;
import protocol.request.RequestPacker;
import protocol.response.Response;
import protocol.response.ResponseEntrar;
import protocol.response.ResponseUnpacker;
import protocol.response.Status;

public class ClientRunner {
	public static void main(String[] args) throws IOException {

		Scanner scan = new Scanner(System.in);
		System.out.println("Type your username:");
		Client client = new Client(scan.nextLine());
		
		System.out.println("Now, enter in a classroom with the command JOIN <className> :");
		Request req = null;
		
		do {
			String input = scan.nextLine();

			RequestPacker packer = new RequestPacker(input);
			req = packer.pack(client.getUsername(), client.getChatroom());

			ResponseUnpacker unpacker = new ResponseUnpacker(client.send(req));
			Response res = unpacker.unpack();

			if (res.getStatus().equals(Status.ERROR))
				handleErrorResponse(res, client, scan);
			else
				handleSuccessResponse(req, res, client);
			
		} while (!req.getAction().equals(ProtocolAction.LEAVE));
		
		scan.close();
		client.close();
	}

	public static void handleSuccessResponse(Request req, Response response, Client cliente) throws IOException {
		if (!req.getAction().equals(ProtocolAction.JOIN))
			return;
		cliente.setChatroom(req.getChatroom());
		ResponseEntrar entrarResp = (ResponseEntrar) response;
		String chatHost = entrarResp.getChatHost();
		int chatPort = entrarResp.getChatPort();
		cliente.start(chatHost, chatPort);
	}

	public static void handleErrorResponse(Response res, Client client, Scanner scan) {
		System.out.println(res.getMessage());
		if(res.getAction().equals(ProtocolAction.JOIN)) {
			System.out.println("Please, type a new username:");
			String username = scan.nextLine();
			client.setUsername(username);
		}
	}

}
