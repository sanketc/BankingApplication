package code.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NoSuchObjectException;

import code.common.ConfigInfo;
import code.common.Globals;
import code.data.FileManager;
import code.data.Record;
import code.message.ClientRequestMessage;
import code.message.ErrorMessage;
import code.message.HealthMessage;
import code.message.Message;
import code.message.MessageEnums;
import code.message.MessageFactory;
import code.message.ServerRequestMessage;
import code.net.RequestSender;

/**
 * Server request handler.
 * 
 * @author Sanket Chandorkar
 */
public class ServerRequestHandler implements Handler {

	private FileManager fm;

	private MessageFactory factory;
	
	public ServerRequestHandler() {
		fm = new FileManager();
		factory = new MessageFactory();
	}
	
	@Override
	public Message serviceRequest(Message message) {
		
		try {
			if(message instanceof ClientRequestMessage) 
				return handleClientRequest((ClientRequestMessage) message);
			
			if(message instanceof ServerRequestMessage) 
				return handleServerRequest((ServerRequestMessage) message);

			if(message instanceof HealthMessage) {
				if(fm.checkHealth())
					return factory.generateAckMessage();
				else
					return factory.generateErrorMessage("Data file does not exists on one or many server !!");
			}
		
		} catch (NoSuchObjectException e) {
			return factory.generateErrorMessage("No Record found !!");
		} catch (FileNotFoundException e) {
			return factory.generateErrorMessage("File not found !!");
		} catch (IOException e) {
			return factory.generateErrorMessage("Error while retriving data !!");
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return factory.generateErrorMessage("Unknown request !!");
	}

	private Message handleServerRequest(ServerRequestMessage reqMsg) throws Exception {
		switch(reqMsg.getType()) {
			case SERVER_WRITE_NEW: 
				fm.writeNewRecord(reqMsg.getRecord());
				return factory.generateAckMessage();
			case SERVER_UPDATE: 
				fm.updateRecord(reqMsg.getRecord());
				return factory.generateAckMessage();
			default: 
				return factory.generateErrorMessage("Unknown request !!");
		}
	}

	private Message handleClientRequest(ClientRequestMessage reqMsg) throws Exception {
		RequestSender sender = new RequestSender();
		Message healthMsg;
		
		switch(reqMsg.getType()) {
		case CLIENT_READ:
			long accNo = reqMsg.getRecord().getAccountNo();		
			Record rec = fm.readRecord(accNo);
			return factory.generateMessage(rec, MessageEnums.DATA);
		case CLIENT_WRITE_NEW: 
			// check health
			healthMsg = checkHealth();
			if(healthMsg instanceof ErrorMessage )
				return healthMsg;
			// write data
			fm.writeNewRecord(reqMsg.getRecord());
			for(ConfigInfo config: Globals.getServerList()) {
				Message reply = sender.sendRequest(factory.generateMessage(reqMsg.getRecord(), MessageEnums.SERVER_WRITE_NEW), config);
				if(reply instanceof ErrorMessage) {
					return reply;
				}
			}
			return factory.generateAckMessage();
		case CLIENT_UPDATE: 
			// check health
			healthMsg = checkHealth();
			if(healthMsg instanceof ErrorMessage )
				return healthMsg;
			// update data
			fm.updateRecord(reqMsg.getRecord());
			for(ConfigInfo config: Globals.getServerList()) {
				Message reply = sender.sendRequest(factory.generateMessage(reqMsg.getRecord(), MessageEnums.SERVER_UPDATE), config);
				if(reply instanceof ErrorMessage) {
					return reply;
				}
			}
			return factory.generateAckMessage();
		default: 
			return factory.generateErrorMessage("Unknown request !!");
		}
	}
	
	private Message checkHealth() throws Exception {
		RequestSender sender = new RequestSender();
		for(ConfigInfo config: Globals.getServerList()) {
			Message reply = sender.sendRequest(factory.generateHealthMessage(), config);
			if(reply instanceof ErrorMessage) {
				return reply;
			}
		}
		return factory.generateAckMessage();
	}
	
}