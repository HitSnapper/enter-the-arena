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

    public FrameThread(ArenaFrame arenaFrame, int tickSpeed, ArenaComponent arenaComponent) {
        this.arenaFrame = arenaFrame;
        this.tickSpeed = tickSpeed;
        threadName = "Frame";
        this.arenaComponent = arenaComponent;
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
            double deltaTime;
            long newTime = 0;
            long oldTime = 0;
            while (true) {
                deltaTime = newTime - oldTime;
                arenaComponent.updateFrameTick((int)deltaTime);
                arenaFrame.repaint();
                if (tickSpeed - deltaTime > 0) {
                    Thread.sleep((long)(tickSpeed - deltaTime));
                }
                oldTime = newTime;
                newTime = System.currentTimeMillis();
            }
        } catch (InterruptedException e){
            System.out.println("Interrupted " + threadName);
        }
    }
}
