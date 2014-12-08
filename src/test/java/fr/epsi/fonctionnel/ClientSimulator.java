package fr.epsi.fonctionnel;

import fr.epsi.utils.XMLParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSimulator extends Thread{
    private String username;
    private String password;
    private String host;
    private int serverPort;


    public ClientSimulator(){
        XMLParser xmlParser = new XMLParser();

        this.username = xmlParser.parseAndGetUsersXMLFile().keySet().toArray()[0].toString(); // 1er utilisateur du fichier de configuration du serveur
        this.password = xmlParser.parseAndGetUsersXMLFile().get(this.username);
        this.serverPort = Integer.parseInt(xmlParser.serverPort());
        this.host = "127.0.0.1";
    }

    public void run(){
        while(true){
            processConnectionAndAuthentificationForOneClient();

            interrupt();
        }
    }

    public void processConnectionAndAuthentificationForOneClient(){
        try {
            Socket socket = new Socket(this.host , this.serverPort);

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(this.username + " " + this.password);
            out.flush();

//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            System.out.println(in.readLine());

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processConnectionAndAuthentificationForNClient(int numberOfClient) throws InterruptedException {


            try {
                for (int i = 0; i < numberOfClient; ++i) {
                    Socket socket = new Socket(this.host , this.serverPort);

                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println(this.username + " " + this.password);
                    out.flush();

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    System.out.println(in.readLine());

                    socket.close();
                }
            } catch (IOException e) {
                System.out.println(e.toString() + " Exception throw !");
                e.printStackTrace();
            }

            System.out.println(numberOfClient + "th Client connected");
            Thread.sleep(1000);
        }
}

