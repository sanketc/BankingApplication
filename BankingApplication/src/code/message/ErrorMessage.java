package code.message;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ErrorMessage extends Message implements Serializable{

	private String msg;
	
	public ErrorMessage(){
	}

	public ErrorMessage(String msg) {
		super(MessageEnums.ERROR);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}