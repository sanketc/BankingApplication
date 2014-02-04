package code.server;

import code.common.ConfigInfo;
import code.common.Globals;
import code.net.RequestListner;

/**
 * Bank server
 * 
 * @author Sanket Chandorkar
 */
public class BankServer {

	private String id;
	
	private ConfigInfo config;
	
	private Handler handler;
	
	public BankServer(String id, String address, int port ) throws Exception {
		this.id = id;
		Globals.initialize(id);
		config = new ConfigInfo(id, address, port);
		handler = new ServerRequestHandler();
	}

	public String getId() {
		return id;
	}
	
	public static void main(String[] args) throws Exception{
		if(args.length != 3) {
			System.out.println("Missing argument !!");
			System.out.println("Usage:");
			System.out.println("        java core.server.BankServer <ServerId> <address> <port>");
			System.out.println("Exiting program now !!");
			System.exit(Globals.SYS_FAILURE);
		}
		
		String id = null, address = null;
		int port = 0;

		try {
			id = args[0];
			address = args[1];
			port = Integer.parseInt(args[2]);
		}
		catch(Exception e) {
			System.out.println("Incorrect argument !!");
			System.out.println("Usage:");
			System.out.println("        java core.server.BankServer <String:ServerId> <String:address> <Integer:port>");
			System.out.println("Exiting program now !!");
			System.exit(Globals.SYS_FAILURE);
		}
		
		BankServer bs = new BankServer(id, address, port);
		bs.start();
	}

	private void start() throws Exception {
		RequestListner listner = new RequestListner();
		while(true) {
			handler = new ServerRequestHandler();
			listner.requestExecutor(config, handler);
		}
	}
}