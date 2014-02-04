package code.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Common APIs
 * 
 * @author Sanket Chandorkar
 */
public class CommonAPIs {

	public static String readLine() throws Exception {
		try{
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			return bufferRead.readLine();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static int readInt() throws Exception {
		return Integer.parseInt(readLine()); 
	}

	public static long readLong() throws Exception {
		return Long.parseLong(readLine()); 
	}
	
	public static double readDouble() throws Exception {
		return Double.parseDouble(readLine()); 
	}
}