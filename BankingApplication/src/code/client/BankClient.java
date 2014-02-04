package code.client;

import code.common.CommonAPIs;
import code.common.ConfigInfo;
import code.common.Globals;
import code.data.Record;
import code.message.AckMessage;
import code.message.DataMessage;
import code.message.ErrorMessage;
import code.message.Message;

/**
 * Bank client.
 * 
 * @author Sanket Chandorkar
 */
public class BankClient {

	private String id;

	private ConfigInfo conInfo;
	
	private ClientHandler handler;
	
	public BankClient(String id, String address, int port) throws Exception {
		this.id = id;
		Globals.initialize(id);
		conInfo = new ConfigInfo(id, address, port);
		handler = new ClientHandler();
	}

	public String getId() {
		return id;
	}
	
	public static void main(String[] args) throws Exception {
		if(args.length != 3) {
			System.out.println("Missing argument !!");
			System.out.println("Usage:");
			System.out.println("        java core.server.BankClient <ClientId> <address> <port>");
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
			System.out.println("        java core.server.BankClient <String:ClientId> <String:address> <Integer:port>");
			System.out.println("Exiting program now !!");
			System.exit(Globals.SYS_FAILURE);
		}

		BankClient bc = new BankClient(id, address, port);
		bc.start();
		Globals.Finalize();
	}
	
	public void start() throws Exception {
		long accNo;
		String name;
		double balance;
		Message reply;
		
		
		while(true){
			System.out.println();
			System.out.println("*********************************************");
			System.out.println("****           Banking system            ****");
			System.out.println("*********************************************");
			System.out.println("Enter your choice:");
			System.out.println("1. Read Account Information.");
			System.out.println("2. Add New Account Inforamtion.");
			System.out.println("3. Update Account Information.");
			System.out.println("4. Exit program.");
			System.out.print("Choice: ");
			
			int ch = CommonAPIs.readInt();
			switch (ch) {
				case 1: 
					System.out.println("");
					System.out.print("Enter The account number : ");
					accNo = CommonAPIs.readLong();
					System.out.println("Sending request, Please wait !!");
					reply = handler.getAccountInfo(accNo);
					if(reply instanceof DataMessage){
						DataMessage dataReply = (DataMessage) reply;
						System.out.println(" -> Success in retriving data !");
						Record rec = dataReply.getRecord();
						System.out.println("Account info:");
						System.out.println(" -> Account No: " + rec.getAccountNo());
						System.out.println(" -> Name: " + rec.getName());
						System.out.println(" -> Balance: " + rec.getBalance());
						continue;
					} 
					if(reply instanceof ErrorMessage) {
						ErrorMessage errorMsg = (ErrorMessage) reply;
						System.out.println("Error while retriving data from server !!");
						System.out.println("Error Message: " + errorMsg.getMsg());
					}
					break;
	
				// ----------------------- add new account ------------------------
				case 2:
					System.out.println("");
					System.out.println("Enter New account information.");
					System.out.print("Enter the account number : ");
					accNo = CommonAPIs.readLong();
					System.out.print("Enter Name : ");
					name = CommonAPIs.readLine();
					System.out.print("Enter account balance : ");
					balance = CommonAPIs.readDouble();
					System.out.println("Sending request, Please wait !!");
					reply = handler.addNewAccount(new Record(accNo, name, balance));
					if(reply instanceof AckMessage){
						System.out.println("Success while writing data !!");
						continue;
					} 
					if(reply instanceof ErrorMessage) {
						ErrorMessage errorMsg = (ErrorMessage) reply;
						System.out.println("Error while writing data to server !!");
						System.out.println("Error Message: " + errorMsg.getMsg());
					}
					break;
					
				// ---------------------- update account  -------------------------
				case 3:
					System.out.println("");
					System.out.println("Enter account information to update");
					System.out.print("Enter the account number : ");
					accNo = CommonAPIs.readLong();
					System.out.print("Enter Name : ");
					name = CommonAPIs.readLine();
					System.out.print("Enter account balance : ");
					balance = CommonAPIs.readDouble();
					System.out.println("Sending request, Please wait !!");
					reply = handler.updateAccount((new Record(accNo, name, balance)));
					if(reply instanceof AckMessage){
						System.out.println("Success while updating data !!");
						continue;
					} 
					if(reply instanceof ErrorMessage) {
						ErrorMessage errorMsg = (ErrorMessage) reply;
						System.out.println("Error while updating data to server !!");
						System.out.println("Error Message: " + errorMsg.getMsg());
					}
					break;
				case 4:
					System.out.println("Exiting program normally !!");
					System.out.println();
					System.exit(Globals.SYS_SUCCESS);
					break;
				default:
					System.out.println("Unknown choice !!");
			}
			System.out.println();
			System.out.println();

		}
	}
}