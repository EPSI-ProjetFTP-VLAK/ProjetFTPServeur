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
    private CommandResolver commandResolver;
    private boolean stop;
    public String[] allowedCommands = {"ls"};
    private int numberOfCommandCatch;

    private String dataFromSocket = "";

    public CommandListenerThread(Socket socket){
        this.clientSocket = socket;
        this.stop = false;
        this.commandResolver = new CommandResolver(socket);
    }

    public void run(){
        commandResolver.start();

        while(!stop){
            readDataFromSocket();
            if(isNewCommandCatch()){
                commandResolver.addCommand(CommandFactory.createCommand(dataFromSocket, clientSocket));
                numberOfCommandCatch++;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        commandResolver.interrupt();
    }

    private boolean isNewCommandCatch(){
        boolean newCommandCatch = false;

        if(!dataFromSocket.equals("command-not-allowed"))
            newCommandCatch = true;

        return newCommandCatch;
    }

    private void readDataFromSocket(){
        String datas = "";

        try {
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            datas = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!commandExist(datas)){
            datas="command-not-allowed";
        }

        dataFromSocket = datas;
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
