package carlorolf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArenaFrame extends JFrame {
    private ArenaComponent arenaComponent;

    public ArenaFrame(int width, int height, final ArenaComponent arenaComponent) {
        this.arenaComponent = arenaComponent;
        this.setLayout(null);

        //Initialize buttons and menu stuff
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Exit");
        JMenuItem exit = new JMenuItem("Exit");


        final ActionListener exitAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Exit?", "Exit?", JOptionPane.YES_NO_OPTION) ==
                        JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        };
        exit.addActionListener(exitAction);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        menu.add(exit);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
        arenaComponent.setBounds(0, 0, width, height);
        this.add(this.arenaComponent);

        this.pack();
        this.setSize(width, height);
        this.setVisible(true);
    }
}
