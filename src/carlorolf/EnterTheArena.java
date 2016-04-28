package carlorolf;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This is the main class with timer for screen updates and timer for physics.
 */
final class EnterTheArena {
    private EnterTheArena() {}

    //This is the main method, that's why it's static
    public static void main(String[] args) {
        final int arenaWidth = 20;
        final int arenaHeight = 20;
        final int frameWidth = 900;
        final int frameHeight = 900;
        final long[] oldTime = {0};
        final long[] newTime = {0};
        final ArenaComponent arenaComponent = new ArenaComponent(frameWidth, frameHeight, arenaWidth, arenaHeight);
        final ArenaFrame arenaFrame = new ArenaFrame(frameWidth, frameHeight, arenaComponent);

        final Action doOneStep = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                arenaFrame.repaint();
            }
        };

        final Action physicsStep = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                arenaComponent.update((newTime[0] - oldTime[0]) * 0.001);
                oldTime[0] = newTime[0];
                newTime[0] = e.getWhen();
            }
        };

        final int frameTick = 20;
        final Timer clockTimer = new Timer(frameTick, doOneStep);
        clockTimer.start();

        final int physicsTick = frameTick / 15;
        final Timer physicsTimer = new Timer(physicsTick, physicsStep);
        physicsTimer.start();
    }
}
