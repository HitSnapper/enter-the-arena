package carlorolf;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EnterTheArena {
    public static void main(String[] args) {
        final int arenaWidth = 16;
        final int arenaHeight = 16;
        final int frameWidth = 700;
        final int frameHeight = 700;
        final ArenaComponent arenaComponent = new ArenaComponent(frameWidth, frameHeight, arenaWidth, arenaHeight);
        final ArenaFrame arenaFrame = new ArenaFrame(frameWidth, frameHeight, arenaComponent);

        final Action doOneStep = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                arenaFrame.repaint();
            }
        };

        final long[] oldTime = {0};

        final Action physicsStep = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                arenaComponent.update();
                if (oldTime[0] != 0) {
                    DeltaTime.setDt(e.getWhen() - oldTime[0]);
                }
                oldTime[0] = e.getWhen();
            }
        };

        final int frameTick = 20;
        final Timer clockTimer = new Timer(frameTick, doOneStep);
        clockTimer.start();

        final int physicsTick = (int) (frameTick / 8);
        final Timer physicsTimer = new Timer(physicsTick, physicsStep);
        physicsTimer.start();
    }
}
