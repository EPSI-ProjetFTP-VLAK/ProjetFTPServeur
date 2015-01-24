package fr.epsi.server.client;

import fr.epsi.commands.Core.ICommand;
import fr.epsi.utils.ThreadMaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandResolver extends ThreadMaster{
    private List<ICommand> commandList;
    private String locationOfTheClientOnTheServer;

    public CommandResolver(){
        this.commandList = new ArrayList<ICommand>();
    }

    public void run(){
        while (!stop){
            if (thereIsCommandToExecute()){
                executelastCatchedCommand();
                commandList.remove(commandList.size() - 1);
            }
            waintNSeconds(500);
        }
    }

    private boolean thereIsCommandToExecute() {
        return !commandList.isEmpty() && commandList.size() > 0;
    }

    private void executelastCatchedCommand() {
        lastCommand().execCommand();
        locationOfTheClientOnTheServer = lastCommand().clientLocationAfterCommandExectution();
        sendResultToClient();
    }

    private ICommand lastCommand() {
        return  commandList.get(commandList.size() - 1);
    }

    private void sendResultToClient() {
        try {
            lastCommand().sendResultToClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waintNSeconds(int N) {
        try {
            Thread.sleep(N);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addCommand(ICommand newCommand){
        commandList.add(newCommand);
    }

    public List<ICommand> commandList(){
        return commandList;
    }

    public String getLocationOfTheClientOnTheServerAfterCommandExecution() {
        return locationOfTheClientOnTheServer;
    }
}
