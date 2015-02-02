package fr.epsi.commands.Command;

import fr.epsi.commands.Core.CommandData;
import fr.epsi.commands.Core.MasterCommand;
import fr.epsi.utils.AbstractLogger;

import java.io.*;

public class Download extends MasterCommand{
    DataInputStream din;
    DataOutputStream dout;

    public Download(CommandData p_commandData) {
        super(p_commandData);
        try {
            din=new DataInputStream(clientSocket.getInputStream());
            dout=new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execCommand(){
        AbstractLogger.log("Transfert entrant en cours");
        if(destinationDirectory().exists()){
            try {
                destinationDirectory().delete();
                destinationDirectory().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("début de la récéption");
        try {
            byte[] b = new byte[1024];
            int len = 0;
            int bytcount = 1024;
            FileOutputStream inFile = null;

                inFile = new FileOutputStream(destinationDirectory);


            InputStream is = clientSocket.getInputStream();
            BufferedInputStream in2 = new BufferedInputStream(is, 1024);
            while ((len = in2.read(b, 0, 1024)) != -1) {
                System.out.println("Init writting");
                bytcount = bytcount + 1024;
                inFile.write(b, 0, len);
                System.out.println("Bytes Writen : " + bytcount);
            }


            // Sending the response back to the client.
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.flush();
            System.out.println("Transfert fini ;)");

            in2.close();
            inFile.close();
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
