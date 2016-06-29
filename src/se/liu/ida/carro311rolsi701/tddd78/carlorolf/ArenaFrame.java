package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates the frame in wich the game is played
 */
public class ArenaFrame extends JFrame {

    public ArenaFrame(int width, int height, final ArenaComponent arenaComponent) {
        this.setLayout(null);
        this.setResizable(false);
        final int outsideFrame = 52;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Initialize buttons and menu stuff
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Resolution");
        JMenuItem res1 = new JMenuItem("1400 X 1000");
        final Action res1Action = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                setSize(1400, 1000);
                arenaComponent.updateResolution(1400, 1000 - outsideFrame);
            }
        };
        res1.addActionListener(res1Action);
        JMenuItem res2 = new JMenuItem("1200 X 900");
        final Action res2Action = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                setSize(1200, 900);
                arenaComponent.updateResolution(1200, 900 - outsideFrame);
            }
        };
        res2.addActionListener(res2Action);
        JMenuItem res3 = new JMenuItem("1000 X 750");
        final Action res3Action = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                setSize(1000, 750);
                arenaComponent.updateResolution(1000, 750 - outsideFrame);
            }
        };
        res3.addActionListener(res3Action);
        JMenuItem res4 = new JMenuItem("1000 X 700");
        final Action res4Action = new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                setSize(1000, 700);
                arenaComponent.updateResolution(1000, 700 - outsideFrame);
            }
        };
        res4.addActionListener(res4Action);

        menu.add(res4);
        menu.add(res3);
        menu.add(res2);
        menu.add(res1);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
        this.add(arenaComponent);

        this.pack();
        this.setSize(width, height);
        this.setVisible(true);
    }
}
