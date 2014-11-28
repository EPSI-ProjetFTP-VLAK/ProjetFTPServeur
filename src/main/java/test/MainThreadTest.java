package test;

import fr.epsi.MainThread;
import fr.epsi.XMLParser;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainThreadTest {
    private MainThread mainThread;

    @Test
    public void LeServeurCeLanceUnClientDuFichierCeConnecteSansLancerUneException(){
        boolean exceptionCatch = false;
        String args[] = null;

        mainThread = new MainThread();
        mainThread.main(args);

        try {
            XMLParser xmlParser = new XMLParser();

            Socket socket = new Socket("127.0.0.1" , Integer.parseInt(xmlParser.serverPort()));

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(xmlParser.parseAndGetUsersXMLFile().keySet().toArray()[0] + " " + xmlParser.parseAndGetUsersXMLFile().get(xmlParser.parseAndGetUsersXMLFile().keySet().toArray()[0]));
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(in.readLine());

            socket.close();
        } catch (IOException e) {
            exceptionCatch = true;
            e.printStackTrace();
        }

        assertFalse(exceptionCatch);
    }
}
