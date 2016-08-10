package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

/**
 * Created by HitSnapper on 2016-05-02.
 */
public class FrameThread implements Runnable {
    private Thread thread;
    private ArenaFrame arenaFrame;
    private int tickSpeed;
    private String threadName;
    private ArenaComponent arenaComponent;
    private boolean unlocked;

    public FrameThread(ArenaFrame arenaFrame, int tickSpeed, ArenaComponent arenaComponent) {
        this.arenaFrame = arenaFrame;
        this.arenaComponent = arenaComponent;
        if (tickSpeed == 0){
            unlocked = true;
        }
        else {
            this.tickSpeed = 1000 / tickSpeed;
            unlocked = false;
        }
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
            long deltaTime;
            while (true) {
                deltaTime = arenaComponent.draw();
                if (!unlocked && tickSpeed - deltaTime > 0) {
                    Thread.sleep(tickSpeed - deltaTime);
                }
            }
        } catch (InterruptedException e){
            System.out.println("Interrupted " + threadName);
        }
    }
}
