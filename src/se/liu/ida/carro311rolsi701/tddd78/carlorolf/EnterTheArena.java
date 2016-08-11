package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.awt.*;

/**
 * This is the main class with timer for screen updates and timer for physics.
 */
final class EnterTheArena {
    //This is the main method, that's why it's static
    public static void main(String[] args) {
        final int arenaWidth = 20;
        final int arenaHeight = 20;
        int frameWidth = 1000;
        int frameHeight = 600;
        int outsideFrame = 52;
        boolean fullScreen = true;
        if (fullScreen){
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            frameWidth = (int)dim.getWidth();
            frameHeight = (int)dim.getHeight();
            outsideFrame = 46;
        }
        final ArenaComponent arenaComponent = new ArenaComponent(frameWidth, frameHeight - outsideFrame, arenaWidth, arenaHeight);
        new ArenaFrame(frameWidth, frameHeight, fullScreen, arenaComponent);

        // Zero = unlocked
        final int frameTick = 0;
        final int physicsTick = 0;
        final int makeGraphicsTick = 0;

        FrameThread frameThread = new FrameThread(arenaComponent, frameTick, 5);
        PhysicsThread physicsThread = new PhysicsThread(arenaComponent, physicsTick, 10);
        MakeGraphicsThread makeGraphicsThread = new MakeGraphicsThread(arenaComponent, makeGraphicsTick, 5);

        frameThread.start();
        physicsThread.start();
        makeGraphicsThread.start();
    }
}
