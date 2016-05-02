package se.liu.ida.carro311rolsi701.tddd78.carlorolf;


/**
 * Created by HitSnapper on 2016-05-02.
 */
public class PhysicsThread implements Runnable {
    private Thread thread;
    private ArenaComponent arenaComponent;
    private int tickSpeed;
    private String threadName;

    public PhysicsThread(ArenaComponent arenaComponent, int tickSpeed) {
        this.arenaComponent = arenaComponent;
        this.tickSpeed = tickSpeed;
        threadName = "Physics";
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
                //deltaTime = newTime - oldTime;
                arenaComponent.update(0.5);
                thread.sleep(10);
                //if (tickSpeed - deltaTime > 0) {
                    //thread.sleep((long)(tickSpeed - deltaTime));
                //}
                //oldTime = newTime;
                //newTime = System.currentTimeMillis();
            }
        } catch (InterruptedException e){
            System.out.println("Interrupted " + threadName);
        }
    }
}
