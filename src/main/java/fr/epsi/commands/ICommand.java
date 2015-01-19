package fr.epsi.commands;


import java.io.IOException;

public interface ICommand {
    public void execCommand();
    public Object result() throws IOException;
    public void sendResultToClient();

    public void setSourcePath(String sourcePath);
}
