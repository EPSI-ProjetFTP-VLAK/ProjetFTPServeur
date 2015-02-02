package fr.epsi.utils;

public class ThreadMaster extends Thread{
    protected boolean stop;

    public void stopThread(){
        try {
            stop = true;
            this.join();
            this.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startThread(){
        stop = false;
        this.start();
    }
    
    public void waitNMilliseconds(int N){
        try {
            Thread.sleep(N);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
}
}
