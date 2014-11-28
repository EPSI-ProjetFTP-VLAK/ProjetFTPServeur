package test;

import fr.epsi.XMLParser;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class XMLParserTest {
    private XMLParser xmlParser;

    @Before
    public void before(){
        xmlParser = new XMLParser();
    }

    @Test
    public void peuRetournerUneListeUtilisateurs(){
        assertTrue(!xmlParser.parseAndGetUsersXMLFile().isEmpty());
    }

    @Test
    public void peuRetournerLePortDuServeur(){
        assertTrue(!xmlParser.serverPort().isEmpty() && (Integer.parseInt(xmlParser.serverPort()) != -1));
    }

    @Test
    public void peuRetournerLeTimeOutDuServeur(){
        assertTrue(!xmlParser.serverTimeOut().isEmpty() && (Integer.parseInt(xmlParser.serverTimeOut()) != -1));
    }

    @Test
    public void peuRetournerLeRepertoirdeDeBaseDuServeur(){
        assertTrue(!xmlParser.serverBaseDirectory().isEmpty());
    }
}