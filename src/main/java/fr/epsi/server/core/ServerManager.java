package fr.epsi.server.core;

import fr.epsi.server.client.Client;
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
        FTPServer = new Server();
    }

    public void startServer() {
        loadServer();
        FTPServer.startServer();
    }

    public void stopEverything(){
        AbstractLogger.log("Extinstion du serveur");
        stop = true;
        stopServer();
        this.interrupt();
    }
    private void stopServer() {
        FTPServer.stopServer();
    }

    public static synchronized Server getFTPServer() {
        return FTPServer;
    }

    public void displayClients(){
        AbstractLogger.log("theres is actually " + getFTPServer().getClients().size() + " clients on the server :");

        int i =0;

        for(Client client : getFTPServer().getClients()){
            AbstractLogger.log(++i + " - " + client.username() + " connection time : " + client.connectionTime());
        }
        System.out.println(getFTPServer().getClients().toString());
    }
}
