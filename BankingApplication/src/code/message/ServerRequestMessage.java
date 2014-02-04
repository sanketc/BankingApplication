package code.message;

import java.io.Serializable;

import code.data.Record;

@SuppressWarnings("serial")
public class ServerRequestMessage extends Message implements Serializable{

	private Record record;
	
	public ServerRequestMessage() {
	}
	
	public ServerRequestMessage(MessageEnums type, Record record) {
		super(type);
		this.record = record;
	}

	public Record getRecord() {
		return record;
	}
}