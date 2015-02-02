package fr.epsi.commands.Command;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.MasterCommand;
import fr.epsi.utils.AbstractLogger;

import java.io.*;

public class Upload extends MasterCommand{
    DataInputStream din;
    DataOutputStream dout;

    public Upload(CommandData p_commandData) {
        super(p_commandData);

        try {
            if(socketIsAvailable()){
                din=new DataInputStream(clientSocket.getInputStream());
                dout=new DataOutputStream(clientSocket.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execCommand(){
        AbstractLogger.log("Upload commande en cours");
        FileOutputStream fout= null;

        /*if(destinationDirectory.exists()){
            destinationDirectory.delete();
        }

        try {
            destinationDirectory.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        if(socketIsAvailable()) {

            try {
                fout = new FileOutputStream(destinationDirectory);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            int ch = 0;
            String temp;

            do {
                try {
                    temp = din.readUTF();
                    ch = Integer.parseInt(temp);
                    if (ch != -1) {
                        fout.write(ch);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (ch != -1);

            try {
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Transfert terminé !");

        isExecuted = true;
    }

    @Override
    public String result(){
        return "upload : OK";
    }

    private boolean socketIsAvailable() {
        return clientSocket.isConnected() && clientSocket.isBound() && !clientSocket.isClosed();
    }
}
