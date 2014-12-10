package fr.epsi.server.core;

import fr.epsi.utils.AbstractLogger;

public class ServerManager extends Thread{
    private Server FTPServer;
    private boolean keepRunning;

    public ServerManager(){
        keepRunning = true;
    }

    private void loadServer() {
        System.out.println("Loading Server...");
        FTPServer = new Server();
    }

    public void run(){
        while(keepRunning){
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
            keepRunning = false;
        }
    }

    public void startServer() {
        loadServer();
        FTPServer.startServer();
    }

    private void stopServer(){
        FTPServer.stopServer();
    }
}
