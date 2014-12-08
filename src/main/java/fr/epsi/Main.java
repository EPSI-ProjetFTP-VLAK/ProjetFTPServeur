package fr.epsi;

import fr.epsi.server.core.ServerManager;

public class Main
{
    public static void main(String[] args)
    {
        ServerManager FTPServerManager = new ServerManager();
        FTPServerManager.start();
    }
}
