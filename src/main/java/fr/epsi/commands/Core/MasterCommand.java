package fr.epsi.commands.Core;

import java.io.*;
import java.net.Socket;

public class MasterCommand implements ICommand {
    protected File sourceDirectory;
    protected File destinationDirectory;

    protected Socket clientSocket;

    protected CommandData commandData;

    protected boolean isExecuted;

    public MasterCommand(CommandData p_commandData){
        this.clientSocket = p_commandData.clientSocket();
        this.commandData = p_commandData;
        this.isExecuted = false;

        inializeFiles();
    }

    private void inializeFiles() {
        if (thereIsAtLeastTwoParameter()){
            setSourceFile(commandData.locationOfTheClientOnTheServer() + "/" + commandData.commandParameter().split("::--::")[1]);
            setDesinationFile(commandData.locationOfTheClientOnTheServer() + "/" + commandData.commandParameter().split("::--::")[0]);
        }else if(thereIsAtLeastOneParameter()){
            setSourceFile(commandData.locationOfTheClientOnTheServer());
            setDesinationFile(commandData.locationOfTheClientOnTheServer() + "/" + commandData.commandParameter());
        } else {
            setSourceFile(commandData.locationOfTheClientOnTheServer());
        }
    }

    @Override
    public CommandData commandData(){
        return this.commandData;
    }

    @Override
    public String clientLocationAfterCommandExectution(){
        return commandData.locationOfTheClientOnTheServer();
    }

    public boolean thereIsAtLeastOneParameter(){
        return this.commandData.commandParameter().split("::--::").length >= 1;
    }

    public boolean thereIsAtLeastTwoParameter(){
        return this.commandData.commandParameter().split("::--::").length >= 2;
    }

    @Override
    public void execCommand() throws IOException {
    }

    @Override
    public String result() {
        return "error";
    }

    @Override
    public void sendResultToClient(){
        try {
            PrintWriter clientSocketOutput = new PrintWriter(clientSocket.getOutputStream());
            clientSocketOutput.println(result());
            clientSocketOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isExecuted(){
        return isExecuted;
    }

    @Override
    public void setSourceFile(String sourcePath){
        sourceDirectory = new File(sourcePath);
    }

    @Override
    public void setDesinationFile(String sourcePath){
        destinationDirectory = new File(sourcePath);
    }

    public File destinationDirectory(){
        return this.destinationDirectory;
    }

    public File sourceDirectory(){
        return sourceDirectory;
    }

    public Socket clientSocket(){
        return clientSocket;
    }
}
