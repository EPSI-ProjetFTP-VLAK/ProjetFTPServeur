package fr.epsi.server.client;

import fr.epsi.commands.ICommand;
import fr.epsi.server.core.ServerManager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CommandResolver extends Thread{
    private List<ICommand> commandList;
    private Socket clientSocket;

    public CommandResolver(Socket socket){
        this.commandList = new ArrayList<ICommand>();
        this.clientSocket = socket;
    }

    public void run(){
        while (true){
            if (!commandList.isEmpty() && commandList.size() > 0){
                int lastCommandIndex = commandList.size() - 1;

                commandList.get(lastCommandIndex).setSourcePath(ServerManager.getFTPServer().getServerBaseDirectory());
                commandList.get(lastCommandIndex).execCommand();
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
}
