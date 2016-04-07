package carlorolf;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Keyboard implements KeyListener
{
    List<Action> keyPressedList;
    List<Action> keyReleasedList;
    Player player;

    public Keyboard(Player player){
	keyPressedList = new ArrayList<Action>();
	keyReleasedList = new ArrayList<Action>();
	this.player = player;
    }
    @Override public void keyTyped(final KeyEvent e) {

    }

    @Override public void keyPressed(final KeyEvent e) {
	for (Action action : keyPressedList) {
	    action.notify();
	}
	player.movePlayer(Direction.NORTH);
    }

    @Override public void keyReleased(final KeyEvent e) {
	for (Action action : keyReleasedList) {
	    action.notify();
	}
    }

    public void addKeyPress(Action action){
	keyPressedList.add(action);
    }

    public void addKeyRelease(Action action){
    	keyReleasedList.add(action);
    }
}
