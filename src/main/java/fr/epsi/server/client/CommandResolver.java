package fr.epsi.server.client;

import fr.epsi.commands.ICommand;

import java.io.IOException;
import java.io.PrintWriter;
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
        String result;

        while (true){
            if (!commandList.isEmpty() && commandList.size() > 0){
                commandList.get(commandList.size() - 1).execCommand();
                sendResult(commandList.get(commandList.size() - 1).result());
                commandList.remove(commandList.size() - 1);
            }
        }
    }

    private void sendResult(String result) {
        try {
            PrintWriter clientSocketOutput = new PrintWriter(clientSocket.getOutputStream());
            clientSocketOutput.println(result);
            clientSocketOutput.flush();
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
}
