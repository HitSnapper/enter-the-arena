package se.liu.ida.carro311rolsi701.tddd78.carlorolf;


/**
 * Created by HitSnapper on 2016-05-02.
 */
public class PhysicsThread implements Runnable {
    private Thread thread;
    private ArenaComponent arenaComponent;
    private int tickSpeed;
    private String threadName;
    private boolean unlocked;
    private int priority;

    public PhysicsThread(ArenaComponent arenaComponent, int tickSpeed, int priority) {
        this.arenaComponent = arenaComponent;
        this.priority = priority;
        if (tickSpeed == 0){
            unlocked = true;
        }
        else {
            this.tickSpeed = 900 / tickSpeed;
            unlocked = false;
        }
        threadName = "Physics";
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
            long newTime;
            long oldTime = System.currentTimeMillis();
            while (true) {
                newTime = System.currentTimeMillis();
                deltaTime = arenaComponent.update((newTime - oldTime)*0.001);
                if (!unlocked && tickSpeed - deltaTime > 0) {
                    thread.sleep(tickSpeed - deltaTime);
                }
                oldTime = newTime;
            }
        } catch (InterruptedException e){
            System.out.println("Interrupted " + threadName);
        }
    }
}
