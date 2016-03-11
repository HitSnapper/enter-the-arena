package carlorolf;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EnterTheArena
{
    public static void main(String[] args) {
	final int tileSize = 50;
    	final Arena arena = new Arena(10, 10, tileSize);
    	new ArenaFrame(arena);

    	final Action doOneStep = new AbstractAction()
    	{
    	    @Override public void actionPerformed(final ActionEvent e) {
    		arena.update();
    	    }
    	};
    	final Timer clockTimer = new Timer(500, doOneStep);
    	clockTimer.start();
    }
}
