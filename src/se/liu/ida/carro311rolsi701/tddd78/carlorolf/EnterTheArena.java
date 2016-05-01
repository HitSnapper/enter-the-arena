package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the main class with timer for screen updates and timer for physics.
 */
final class EnterTheArena
{
    private final static Logger LOGGER = Logger.getLogger(EnterTheArena.class.getName());

    private EnterTheArena() {}

    //This is the main method, that's why it's static
    public static void main(String[] args) {
	final int arenaWidth = 20;
	final int arenaHeight = 20;
	final int frameWidth = 1400;
	final int frameHeight = 1000;
	final long[] oldTime = { 0 };
	final long[] newTime = { 0 };
	final ArenaComponent arenaComponent = new ArenaComponent(frameWidth, frameHeight, arenaWidth, arenaHeight);
	final ArenaFrame arenaFrame = new ArenaFrame(frameWidth, frameHeight, arenaComponent);
	final int physicsTick = 10;
	final int[] slowTickCount = { 0 };

	final Action physicsStep = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		double deltaTime = newTime[0] - oldTime[0];
		if (deltaTime > physicsTick * 2) {
		    slowTickCount[0]++;
		    if (slowTickCount[0] > 4) LOGGER.log(Level.WARNING,
							 "Physics updates to seldom, may cause frame-drop and unhandled collision. Tick time: {0}, Normal tick time: " +
							 physicsTick, (newTime[0] - oldTime[0]));
		} else {
		    slowTickCount[0] = 0;
		}
		if (deltaTime < physicsTick * 1.5) {
		    arenaFrame.repaint();
		}
		arenaComponent.update(deltaTime * 0.001);
		oldTime[0] = newTime[0];
		newTime[0] = e.getWhen();
	    }
	};

	final Timer physicsTimer = new Timer(physicsTick, physicsStep);
	physicsTimer.start();
    }
}
