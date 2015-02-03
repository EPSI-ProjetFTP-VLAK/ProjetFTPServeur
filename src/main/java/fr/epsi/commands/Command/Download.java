package fr.epsi.commands.Command;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.MasterCommand;
import fr.epsi.utils.AbstractLogger;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class Download extends MasterCommand{
    FileInputStream fin;
    BufferedOutputStream bout;

    public Download(CommandData p_commandData) {
        super(p_commandData);
        try {
            fin = new FileInputStream(sourceDirectory);
            bout = new BufferedOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execCommand(){
        AbstractLogger.log("Transfert sortant en cours");
        FileInputStream fin= null;

        try {
            byte[] buffer = new byte[1024];
            int count;
            while ((count = fin.read(buffer)) >= 0) {
                bout.write(buffer, 0, count);
                bout.flush();
            }

            fin.close();
            bout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        isExecuted = true;
    }

    @Override
    public String result(){
        return "download : OK";
    }
}
