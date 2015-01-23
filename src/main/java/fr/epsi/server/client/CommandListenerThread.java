package fr.epsi.server.client;

import fr.epsi.commands.CommandData;
import fr.epsi.commands.CommandFactory;
import fr.epsi.commands.ICommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class CommandListenerThread extends Thread{
    private Socket clientSocket;
    private CommandResolver commandResolver;
    private boolean stop;
    private int numberOfCommandCatch;
    private String locationOfTheClientOnTheServer;
    private CommandData commandToCheck;

    public CommandListenerThread(Socket socket, String locationOfTheClientOnTheServer){
        this.clientSocket = socket;
        this.stop = false;
        this.commandResolver = new CommandResolver();
        this.numberOfCommandCatch = 0;
        this.locationOfTheClientOnTheServer = locationOfTheClientOnTheServer;
    }

    public void run(){
        commandResolver.start();

        while(!stop){
            readDataFromSocket();

            if(isNewCommandCatch()){
                numberOfCommandCatch++;
                commandResolver.addCommand(CommandFactory.createCommand(commandToCheck));
                this.locationOfTheClientOnTheServer = commandResolver.getLocationOfTheClientOnTheServerAfterCommandExecution();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            commandResolver.stopListening();
            commandResolver.join();
            commandResolver.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private boolean isNewCommandCatch(){
        boolean newCommandCatch = false;

        if(commandToCheck.commandExist())
            newCommandCatch = true;

        return newCommandCatch;
    }

    private void readDataFromSocket(){
        /* TO_DO léve des exceptions non fatal tout le temps !! */

        String datas = "error::--::";

        try {
            if(!clientSocket.getInputStream().equals(null)){
                BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
                datas = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        commandToCheck = new CommandData(datas, this.locationOfTheClientOnTheServer, clientSocket);
    }

    public List<ICommand> commandList(){
        return commandResolver.commandList();
    }

    public void stopListener(){
        stop = true;
    }

    public int numberOfCommandCatch(){
        return numberOfCommandCatch;
    }
}
