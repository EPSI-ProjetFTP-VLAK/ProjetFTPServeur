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

    private CommandData commandToCheck;

    public CommandListenerThread(Socket socket){
        this.clientSocket = socket;
        this.stop = false;
        this.commandResolver = new CommandResolver(socket);
        this.numberOfCommandCatch = 0;
    }

    public void run(){
        commandResolver.start();

        while(!stop){
            readDataFromSocket();

            if(isNewCommandCatch()){
                numberOfCommandCatch++;
                commandResolver.addCommand(CommandFactory.createCommand(commandToCheck));
                commandToCheck = new CommandData("empty", clientSocket);
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

        if(commandToCheck.commandExist())
            newCommandCatch = true;

        return newCommandCatch;
    }

    private void readDataFromSocket(){
        /* TO_DO léve des exceptions non fatal tout le temps !! */

        String datas = "";

        try {
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            datas = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        commandToCheck = new CommandData(datas, clientSocket);
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
