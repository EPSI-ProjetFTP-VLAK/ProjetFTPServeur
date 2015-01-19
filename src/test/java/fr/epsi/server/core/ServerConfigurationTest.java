package fr.epsi.server.core;

import junit.framework.TestCase;

public class ServerConfigurationTest extends TestCase {

    private ServerConfiguration serverConfiguration;

    @Override
    public void setUp() throws Exception {
        this.serverConfiguration = new ServerConfiguration();
    }

    public void testConfigurationLoading() throws Exception {
        assertEquals(this.serverConfiguration.serverPort(), 4010);
        assertEquals(this.serverConfiguration.serverTimeOut(), 3500);
        assertEquals(this.serverConfiguration.serverBaseDirectory(), "D:/FTPFolder");
    }
}