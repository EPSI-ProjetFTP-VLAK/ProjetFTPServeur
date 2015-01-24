package fr.epsi.commands.Core;

import fr.epsi.commands.DTO.FileDTO;

import java.net.Socket;

public class CommandData {
    private final String[] allowedCommands = {"ls", "mkdir", "rm", "mv", "copy"};
    private final String parameterDelimiter = "::--::";
    private String defaultCommandType = "command not allowed" + parameterDelimiter;
    private String defaultParameter = "";

    private String commandType;
    private String firstCommandParameter;
    private Socket clientSocket;
    private String locationOfTheClientOnTheServer;
    protected FileDTO DestinationfileDTO;
    protected FileDTO SourceFileDTO;

    public CommandData(String command, String locationOfTheClientOnTheServer, Socket socket){
        if (command == null || command.isEmpty()){
            setDefaultParameters();
        }

        if(command.contains(parameterDelimiter)){
            this.commandType = command.split(parameterDelimiter)[0];
            this.firstCommandParameter = command.substring(commandType.length() + parameterDelimiter.length());
        }else{
            this.commandType = defaultCommandType;
            this.firstCommandParameter = defaultParameter;
        }

        if (!commandExist()){
            setDefaultParameters();
        }

        clientSocket = socket;
        this.locationOfTheClientOnTheServer = locationOfTheClientOnTheServer;
    }

    private void setDefaultParameters(){
        this.commandType = defaultCommandType;
        this.firstCommandParameter = defaultParameter;
    }

    public Socket clientSocket(){
        return clientSocket;
    }

    public String commandType(){
        return commandType;
    }

    public String commandParameter(){
        return firstCommandParameter;
    }

    public String locationOfTheClientOnTheServer(){ return locationOfTheClientOnTheServer; }

    public boolean commandExist(){
        boolean commandExist = false;

        for (String command : allowedCommands){
            if (command.equals(commandType))
                commandExist = true;
        }

        return commandExist;
    }
}
