package fr.epsi.commands;

import java.io.*;
import java.net.Socket;

public class Ls extends MasterCommand{
    private File[] filesList;
    private final String prefixAnswer = "ls:";
    private final String returnCodeForEmptyFolder = "empty" + wordDelimiter + "folder";

    public Ls(Socket socket){
        clientSocket = socket;
    }

    public byte[] serializedFilesList() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;

        out = new ObjectOutputStream(bos);
        out.writeObject(prefixAnswer);
        out.writeObject(filesList);
        byte[] arrayBytes = bos.toByteArray();
        out.close();

        return arrayBytes;
    }

    @Override
    public void execCommand() {
        this.filesList = sourceDirectory().listFiles();
    }

    @Override
    public byte[] result() throws IOException {
        return serializedFilesList();
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
