package fr.epsi.ServerCore;

import fr.epsi.Utils.AbstractLogger;

public class ServerManager extends Thread{
    private Server FTPServer;
    private boolean keepRunning;
    private boolean isLoaded;

    public ServerManager(){
        keepRunning = true;
        isLoaded = false;
    }

    public void loadServer() {
        FTPServer = new Server();
        isLoaded = true;
    }

    public void run(){
        while(keepRunning){
            String commandManager = AbstractLogger.getConsoleInput();

            if(!commandManager.isEmpty()){
                resolveManagerCommand(commandManager);
            }
        }
    }

    private void resolveManagerCommand(String command) {
        if(command.toLowerCase().equals("start")){
            if(isLoaded)
                startServer();
        }else if(command.toLowerCase().equals("stop")){
            stopServer();
        }else if(command.toLowerCase().equals("load")){
            loadServer();
        }else if(command.toLowerCase().equals("exit")){
            stopServer();
            stopManagerThread();
        }
    }

    private void stopManagerThread(){
        keepRunning = false;
    }

    public void startServer(){
        FTPServer.startServer();
    }

    public void stopServer(){
        FTPServer.stopServer();
    }
}
