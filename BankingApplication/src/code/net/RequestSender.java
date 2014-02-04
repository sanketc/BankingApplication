package code.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import code.common.ConfigInfo;
import code.common.Globals;
import code.message.Message;

/**
 * Sends one request and accepts one replies.
 * 
 * @author Sanket Chandorkar
 */
public class RequestSender {
	
	public Message sendRequest(Message message, ConfigInfo config) throws Exception{

		Socket reqSocket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;

		try {
			reqSocket = new Socket(config.getAddress(), config.getPort());
			out = new ObjectOutputStream(reqSocket.getOutputStream());
			in = new ObjectInputStream(reqSocket.getInputStream());
		} catch (UnknownHostException e) {
			System.out.println("Don't know about host: " + config.getAddress());
			return null;
		} catch (IOException e) {
			System.out.println("Couldn't get I/O for the connection to: " + config.getAddress());
			return null;
		}

		Globals.logMsg("Sending message type: " + message.getType() + " to "+ config.getAddress());
		out.writeObject(message);
		out.flush();
		Globals.logMsg("Sending point, waiting for return value");

		Message reply;
		try {
			reply = (Message) in.readObject();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		Globals.logMsg("Received Message type: " + reply.getType() + " from " + config.getAddress());
		out.close();
		in.close();
		reqSocket.close();
		
		return reply;
	}
	
}