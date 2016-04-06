package carlorolf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArenaFrame extends JFrame
{
    private ArenaComponent arenaComponent;

    public ArenaFrame(int width, int height, Arena arena)
    {
	arenaComponent = new ArenaComponent(arena);
	this.setLayout(null);

	//Initialize buttons and menu stuff
	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu("Eksit");
	JMenuItem exit = new JMenuItem("Eksit");


	final ActionListener exitAction = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e) {
		if (JOptionPane.showConfirmDialog(null, "Exit?", "Exit?", JOptionPane.YES_NO_OPTION) ==
		    JOptionPane.YES_OPTION) {
		    System.exit(0);
		}
	    }
	};
	exit.addActionListener(exitAction);

	menu.add(exit);
	menuBar.add(menu);
	this.setJMenuBar(menuBar);
	arenaComponent.setBounds(0, 0, width, height);
	this.add(this.arenaComponent);

	this.pack();
	this.setSize(width, height);
	this.setVisible(true);

	final JButton play = new JButton("PLAY");
	play.setBounds(100, 100, 100, 100);
	// Initializing buttons
	final ActionListener playAction = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		arenaComponent.setGameState(GameState.INGAME);
		play.setVisible(false);
	    }
	};
	play.addActionListener(playAction);
	arenaComponent.add(play);
    }

    //Here for repaint()
    public void drawComponent(Graphics g) {
	this.arenaComponent.paintComponent(g);
    }

    public void updateKeyInput(){
	arenaComponent.updateKeyInput();
    }

    public ArenaComponent getArenaComponent(){
	return arenaComponent;
    }
}
