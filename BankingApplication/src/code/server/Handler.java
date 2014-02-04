package code.server;

import code.message.Message;

public interface Handler {

	public Message serviceRequest(Message message);
}