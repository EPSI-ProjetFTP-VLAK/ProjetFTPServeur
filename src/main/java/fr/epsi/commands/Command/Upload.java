package fr.epsi.commands.Command;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.MasterCommand;
import fr.epsi.utils.AbstractLogger;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class Upload extends MasterCommand{
    BufferedInputStream bin;
    FileOutputStream fout;

    public Upload(CommandData p_commandData) {
        super(p_commandData);

        try {
            if(socketIsAvailable()){
                bin = new BufferedInputStream(clientSocket.getInputStream());
                fout = new FileOutputStream(destinationDirectory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execCommand(){

        AbstractLogger.log("Transfert entrant en cours");
        AbstractLogger.log("Réception du fichier " + destinationDirectory().toString() + " en cours");

        try {
            // IOUtils.copy(fin, fout);

            byte[] buffer = new byte[1024];
            int count;
            while((count = bin.read(buffer)) >= 0){
                fout.write(buffer);
            }

            bin.close();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
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
