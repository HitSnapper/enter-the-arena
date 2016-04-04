package carlorolf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ArenaComponent extends JComponent implements ArenaListener
{
    private Arena arena;
    public ArenaComponent(Arena arena) {
	this.arena = arena;
	arena.addArenaListener(this);

	final Action exitAction = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		System.exit(0);
	    }
	};

	this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "exit");
	this.getActionMap().put("exit", exitAction);
    }

    @Override public void arenaChanged() {
    repaint();
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	int tileSize = arena.getTileSize();
	for (Grass grass : arena.getGrassList()) {
	    /*
	    BufferedImage bi = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_RGB);
	    Graphics big = bi.getGraphics();
	    */
	    int x = grass.getX() * arena.getTileSize();
	    int y = grass.getY() * arena.getTileSize();


	    Image tile = grass.getImage();
	    g2d.drawImage(tile, x, y, tileSize, tileSize, this);
	}

	//g2d.setColor();
	//g2d.fill3DRect();
    }
}
