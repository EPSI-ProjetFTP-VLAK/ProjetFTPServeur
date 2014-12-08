package fr.epsi;

import fr.epsi.ServerCore.ServerManager;

public class Main
{
    public static void main(String[] args)
    {
        ServerManager FTPServerManager = new ServerManager();
        FTPServerManager.start();
    }
}
