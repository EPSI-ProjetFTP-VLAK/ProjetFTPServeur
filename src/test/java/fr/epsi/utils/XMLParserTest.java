package fr.epsi.utils;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class XMLParserTest {
    private XMLParser xmlParser;

    @Before
    public void before() {
        xmlParser = new XMLParser();
    }

    @Test
    public void canParseXMLUserFile() {
        assertEquals(xmlParser.parseAndGetUsersXMLFile().size(), 1);
        assertEquals(xmlParser.parseAndGetUsersXMLFile().get("test"), "test");
    }

    @Test
    public void canParseXMLConfigurationFile() {
        assertEquals(xmlParser.parseXMLForNode("port"), "4010");
        assertEquals(xmlParser.parseXMLForNode("timeout"), "3500");
        assertEquals(xmlParser.parseXMLForNode("basedirectory"), "D:/FTPFolder");
    }
}