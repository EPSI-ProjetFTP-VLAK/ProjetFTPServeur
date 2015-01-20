package fr.epsi.commands;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MasterCommand implements ICommand{
    protected Path sourcePath;
    protected File sourceDirectory;
    protected Socket clientSocket;
    protected CommandData commandData;

    public MasterCommand(CommandData p_commandData){
        this.clientSocket = p_commandData.clientSocket();
        this.commandData = p_commandData;
    }

    @Override
    public void setSourcePath(String path){
        this.sourcePath = Paths.get(path);
        setSourceDirectory();
    }

    public void setSourceDirectory(){
      sourceDirectory = new File(sourcePath.toString());
    }

    public File sourceDirectory(){
        return sourceDirectory;
    }

    public Socket clientSocket(){
        return clientSocket;
    }

    @Override
    public void execCommand() {
    }

    @Override
    public byte[] result() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;

        out = new ObjectOutputStream(bos);
        out.writeObject("unknow-request");
        byte[] arrayBytes = bos.toByteArray();
        out.close();

        return arrayBytes;
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
    };
}
