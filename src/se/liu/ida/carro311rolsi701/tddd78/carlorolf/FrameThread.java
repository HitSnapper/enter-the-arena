package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

/**
 * Created by HitSnapper on 2016-05-02.
 */
public class FrameThread implements Runnable {
    private Thread thread;
    private ArenaFrame arenaFrame;
    private int tickSpeed;
    private String threadName;

    public FrameThread(ArenaFrame arenaFrame, int tickSpeed) {
        this.arenaFrame = arenaFrame;
        this.tickSpeed = tickSpeed;
        threadName = "Frame";
    }

    public void start(){
        if (thread == null){
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    @Override
    public void run() {
        System.out.println("Running " + threadName);
        try {
            while (true) {
                arenaFrame.repaint();
                thread.sleep(10);
            }
        } catch (InterruptedException e){
            System.out.println("Interrupted " + threadName);
        }
    }
}
