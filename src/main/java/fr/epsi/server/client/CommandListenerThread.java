package fr.epsi.server.client;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.CommandFactory;
import fr.epsi.commands.Core.ICommand;
import fr.epsi.utils.ThreadMaster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class  CommandListenerThread extends ThreadMaster{
    private Socket clientSocket;
    private CommandResolver commandResolver;
    private int numberOfCommandCatch;
    private String locationOfTheClientOnTheServer;
    private CommandData commandToCheck;
    private static String command = "cd";

    public CommandListenerThread(Socket socket, String locationOfTheClientOnTheServer){
        this.clientSocket = socket;
        this.commandResolver = new CommandResolver();
        this.numberOfCommandCatch = 0;
        this.locationOfTheClientOnTheServer = locationOfTheClientOnTheServer;
    }

    public void run(){
        if(!commandResolver.isAlive())
            commandResolver.startThread();

        while(!stop){
            readDataFromSocket();

            if(isNewCommandCatch()){
                  sendCommandToExecution();
                addNewLs();
            }

            waitNMilliseconds(500);
        }

        if (stop = true)
            commandResolver.stopThread();
    }

    private void addNewLs() {
        if(!commandToCheck.commandType().equals("ls")){
            numberOfCommandCatch++;
            commandResolver.addCommand(CommandFactory.createCommand(LsFromHere()));
        }
    }

    private CommandData LsFromHere(){
        return new CommandData("ls::--::", locationOfTheClientOnTheServer, clientSocket);
    }

    private void sendCommandToExecution() {
        numberOfCommandCatch++;
        commandResolver.addCommand(CommandFactory.createCommand(commandToCheck));
        waitNMilliseconds(500);
        waitUntilCommandExecutionEnd();
        this.locationOfTheClientOnTheServer = commandResolver.getLocationOfTheClientOnTheServerAfterCommandExecution();
    }

    private void waitUntilCommandExecutionEnd() {
        while (commandResolver.isWorking()) try {
            Thread.sleep(20);
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
        String datas = "";
        boolean exceptionCatch = false;

        try {
            if(!clientSocket.isClosed() && clientSocket.isBound() && clientSocket.isConnected()){
                InputStream is = clientSocket.getInputStream();
                if (is != null) {
                    BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(is));
                    datas = bufferedReader.readLine();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            exceptionCatch = true;
            datas = "";
        }finally {
            if(exceptionCatch){
                this.stopThread();
            }else {
                if(datas == null){
                    datas = "error::--::";
                }else {
                    if(datas.equals(""))
                        datas = "error::--::";
                }

                commandToCheck = new CommandData(datas, this.locationOfTheClientOnTheServer, clientSocket);
            }
        }
    }

    @Override
    public void stopThread(){
        commandResolver.stopThread();
        stop = true;
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
