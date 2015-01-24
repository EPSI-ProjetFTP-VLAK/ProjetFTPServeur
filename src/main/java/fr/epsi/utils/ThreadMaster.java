package fr.epsi.utils;

public class ThreadMaster extends Thread{
    protected boolean stop;

    public void stopThread(){
        stop = true;
    }

    public void startThread(){
        stop = false;
        this.start();
    }
}
