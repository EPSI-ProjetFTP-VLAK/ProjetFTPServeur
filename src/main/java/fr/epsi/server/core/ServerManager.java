package fr.epsi.server.core;

import fr.epsi.utils.AbstractLogger;
import fr.epsi.utils.ThreadMaster;

public class ServerManager extends ThreadMaster{
    private static Server FTPServer;

    public ServerManager(){
    }

    public void run(){
        while(!stop){
            String commandManager = AbstractLogger.getConsoleInput();

            if(!commandManager.isEmpty()){
                resolveManagerCommand(commandManager);
            }
        }
    }

    public void resolveManagerCommand(String command) {
        if(command.toLowerCase().equals("start")){
            startServer();
        } else if (command.toLowerCase().equals("stop")){
            stopServer();
        } else if (command.toLowerCase().equals("load")){
            loadServer();
        } else if (command.toLowerCase().equals("exit")){
            stopServer();
            stop = true;
        }else if(command.toLowerCase().equals("users")){
            displayClients();
        }
    }

    private void loadServer() {
        System.out.println("Loading Server...");
        FTPServer = new Server();
    }

    public void startServer() {
        loadServer();
        FTPServer.startServer();
    }

    private void stopServer() {
        FTPServer.stopServer();
    }

    public static synchronized Server getFTPServer() {
        return FTPServer;
    }

    public void displayClients(){
        System.out.println(getFTPServer().getClients().toString());
    }
}
