package fr.epsi.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public abstract class AbstractLogger {
	
	public static String fileLogs = "/logs.txt";
	
	public static void writeLogInFile(String log){
		
		ConfigOS os = new ConfigOS();
		try {
			File file = new File(os.getUrl(fileLogs));
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(log);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String readLogInFile(){
		
		 String string = "";
		 ConfigOS os = new ConfigOS();
		 File file = new File(os.getUrl(fileLogs));
         InputStream ips;
         
		try {
			ips = new FileInputStream(file.getAbsoluteFile());
			InputStreamReader ipsr=new InputStreamReader(ips);
	         BufferedReader br=new BufferedReader(ipsr);
	         
	         String line;
	         
	           while ((line = br.readLine())!=null){
	               string+=line;
	           }
	           br.close(); 
	           
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
        
       catch (Exception e){
           System.out.println(e.toString());
       }
		
		return string;
	}
	
    public synchronized static void log(String stringToDisplay){
    	String log = logPrefix() + stringToDisplay;
        System.out.println(log);
        writeLogInFile(log);
    }

    public static String logPrefix(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date).toString() + "> ";
    }

    public static String getConsoleInput(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
