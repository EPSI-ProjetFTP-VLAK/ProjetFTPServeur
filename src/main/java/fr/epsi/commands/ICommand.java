package fr.epsi.commands;


public interface ICommand {
    public void execCommand();
    public String result();

    public void setSourcePath(String sourcePath);
}
