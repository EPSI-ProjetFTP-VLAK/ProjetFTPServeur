package fr.epsi.commands;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestLs {
    private Ls command;

    @Before
    public void setup(){
        String testEnvironementPath = this.getClass().getClassLoader().getResource("EnvTest").toString().substring(6);

        this.command = new Ls();
        this.command.setSourcePath(testEnvironementPath);
    }

    @Test
    public void canListAllFilesAndSubFolderFromDirectory(){
        assertTrue(this.command.execCommand() == "");
    }
}
