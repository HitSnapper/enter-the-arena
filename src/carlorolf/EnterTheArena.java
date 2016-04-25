package carlorolf;

import javax.swing.*;
import java.awt.event.ActionEvent;

class EnterTheArena {
    public static void main(String[] args) {
        final int arenaWidth = 20;
        final int arenaHeight = 20;
        final int frameWidth = 700;
        final int frameHeight = 700;
	final long[] oldTime = { 0 };
	final long[] newTime = { 0 };
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

        final int frameTick = 30;
        final Timer clockTimer = new Timer(frameTick, doOneStep);
        clockTimer.start();

        final int physicsTick = frameTick / 15;
        final Timer physicsTimer = new Timer(physicsTick, physicsStep);
        physicsTimer.start();
    }
}
