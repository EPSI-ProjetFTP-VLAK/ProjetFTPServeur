package fr.epsi.server.core;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServerTest extends TestCase {

    private Server server;

    @Before
    public void setUp() throws Exception {
        server = new Server();
    }

    @After
    public void tearDown() throws Exception {
        server.stopServer();

        assertFalse(server.getListeningThread().isAlive());
        assertTrue(server.getServerSocket().isClosed());
    }

    @Test
    public void testListensToClients() throws Exception {
        assertTrue(server.getServerSocket().isBound());

        server.startServer();

        assertTrue(server.getListeningThread().isAlive());
    }
}