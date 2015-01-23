package fr.epsi.commands;


import java.io.IOException;

public interface ICommand {
    public void execCommand();

    public void setParameters();
    public void setDesinationPath(String sourcePath);
    public void setSourcePath(String sourcePath);

    public Object result();
    public void sendResultToClient() throws IOException;


    public String clientLocationAfterCommandExectution();
    public CommandData commandData();
}
