package code.message;

import code.data.Record;

/**
 * Factory for message generation.
 *  
 * @author Sanket Chandorkar
 */
public class MessageFactory {

	public Message generateMessage(Record record, MessageEnums type){
		switch(type){
			case DATA: return new DataMessage(record);
			case SERVER_UPDATE : return new ServerRequestMessage(MessageEnums.SERVER_UPDATE, record);
			case SERVER_WRITE_NEW : return new ServerRequestMessage(MessageEnums.SERVER_WRITE_NEW, record);
			case CLIENT_READ : return new ClientRequestMessage(MessageEnums.CLIENT_READ, record);
			case CLIENT_UPDATE : return new ClientRequestMessage(MessageEnums.CLIENT_UPDATE, record);
			case CLIENT_WRITE_NEW : return new ClientRequestMessage(MessageEnums.CLIENT_WRITE_NEW, record);
			default: return null;
		}
	}
	
	public Message generateErrorMessage(String message){
		return new ErrorMessage(message);
	}
	
	public Message generateAckMessage(){
		return new AckMessage();
	}

	public Message generateHealthMessage(){
		return new HealthMessage();
	}
}