package code.message;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Message  implements Serializable{

	private MessageEnums type;
	
	public Message(){
	}

	public Message(MessageEnums type) {
		this.type = type;
	}

	public MessageEnums getType() {
		return type;
	}
}