package code.message;

import java.io.Serializable;

import code.data.Record;

@SuppressWarnings("serial")
public class ClientRequestMessage extends Message implements Serializable{

	private Record record;
	
	public ClientRequestMessage() {
	}
	
	public ClientRequestMessage(MessageEnums type, Record record) {
		super(type);
		this.record = record;
	}

	public Record getRecord() {
		return record;
	}
}