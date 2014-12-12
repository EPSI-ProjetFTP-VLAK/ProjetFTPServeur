package fr.epsi.commandFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowsCommand extends AllCommands implements TypeCommand {
	
	public WindowsCommand(String type, String path) {
		this.type = type;
		this.path = path;
		}

	@Override
	public String run() {
		
		StringBuffer output = new StringBuffer();
		 String command[] = { "cmd.exe", "/c", type, path};
				Process p;
				try {
						p = Runtime.getRuntime().exec(command);
						BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));  
						String line = "";  
						while ((line = in.readLine()) != null) {  
							output.append(line + "\n");
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				return output.toString();
	}

	private String type;
	private String path;

}
