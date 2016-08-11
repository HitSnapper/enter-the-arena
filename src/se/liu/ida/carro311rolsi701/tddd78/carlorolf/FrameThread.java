package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

/**
 * Created by HitSnapper on 2016-05-02.
 */
public class FrameThread implements Runnable {
    private Thread thread;
    private int tickSpeed;
    private String threadName;
    private ArenaComponent arenaComponent;
    private boolean unlocked;
    private int priority;

    public FrameThread(ArenaComponent arenaComponent, int tickSpeed, int priority) {
        this.arenaComponent = arenaComponent;
        this.priority = priority;
        if (tickSpeed == 0){
            unlocked = true;
        }
        else {
            //900 to increase tick speed a little
            this.tickSpeed = 900 / tickSpeed;
            unlocked = false;
        }
        threadName = "Frame";
    }

    public void start(){
        if (thread == null){
            thread = new Thread(this, threadName);
            thread.setPriority(priority);
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
