package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the main class with timer for screen updates and timer for physics.
 */
final class EnterTheArena {
    private final static Logger LOGGER = Logger.getLogger(EnterTheArena.class.getName());

    private EnterTheArena() {
    }

    //This is the main method, that's why it's static
    public static void main(String[] args) {
        final int arenaWidth = 20;
        final int arenaHeight = 20;
        final int frameWidth = 1400;
        final int frameHeight = 1000;
        final ArenaComponent arenaComponent = new ArenaComponent(frameWidth, frameHeight, arenaWidth, arenaHeight);
        final ArenaFrame arenaFrame = new ArenaFrame(frameWidth, frameHeight, arenaComponent);

        final int frameTick = 25;
        final int physicsTick = 6;

        FrameThread frameThread = new FrameThread(arenaFrame, frameTick);
        frameThread.start();

        PhysicsThread physicsThread = new PhysicsThread(arenaComponent, physicsTick);
        physicsThread.start();
    }
}
