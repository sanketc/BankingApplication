package code.message;

import java.io.Serializable;

import code.data.Record;

@SuppressWarnings("serial")
public class DataMessage extends Message implements Serializable {

	private Record record;
	
	public DataMessage(){
	}
	
	public DataMessage(Record record) {
		super(MessageEnums.DATA);
		this.record = record;
	}

	public Record getRecord() {
		return record;
	}
}