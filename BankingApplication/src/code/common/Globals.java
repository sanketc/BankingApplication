package code.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Global API, Variables and Constants.
 * 
 * @author Sanket Chandorkar
 */
public class Globals {

	public static final int SYS_SUCCESS = 0;
	public static final int SYS_FAILURE = -1;
	public static final String DATA = "data";
	public static final String CONFIG = "config";
	public static final String LOG = "log";
	public static final String configFile = CONFIG + File.separator + "ServerConfigurationFile.txt";
	private static PrintWriter lowWriter;
	private static ArrayList<ConfigInfo> serverList = null;
	public static String nodeId = "NULL";
	
	public static ArrayList<ConfigInfo> getServerList() {
		return serverList;
	}
	
	public static void initialize(String id) throws Exception {
		nodeId = id;
		initializeLog(id);
		initilizeServerList(id);
	}
	
	public static void Finalize(){
		closeLog();
	}
	
	private static void initializeLog(String id){
		String logFileName = LOG + File.separator + id + "_log.txt";
		try {
			lowWriter = new PrintWriter(new FileWriter(new File(logFileName)));
		} catch (IOException e) {
			System.out.println("Error: While opening log file : " + logFileName);
			System.out.println("Exiting application now !!");
			System.exit(SYS_FAILURE);
		}
	}
	
	private static void closeLog(){
		lowWriter.close();
	}
	
	public static void logMsg(String msg){
		lowWriter.println("LOG: " + msg);
		lowWriter.flush();
	}
	
	private static void initilizeServerList(String currId) throws Exception {
		File file = new File(configFile);
		if(!file.exists()) {
			System.out.println("Error: Config file does not exits : " + configFile);
			System.out.println("Exiting application now !!");
			System.exit(SYS_FAILURE);
		}
		BufferedReader br = new BufferedReader(new FileReader(file));
		serverList = new ArrayList<ConfigInfo>();
		
		String line;
		StringTokenizer stk;
		while((line=br.readLine())!=null){
			stk = new StringTokenizer(line);
			if(stk.countTokens() != 3){
				System.out.println("Error: Config file not in correct format !!");
				System.out.println("Exiting application now !!");
				System.exit(SYS_FAILURE);
			}
			
			String id = stk.nextToken();
			String address = stk.nextToken();
			int port = Integer.parseInt(stk.nextToken());
			if(id.equals(currId))
				continue;
			ConfigInfo sInfo = new ConfigInfo(id, address, port);
			serverList.add(sInfo);
		}
		br.close();
	}
	
	public static ConfigInfo getRandomServer(){
		if(serverList == null)
			return null;
		Random rand = new Random();
		int  index = rand.nextInt(100) % serverList.size() ;
		return serverList.get(index);
	}
}