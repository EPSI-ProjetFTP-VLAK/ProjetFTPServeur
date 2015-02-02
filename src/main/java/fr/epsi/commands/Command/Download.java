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
        FileInputStream fin = null;

        try {
            System.out.println("srv - recherche du fichier");
            try {
                fin = new FileInputStream(destinationDirectory);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println("srv -  fichier trouvé");

            int ch;
            String temp;

            System.out.println("srv - début de la récéption");
            do {
                temp = din.readUTF();
                ch = Integer.parseInt(temp);
                if (ch != -1) {
                    dout.write(ch);
                }
            }
            while (ch != -1);
            System.out.println("srv - Récéption terminé !");
            if(fin != null)
                fin.close();
        }  catch (IOException e) {
            e.printStackTrace();
            isExecuted = true;
        }

        isExecuted = true;
    }

    @Override
    public String result(){
        return "download : OK";
    }
}
