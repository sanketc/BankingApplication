package code.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;

import code.common.Globals;

/**
 * Handles file operation.
 * 
 * @author Sanket Chandorkar
 */
public class FileManager {

	public static String dataFile; 
	
	public FileManager(){
		/* create file for the first time if does not exists. */
		String serfolder = Globals.DATA + File.separator + Globals.nodeId;
		File folder = new File(serfolder);
		folder.mkdirs();
		
		dataFile = Globals.DATA + File.separator + Globals.nodeId 
				+ File.separator + "dataFile.txt";
		File file = new File(dataFile);
		if(!file.exists()){
			Globals.logMsg("Creating new dataFile : " + dataFile);
			try {
				
				new FileWriter(file);
			} catch (IOException e) {
				System.out.println("Error: Creating new datafile : " + dataFile);
				System.out.println("Exiting application now !!");
				System.exit(Globals.SYS_FAILURE);
			}
		}
	}
	
	public Record readRecord(long accNo) throws IOException, FileNotFoundException, NoSuchObjectException {
		File file = new File(dataFile);
		if(!file.exists())
			throw new FileNotFoundException();

		Record rec = null;
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(dataFile)));
			String line;
			while ((line = br.readLine()) != null) {
				rec = Record.decode(line);
				if (rec.getAccountNo() == accNo){
					br.close();
					return rec;
				}
			}
			
			br.close();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new IOException();
		}
		throw new NoSuchObjectException("No such account: " + accNo);
	}
	
	public void writeNewRecord(Record record) throws IOException, FileNotFoundException {
		File file = new File(dataFile);
		if(!file.exists())
			throw new FileNotFoundException();

		ArrayList<String> list  = new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(dataFile)));
			String line;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
			br.close();

			/* ------------- write file --------------- */

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(dataFile)));
			for(String s: list) {
				pw.println(s);
			}
			pw.println(record);
			pw.close();
			
		}
		catch(Exception e){
			throw new IOException();
		}
	}
	
	public void updateRecord(Record record) throws IOException, FileNotFoundException,  NoSuchObjectException {
		File file = new File(dataFile);
		if(!file.exists())
			throw new FileNotFoundException();
		boolean accountFound = false;
		ArrayList<Record> list  = new ArrayList<Record>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(dataFile)));
			String line;
			while ((line = br.readLine()) != null) {
				list.add(Record.decode(line));
			}
			br.close();

			/* ------------- update file --------------- */

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(dataFile)));
			for(Record r: list) {
				if(r.getAccountNo() == record.getAccountNo()){
					pw.println(record);
					accountFound = true;
					continue;
				}
				pw.println(r);
			}
			pw.close();
			
		}
		catch(Exception e){
			throw new IOException();
		}
		
		/* No record found */
		if(!accountFound)
			throw new NoSuchObjectException("No record/account found !!");
	}
	
	public boolean checkHealth() {
		File file = new File(dataFile);
		return file.exists();
	}
	
}