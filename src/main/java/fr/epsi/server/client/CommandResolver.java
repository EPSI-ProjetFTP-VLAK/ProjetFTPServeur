package fr.epsi.server.client;

import fr.epsi.commands.Core.ICommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandResolver extends Thread{
    private List<ICommand> commandList;
    private String locationOfTheClientOnTheServer;
    private boolean stop = false;

    public CommandResolver(){
        this.commandList = new ArrayList<ICommand>();
    }

    public void run(){
        while (!stop){
            if (!commandList.isEmpty() && commandList.size() > 0){
                int lastCommandIndex = commandList.size() - 1;

                commandList.get(lastCommandIndex).setParameters();
                commandList.get(lastCommandIndex).setSourcePath(commandList.get(lastCommandIndex).commandData().locationOfTheClientOnTheServer());
                commandList.get(lastCommandIndex).setDesinationPath(commandList.get(lastCommandIndex).commandData().commandParameter());
                commandList.get(lastCommandIndex).execCommand();
                locationOfTheClientOnTheServer = commandList.get(lastCommandIndex).clientLocationAfterCommandExectution();
                try {
                    commandList.get(lastCommandIndex).sendResultToClient();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //commandList.add(new Ls(new CommandData("ls::--::", locationOfTheClientOnTheServer, )));
                //commandList.remove(lastCommandIndex);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopListening(){
        stop = true;
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
