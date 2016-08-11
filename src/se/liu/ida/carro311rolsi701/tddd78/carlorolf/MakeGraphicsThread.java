package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

/**
 * Created by Linnea Sievert on 2016-08-11.
 */
public class MakeGraphicsThread implements Runnable{
    private Thread thread;
    private int tickSpeed;
    private String threadName;
    private ArenaComponent arenaComponent;
    private boolean unlocked;
    private int priority;

    public MakeGraphicsThread(ArenaComponent arenaComponent, int tickSpeed, int priority) {
        this.arenaComponent = arenaComponent;
        this.priority = priority;
        if (tickSpeed == 0){
            unlocked = true;
        }
        else {
            this.tickSpeed = 900 / tickSpeed;
            unlocked = false;
        }
        threadName = "MakeGraphics";
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
                deltaTime = arenaComponent.makeGraphics();
                if (!unlocked && tickSpeed - deltaTime > 0) {
                    Thread.sleep(tickSpeed - deltaTime);
                }
            }
        } catch (InterruptedException e){
            System.out.println("Interrupted " + threadName);
        }
    }
}
