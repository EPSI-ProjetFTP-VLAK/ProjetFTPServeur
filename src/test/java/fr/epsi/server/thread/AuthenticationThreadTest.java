package fr.epsi.server.thread;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class AuthenticationThreadTest extends TestCase {

    private AuthenticationThread authenticationThread;
    private Socket mockedClientSocket;

    @Before
    public void setUp() throws Exception {
        mockedClientSocket = Mockito.mock(Socket.class);

        authenticationThread = new AuthenticationThread(mockedClientSocket);
    }

    @After
    public void tearDown() throws Exception {

    }

    public void testAuthenticationSuccess() throws Exception {
        InputStream mockedInputStream = new ByteArrayInputStream("test test".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(mockedInputStream).when(mockedClientSocket).getInputStream();

        ByteArrayOutputStream mockedOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockedClientSocket.getOutputStream()).thenReturn(mockedOutputStream);

        authenticationThread.start();
        authenticationThread.join();

        assertEquals("AUTH : OK\n", mockedOutputStream.toString());
    }

    public void testAuthenticationError() throws Exception {
        InputStream mockedInputStream = new ByteArrayInputStream("test fail".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(mockedInputStream).when(mockedClientSocket).getInputStream();

        ByteArrayOutputStream mockedOutputStream = new ByteArrayOutputStream();
        Mockito.when(mockedClientSocket.getOutputStream()).thenReturn(mockedOutputStream);

        authenticationThread.start();
        authenticationThread.join();

        assertEquals("AUTH : NOK\n", mockedOutputStream.toString());
    }
}