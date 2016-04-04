package carlorolf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ArenaComponent extends JComponent implements ArenaListener
{
    private Dimension tileSize;
    private Arena arena;
    public ArenaComponent(Arena arena) {
	this.arena = arena;
	arena.addArenaListener(this);
	tileSize = new Dimension(40, 40);

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

    private void updateTileSize(Dimension size){
	double width = (size.getWidth()/arena.getWidth());
	double height = ((size.getHeight())/arena.getHeight());
	tileSize.setSize(width, height);
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	updateTileSize(new Dimension(getWidth(), getHeight()));
	g2d.setColor(Color.black);
	g2d.fillRect(0, 0, getWidth(), getHeight());
	for (Grass grass : arena.getGrassList()) {
	    int x = grass.getX() * (int)tileSize.getWidth();
	    int y = grass.getY() * (int)tileSize.getHeight();


	    Image tile = grass.getImage();
	    g2d.drawImage(tile, x, y, (int)tileSize.getWidth(), (int)tileSize.getHeight(), this);
	}
    }
}
