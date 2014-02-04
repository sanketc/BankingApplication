package code.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import code.common.ConfigInfo;
import code.common.Globals;
import code.message.Message;
import code.server.Handler;

/**
 * Listens and handles one request.
 * 
 * @author Sanket Chandorkar
 */
public class RequestListner {

	public void requestExecutor(ConfigInfo config, Handler handle) throws Exception {
		
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(config.getPort());
		} catch (IOException e) {
			System.out.println("Could not listen on port: " + config.getPort());
			return;
		}

		Socket clientSocket = null;

		try {
			Globals.logMsg("Waiting for Client");
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.out.println("Accept failed.");
			return;
		}

		ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

		Message request = null;
		Message reply = null;

		try {
			request = (Message) in.readObject();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return;
		}

		Globals.logMsg("Server recieved Message type: " + request.getType() + " from Client.");

		reply = handle.serviceRequest(request);
		
		Globals.logMsg("Server sending Message type: " + reply.getType() + " to Client.");
		out.writeObject(reply);
		out.flush();

		// closing operations
		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
	}
}