package fr.epsi.server.client;

import fr.epsi.commands.CommandFactory;
import fr.epsi.commands.ICommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class CommandListenerThread extends Thread{
    private Socket clientSocket;
    private CommandFactory factory;
    private CommandResolver commandResolver;
    private boolean stop;
    public String[] allowedCommands = {"ls"};
    private int numberOfCommandCatch;

    public CommandListenerThread(Socket socket){
        this.clientSocket = socket;
        this.factory = new CommandFactory();
        this.stop = false;
        this.commandResolver = new CommandResolver(socket);
        commandResolver.start();
    }

    public void run(){
        while(!stop){
            if(isNewCommandCatch()){
                commandResolver.addCommand(factory.createCommand(readDataFromSocket()));
                numberOfCommandCatch++;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isNewCommandCatch(){
        boolean newCommandCatch = false;

        if(!readDataFromSocket().equals("command-not-allowed"))
            newCommandCatch = true;

        return newCommandCatch;
    }

    private String readDataFromSocket(){
        String datas = "";

        try {
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            datas = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!commandExist(datas.split("-")[0])){
            datas="command-not-allowed";
        }

        return datas;
    }

    private boolean commandExist(String commandToCheck){
        boolean commandExist = false;

        for (String command : allowedCommands){
            if (command.equals(commandToCheck))
                commandExist = true;
        }

        return commandExist;
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
