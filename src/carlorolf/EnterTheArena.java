package carlorolf;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EnterTheArena
{
    public static void main(String[] args) {
	final int arenaWidth = 16;
	final int arenaHeight = 16;
	final int frameWidth = 700;
	final int frameHeight = 700;
	final ArenaComponent arenaComponent = new ArenaComponent(frameWidth, frameHeight, arenaWidth, arenaHeight);
    	final ArenaFrame arenaFrame = new ArenaFrame(frameWidth, frameHeight, arenaComponent);

    	final Action doOneStep = new AbstractAction()
    	{
    	    @Override public void actionPerformed(final ActionEvent e) {
    		arenaComponent.update();
		arenaFrame.repaint();
		//arenaFrame.getArenaComponent().setSize(arenaFrame.getSize());
    	    }
    	};
    	final Timer clockTimer = new Timer(20, doOneStep);
    	clockTimer.start();
    }
}
