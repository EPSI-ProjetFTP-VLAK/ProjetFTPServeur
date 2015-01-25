package fr.epsi.server.client;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.CommandFactory;
import fr.epsi.commands.Core.ICommand;
import fr.epsi.utils.ThreadMaster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class  CommandListenerThread extends ThreadMaster{
    private Socket clientSocket;
    private CommandResolver commandResolver;
    private int numberOfCommandCatch;
    private String locationOfTheClientOnTheServer;
    private CommandData commandToCheck;

    public CommandListenerThread(Socket socket, String locationOfTheClientOnTheServer){
        this.clientSocket = socket;
        this.commandResolver = new CommandResolver();
        this.numberOfCommandCatch = 0;
        this.locationOfTheClientOnTheServer = locationOfTheClientOnTheServer;
    }

    public void run(){
        if(!commandResolver.isAlive())
            commandResolver.start();

        while(!stop){
            readDataFromSocket();

            if(isNewCommandCatch()){
                sendCommandToExecution();
            }
        }

        if (stop = true)
            stopCommandResolverThread();
    }

    private void sendCommandToExecution() {
        numberOfCommandCatch++;
        commandResolver.addCommand(CommandFactory.createCommand(commandToCheck));
        waitNMilliseconds(500);
        this.locationOfTheClientOnTheServer = commandResolver.getLocationOfTheClientOnTheServerAfterCommandExecution();
    }

    private void stopCommandResolverThread() {
        try {
            commandResolver.stopThread();
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

    public void waitNMilliseconds(int N){
        try {
            Thread.sleep(N);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        if(datas == null){
            datas = "error";
        }

        commandToCheck = new CommandData(datas, this.locationOfTheClientOnTheServer, clientSocket);
    }

    public List<ICommand> commandList(){
        return commandResolver.commandList();
    }

    public int numberOfCommandCatch(){
        return numberOfCommandCatch;
    }

    public String locationOfTheClientOnTheServer(){
        return locationOfTheClientOnTheServer;
    }
}
