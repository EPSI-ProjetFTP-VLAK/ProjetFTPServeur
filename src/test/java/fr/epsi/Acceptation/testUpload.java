/*package fr.epsi.Acceptation;

import java.io.*;
import java.net.Socket;

import fr.epsi.commands.Command.Copy;
import fr.epsi.commands.Core.CommandData;
import fr.epsi.server.core.ServerManager;
import fr.epsi.utils.ConfigOS;

import fr.epsi.utils.XMLParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testUpload {
    ServerManager serverManager;
    String testDirectory;
    ConfigOS os;
    String urlTestDirectory;
    Socket socket;
    DataInputStream din;
    DataOutputStream dout;
    BufferedReader br;
    PrintWriter out;

    @Before
    public void setUp(){
        testDirectory = "EnvTest";
        os = new ConfigOS();
        urlTestDirectory = os.getUrlEnv(testDirectory);

        serverManager = new ServerManager();

        serverManager.startServer();

        File destFile = new File(urlTestDirectory + "/testUpload.txt");

        if(!destFile.exists()){
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            socket = new Socket("127.0.0.1", Integer.parseInt(new XMLParser().parseXMLForNode("port")));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("test test");
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            din=new DataInputStream(socket.getInputStream());
            dout=new DataOutputStream(socket.getOutputStream());
            br=new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void  clientCanUpload(){
        String datas= "";

        try {
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(din));
            datas = bufferedReader.readLine();
            System.out.println(datas);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(din));
            datas = bufferedReader.readLine();
            System.out.println(datas);
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println("pwd::--::");
        out.flush();

        try {
            BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(din));
            datas = bufferedReader.readLine();
            System.out.println(datas);
        } catch (IOException e) {
            e.printStackTrace();
        }




        out.println("up::--::testUpload.txt");
        out.flush();

        File fileToUpload = new File(urlTestDirectory + "/testUpload.txt");

        FileInputStream fin= null;

        System.out.println("cli - Recherchedu fichier d'upload");

        try {
            fin = new FileInputStream(fileToUpload);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("cli - fichier d'upload trouvé");

        System.out.println("cli - début de l'upload");

        int ch;

        try {
            do {
                ch = fin.read();
                dout.writeUTF(String.valueOf(ch));
            }
            while (ch != -1);
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("cli - Upload terminé");

        try {
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("ci - Fermeture de l'inputStream");



        /*  -------- Download -----



        File f=new File(urlTestDirectory.replace("\\", "/") + "/testDownload.txt");

        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            f.delete();
        }

        out.println("down::--::testDownload.txt");
        out.flush();

        if(f.exists())
        {
            try {
                dout.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        System.out.println("création fciher si il existe");

        if(f.exists())
        {
            try {
                dout.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("création outpout stream");

        FileOutputStream fout= null;
        try {
            fout = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("transfert fichier");

        String temp;

        try {
            do
            {
                ch=0;
                temp=din.readUTF();
                ch=Integer.parseInt(temp);
                if(ch!=-1) {
                    fout.write(ch);
                }

            }while(ch!=-1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("transfert fichier terminé");

        try {
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("fermerture outputstream");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("fermeture du serveur");
        serverManager.stopEverything();
    }

    @After
    public void tearDown(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serverManager.stopEverything();
    }




}*/
