package fr.epsi.commands;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.*;

public class TestLs {
    private ICommand command;

    @Before
    public void setup() throws URISyntaxException {
        URL testEnvironnementFolderURL =  this.getClass().getClassLoader().getResource("EnvTest");
        File testEnironnementFolder = new File(testEnvironnementFolderURL.toURI());
        String testEnvironnementPath = testEnironnementFolder.toString();

        String testEnvironementPath = testEnvironnementPath;

        this.command = new Ls();
        this.command.setSourcePath(testEnvironementPath);
    }

    @Test
    public void canListAllFilesAndSubFolderFromDirectory(){
        this.command.execCommand();
        assertTrue(!this.command.result().equals("empty-folder"));
    }
}
