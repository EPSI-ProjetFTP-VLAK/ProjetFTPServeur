package fr.epsi.commandFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowsCommand extends AllCommands implements TypeCommand {
	
	private String type;

	public WindowsCommand(String type) {
		this.type = type;
		}

	@Override
	public void run() {
		
		 String command[] = { "cmd.exe", "/c", "dir C:\\Users\\" };
				Process p;
				try {
						p = Runtime.getRuntime().exec(command);
						BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));  
						String line = null;  
						while ((line = in.readLine()) != null) {  
							System.out.println(line); 
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
	}


}
