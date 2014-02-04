package code.common;

/**
 * Host Config information.
 * 
 * @author Sanket Chandorkar
 */
public class ConfigInfo {

	private String id;

	private String address;

	private int port;

	public ConfigInfo(String id, String address, int port) {
		super();
		this.id = id;
		this.address = address;
		this.port = port;
	}

	public String getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}
}