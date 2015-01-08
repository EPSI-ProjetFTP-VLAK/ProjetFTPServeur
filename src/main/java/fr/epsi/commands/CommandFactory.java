package fr.epsi.commands;

public class CommandFactory {
    public ICommand createCommand(String commandType){
        ICommand command = null;

        if(commandType == "ls"){
            command = new Ls();
        }

        return command;
    }
}
