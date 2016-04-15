package carlorolf;
import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Keyboard implements KeyListener
{
    List<Action> keyPressedList;
    List<Action> keyReleasedList;
    private GameState gameState;
    private ArenaComponent arenaComponent;
    Player player;
    private final int RIGHT = 39, LEFT = 37, UP = 38, DOWN = 40, ESCAPE = 27, R = 82;

    public Keyboard(Player player, ArenaComponent arenaComponent){
	this.gameState = arenaComponent.getGameState();
	keyPressedList = new ArrayList<Action>();
	keyReleasedList = new ArrayList<Action>();
	this.player = player;
	this.arenaComponent = arenaComponent;
    }
    @Override public void keyTyped(final KeyEvent e) {

    }

    @Override public void keyPressed(final KeyEvent e) {
	if (gameState.getPhase() == Phase.INGAME) {
	    if (gameState.getState() != State.PAUSEMENU) {
		switch (e.getKeyCode()) {
		    case RIGHT:
				player.movePlayer(Direction.EAST);
				break;
		    case LEFT:
				player.movePlayer(Direction.WEST);
				break;
		    case DOWN:
				player.movePlayer(Direction.SOUTH);
				break;
		    case UP:
				player.movePlayer(Direction.NORTH);
				break;
			case R:
				player.addRecoil(new Vector(0.5, 0.5));
				break;
		}
	    }
	    if (e.getKeyCode() == ESCAPE){
			if (gameState.getState() == State.PAUSEMENU){
				arenaComponent.hidePauseMenu();
			}
			else {
				arenaComponent.showPauseMenu();
			}
	    }
	}
    }

    @Override public void keyReleased(final KeyEvent e) {
	switch(e.getKeyCode()){
	    case RIGHT:
		player.stopMovingInDirection(Direction.EAST);
		break;
	    case LEFT:
		player.stopMovingInDirection(Direction.WEST);
		break;
	    case DOWN:
		player.stopMovingInDirection(Direction.SOUTH);
		break;
	    case UP:
		player.stopMovingInDirection(Direction.NORTH);
		break;
	}
    }

    public void addKeyPress(Action action){
	keyPressedList.add(action);
    }

    public void addKeyRelease(Action action){
    	keyReleasedList.add(action);
    }
}
