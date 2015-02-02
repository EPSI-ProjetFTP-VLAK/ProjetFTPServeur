package fr.epsi.server.client;

import fr.epsi.commands.Core.ICommand;
import fr.epsi.utils.AbstractLogger;
import fr.epsi.utils.ThreadMaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandResolver extends ThreadMaster{
    private List<ICommand> commandList;
    private String locationOfTheClientOnTheServer;
    private boolean isWorking;

    public CommandResolver(){
        isWorking = true;
        this.commandList = new ArrayList<ICommand>();
    }

    public void run(){
        while (!stop){
            isWorking = true;
            if (thereIsCommandToExecute()){
                executelastCatchedCommand();
                commandList.remove(commandList.size() - 1);
            }
            isWorking = false;
            waitNMilliseconds(500);
        }
    }

    private boolean thereIsCommandToExecute() {
        return !commandList.isEmpty() && commandList.size() > 0;
    }

    private void executelastCatchedCommand() {
        lastCommand().execCommand();
        waitUntilCommandExecutionEnd();
        locationOfTheClientOnTheServer = lastCommand().clientLocationAfterCommandExectution();
        sendResultToClient();
    }

    private void waitUntilCommandExecutionEnd() {
        while (!lastCommand().isExecuted()) try {
                Thread.sleep(20);
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
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

    public void addCommand(ICommand newCommand){
        commandList.add(newCommand);
    }

    public List<ICommand> commandList(){
        return commandList;
    }

    public String getLocationOfTheClientOnTheServerAfterCommandExecution() {
        return locationOfTheClientOnTheServer;
    }

    public boolean isWorking() { return isWorking; }
}
