package fr.epsi.server.client;

import fr.epsi.commands.ICommand;
import fr.epsi.server.core.ServerManager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CommandResolver extends Thread{
    private List<ICommand> commandList;
    private String locationOfTheClientOnTheServer;

    public CommandResolver(){
        this.commandList = new ArrayList<ICommand>();
    }

    public void run(){
        while (true){
            if (!commandList.isEmpty() && commandList.size() > 0){
                int lastCommandIndex = commandList.size() - 1;

                commandList.get(lastCommandIndex).setParameters();
                commandList.get(lastCommandIndex).setSourcePath(commandList.get(lastCommandIndex).commandData().locationOfTheClientOnTheServer());
                commandList.get(lastCommandIndex).setDesinationPath(commandList.get(lastCommandIndex).commandData().commandParameter());
                commandList.get(lastCommandIndex).execCommand();
                locationOfTheClientOnTheServer = commandList.get(lastCommandIndex).clientLocationAfterCommandExectution();
                commandList.get(lastCommandIndex).sendResultToClient();
                commandList.remove(lastCommandIndex);
            }
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
