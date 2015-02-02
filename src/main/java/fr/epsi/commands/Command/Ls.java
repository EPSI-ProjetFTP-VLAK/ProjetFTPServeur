package fr.epsi.commands.Command;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.MasterCommand;
import fr.epsi.dto.FileDTO;

import java.io.*;

public class Ls extends MasterCommand {
    private File[] filesList;
    private final String prefixAnswer = "ls:";

    public Ls(CommandData p_commandData){
        super(p_commandData);
    }

    @Override
    public void execCommand() {
        this.filesList = sourceDirectory().listFiles();

        if(filesList == null)
            this.filesList = new File[0];


        isExecuted = true;
    }

    @Override
    public void sendResultToClient() {
        sendHeader();
        sendData();
    }

    private void sendData() {
        ObjectOutput out = null;
        try {
            for (int i = 0; i < filesList.length; ++i){
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.writeObject(new FileDTO(filesList[i]));
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendHeader() {
        try {
            PrintWriter clientSocketOutput = new PrintWriter(clientSocket.getOutputStream());
            clientSocketOutput.println(prefixAnswer + filesList.length);
            clientSocketOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
