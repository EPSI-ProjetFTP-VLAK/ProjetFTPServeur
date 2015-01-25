package fr.epsi.utils;

public class ConfigOS {
	
	String os;
	
	public ConfigOS(){
		os = System.getProperty("os.name");
	}
	
	public String getUrl(String testDirectory){
		
		testEnvironementPath = this.getClass().getResource(testDirectory).toString();
		
		if(os.contains("Windows")){
			return testEnvironementPath.substring(6);
		}else{
			return testEnvironementPath;
		}
	}
	
	public String getUrlEnv(String testDirectory){
		
		testEnvironementPath = this.getClass().getClassLoader().getResource(testDirectory).toString();
		
		if(os.contains("Windows")){
			return testEnvironementPath.substring(6);
		}else{
			return testEnvironementPath;
		}
	}
	
		private String testEnvironementPath = "";
}
