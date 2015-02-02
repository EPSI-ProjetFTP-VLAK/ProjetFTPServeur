package fr.epsi.commands.Core;

import java.io.IOException;

public interface ICommand {
    public void execCommand();

    public void setDesinationFile(String sourcePath);
    public void setSourceFile(String sourcePath);

    public Object result();
    public void sendResultToClient() throws IOException;


    public String clientLocationAfterCommandExectution();
    public CommandData commandData();
    public boolean isExecuted();
}
