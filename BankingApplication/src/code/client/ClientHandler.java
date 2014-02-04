package code.client;

import code.common.ConfigInfo;
import code.common.Globals;
import code.data.Record;
import code.message.Message;
import code.message.MessageEnums;
import code.message.MessageFactory;
import code.net.RequestSender;

/**
 * Client request handler. 
 * 
 * @author Sanket Chandorkar
 */
public class ClientHandler {

	private RequestSender sender;
	
	private MessageFactory factory; 
	
	public ClientHandler() {
		sender = new RequestSender();
		factory = new MessageFactory();
	}
	
	public Message getAccountInfo(long accNo) throws Exception {
		ConfigInfo serverConfig = Globals.getRandomServer();
		Message reqMsg = factory.generateMessage(new Record(accNo), MessageEnums.CLIENT_READ);
		return sender.sendRequest(reqMsg, serverConfig);
	}
	
	public Message addNewAccount(Record record) throws Exception {
		ConfigInfo serverConfig = Globals.getRandomServer();
		Message reqMsg = factory.generateMessage(record, MessageEnums.CLIENT_WRITE_NEW);
		return sender.sendRequest(reqMsg, serverConfig);
	}

	public Message updateAccount(Record record) throws Exception {
		ConfigInfo serverConfig = Globals.getRandomServer();
		Message reqMsg = factory.generateMessage(record, MessageEnums.CLIENT_UPDATE);
		return sender.sendRequest(reqMsg, serverConfig);
	}
}
