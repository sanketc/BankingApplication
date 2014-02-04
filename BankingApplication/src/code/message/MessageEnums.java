package code.message;

public enum MessageEnums {

	CLIENT_READ,
	CLIENT_WRITE_NEW,
	CLIENT_UPDATE,
	SERVER_WRITE_NEW,
	SERVER_UPDATE,
	ERROR,
	DATA,
	ACK,
	HEALTH;
	
	@Override
	public String toString(){
		return this.name();
	}
}
