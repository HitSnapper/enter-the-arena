package carlorolf;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EnterTheArena
{
    public static void main(String[] args) {
	final int arenaWidth = 10;
	final int arenaHeight = 10;
	final int frameWidth = 800;
	final int frameHeight = 800;
    	final Arena arena = new Arena(arenaWidth, arenaHeight);
    	final ArenaFrame arenaFrame = new ArenaFrame(frameWidth, frameHeight, arena);

    	final Action doOneStep = new AbstractAction()
    	{
    	    @Override public void actionPerformed(final ActionEvent e) {
    		arena.update();
    	    }
    	};
    	final Timer clockTimer = new Timer(20, doOneStep);
    	clockTimer.start();
    }
}
