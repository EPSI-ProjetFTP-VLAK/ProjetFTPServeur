package fr.epsi.server.core;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

public class ServerTest extends TestCase {

    private Server server;

    @Before
    public void setUp() throws Exception {
        server = new Server();
    }

    @After
    public void tearDown() throws Exception {
        server.stopServer();

        assertTrue(server.getServerSocket().isClosed());
        assertFalse(server.getListeningThread().isAlive());
    }

    public void testInitialization() throws Exception {
        assertTrue(server.getServerSocket().isBound());
        assertFalse(server.getListeningThread().isAlive());
    }

    public void testStartServer() throws Exception {
        server.startServer();

        assertTrue(server.getListeningThread().isAlive());
    }
}