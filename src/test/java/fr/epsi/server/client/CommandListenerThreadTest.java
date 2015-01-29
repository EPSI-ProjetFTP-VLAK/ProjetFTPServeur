package fr.epsi.server.client;

import fr.epsi.utils.XMLParser;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CommandListenerThreadTest{
    private CommandListenerThread commandListenerThread;
    private Socket mockedClientSocket;
    private String testEnvironementPath;
    OutputStream mockedOutputStream;

    @Before
    public void setUp() throws IOException {
        String os = System.getProperty("os.name");


        if(os.contains("Windows")){
            testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString().substring(6);
        }else{
            testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString();
        }

        mockedClientSocket = Mockito.mock(Socket.class);

        InputStream InputStream = new ByteArrayInputStream("ls::--::".getBytes(StandardCharsets.UTF_8));
        mockedOutputStream = new ByteArrayOutputStream();

        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();
        Mockito.doReturn(mockedOutputStream).when(mockedClientSocket).getOutputStream();

        commandListenerThread = new CommandListenerThread(mockedClientSocket, testEnvironementPath);
    }

    @Test
    public void canInterceptCommandExecuteTheCommandAndALs() throws InterruptedException {
        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.startThread();
        Thread.sleep(1000);
        commandListenerThread.stopThread();
        commandListenerThread.join();

        /* + 1 for the ls return () */
        assertEquals(1, commandListenerThread.numberOfCommandCatch());
    }

    @Test
    public void dontInterceptMalformedCommand() throws InterruptedException, IOException {
        InputStream InputStream = new ByteArrayInputStream("i'm a fake ::--:: command !".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();

        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.startThread();
        Thread.sleep(1000);
        commandListenerThread.stopThread();
        commandListenerThread.join();
        commandListenerThread.interrupt();

        assertEquals(0, commandListenerThread.numberOfCommandCatch());
    }

    @Test
    public void canIntercepetAndExectureCommandCopy() throws InterruptedException, IOException {
        File dst = new File(testEnvironementPath + "/test/testcopy.txt");
        File dstFolder = new File(testEnvironementPath + "/test/");
        File src = new File(testEnvironementPath + "/testcopy.txt");

        if(!src.exists()){
            src.createNewFile();
        }

        if(!dstFolder.exists()){
            dstFolder.createNewFile();
        }

        if(dst.exists()){
            dst.delete();
        }

        InputStream InputStream = new ByteArrayInputStream("copy::--::test/testcopy.txt::--::testcopy.txt".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();
        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.startThread();
        Thread.sleep(3000);
        commandListenerThread.stopThread();
        commandListenerThread.join();
        commandListenerThread.interrupt();

        assertTrue(src.exists());
        assertTrue(dst.exists());
        assertEquals(1+1, commandListenerThread.numberOfCommandCatch());
    }

    @Test
    public void canIntercepetAndExectureCommandMv() throws InterruptedException, IOException {
        if(!new File(testEnvironementPath + "/testmv.txt").exists()){
            new File(testEnvironementPath + "/testmv.txt").createNewFile();
        }

        if(!new File(testEnvironementPath + "/test/").exists()){
            new File(testEnvironementPath + "/test/").createNewFile();
        }

        if(new File(testEnvironementPath + "/test/testmv.txt").exists()){
            new File(testEnvironementPath + "/testmv.txt/").delete();
        }

        InputStream InputStream = new ByteArrayInputStream("copy::--::test/testmv.txt::--::testmv.txt".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();
        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.startThread();
        Thread.sleep(3000);
        commandListenerThread.stopThread();
        commandListenerThread.join();
        commandListenerThread.interrupt();

        assertEquals(1+1, commandListenerThread.numberOfCommandCatch());
        assertTrue(new File(testEnvironementPath + "/test/testmv.txt").exists());
        assertTrue(!new File(testEnvironementPath + "/testmv.txt").exists());
    }

    @Test
    public void canIntercepetAndExectureCommandPwd() throws InterruptedException, IOException {
        InputStream InputStream = new ByteArrayInputStream("pwd::--::".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();

        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.startThread();
        Thread.sleep(3000);
        commandListenerThread.stopThread();
        commandListenerThread.join();
        commandListenerThread.interrupt();

        //assertEquals(commandListenerThread.locationOfTheClientOnTheServer(), testEnvironementPath);
        assertEquals(1+1, commandListenerThread.numberOfCommandCatch());
    }

    @Test
    public void canIntercepetAndExectureCommandCd() throws InterruptedException, IOException {
        InputStream InputStream = new ByteArrayInputStream("cd::--::\\test".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();

        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.startThread();
        Thread.sleep(3000);
        commandListenerThread.stopThread();
        commandListenerThread.join();
        commandListenerThread.interrupt();

        //assertEquals(commandListenerThread.locationOfTheClientOnTheServer(), testEnvironementPath+"\\test");
        assertEquals(1+1, commandListenerThread.numberOfCommandCatch());
    }

    @Test
    public void canIntercepetAndExectureCommandError() throws InterruptedException, IOException {
        InputStream InputStream = new ByteArrayInputStream("Error::--::".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();

        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.startThread();
        Thread.sleep(3000);
        commandListenerThread.stopThread();
        commandListenerThread.join();
        commandListenerThread.interrupt();

        assertEquals(0, commandListenerThread.numberOfCommandCatch());
    }

    @Test
    public void canIntercepetAndExectureCommandMkdir() throws InterruptedException, IOException {
        if(new File(testEnvironementPath + "\\testcopy\\").exists()){
            new File(testEnvironementPath + "\\testcopy\\").delete();
        }

        InputStream InputStream = new ByteArrayInputStream("mkdir::--::testcopy/".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();
        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.startThread();
        Thread.sleep(3000);
        commandListenerThread.stopThread();
        commandListenerThread.join();
        commandListenerThread.interrupt();

        assertTrue(new File(testEnvironementPath + "\\testcopy\\").exists());
        assertEquals(1 + 1, commandListenerThread.numberOfCommandCatch());
    }

    @Test
    public void canIntercepetAndExectureCommandRm() throws InterruptedException, IOException {
        if(!new File(testEnvironementPath + "testrm.txt").exists()){
            new File(testEnvironementPath + "testrm.txt").createNewFile();
        }

        InputStream InputStream = new ByteArrayInputStream("mkdir::--::testcopy/".getBytes(StandardCharsets.UTF_8));
        Mockito.doReturn(InputStream).when(mockedClientSocket).getInputStream();
        assertEquals(0, commandListenerThread.numberOfCommandCatch());

        commandListenerThread.startThread();
        Thread.sleep(3000);
        commandListenerThread.stopThread();
        commandListenerThread.join();
        commandListenerThread.interrupt();

        assertTrue(!new File(testEnvironementPath + "\\testrm.txt").exists());
        assertEquals(1 + 1, commandListenerThread.numberOfCommandCatch());
    }
}
