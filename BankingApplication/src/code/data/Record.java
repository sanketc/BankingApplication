package code.data;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * This class represents the structure of one record.
 * 
 * @author Sanket Chandorkar
 */
@SuppressWarnings("serial")
public class Record implements Serializable {

	private long accountNo;
	
	private String name;
	
	private double balance;
	
	private static final String SEPERATOR = "	";
	
	public Record(long accountNo, String name, double balance) {
		this.accountNo = accountNo;
		this.name = name;
		this.balance = balance;
	}

	public Record(long accountNo) {
		this.accountNo = accountNo;
		this.name = "Dummy";
		this.balance = 0.0;
	}
	
	public static Record decode(String data){
		if(data == null || data.isEmpty())
			return null;
		StringTokenizer stk = new StringTokenizer(data);
		if(stk.countTokens() != 3) {
			System.out.println("Error: Data File in incorrect format !!");
			return null;
		}
		return new Record(Long.parseLong(stk.nextToken()), stk.nextToken(), Double.parseDouble(stk.nextToken()));
	}
	
	@Override
	public String toString(){
		return accountNo + SEPERATOR + name + SEPERATOR + balance;
	}

	public long getAccountNo() {
		return accountNo;
	}
	
	public String getName() {
		return name;
	}
	
	public double getBalance() {
		return balance;
	}
}
