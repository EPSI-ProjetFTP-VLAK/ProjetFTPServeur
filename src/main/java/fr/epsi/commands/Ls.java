package fr.epsi.commands;

import java.io.*;

public class Ls extends MasterCommand{
    private File[] filesList;
    private final String prefixAnswer = "ls:";

    public Ls(CommandData p_commandData){
        super(p_commandData);
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
}
