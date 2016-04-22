package carlorolf;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Keyboard implements KeyListener {
    List<Action> keyPressedList;
    List<Action> keyReleasedList;
    private GameState gameState;
    private ArenaComponent arenaComponent;
    private Arena arena;
    private final int RIGHT = 39, LEFT = 37, UP = 38, DOWN = 40, ESCAPE = 27, R = 82, SPACE = 32;

    public Keyboard(Arena arena, ArenaComponent arenaComponent) {
        this.gameState = arenaComponent.getGameState();
        keyPressedList = new ArrayList<Action>();
        keyReleasedList = new ArrayList<Action>();
        this.arena = arena;
        this.arenaComponent = arenaComponent;
    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (gameState.getPhase() == Phase.INGAME) {
            if (gameState.getState() != State.PAUSEMENU) {
                if (!arena.getPlayer().isDead()) {
                    switch (e.getKeyCode()) {
                        case RIGHT:
                            arena.getPlayer().movePlayer(Direction.EAST);
                            break;
                        case LEFT:
                            arena.getPlayer().movePlayer(Direction.WEST);
                            break;
                        case DOWN:
                            arena.getPlayer().movePlayer(Direction.SOUTH);
                            break;
                        case UP:
                            arena.getPlayer().movePlayer(Direction.NORTH);
                            break;
                        case R:
                            arena.getPlayer().death();
                            break;
                        case SPACE:
                            arena.getPlayer().hit();
                            break;
                    }
                }
            }
            if (e.getKeyCode() == ESCAPE) {
                if (gameState.getState() == State.PAUSEMENU) {
                    arenaComponent.hidePauseMenu();
                } else {
                    arenaComponent.showPauseMenu();
                }
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case RIGHT:
                arena.getPlayer().stopMovingInDirection(Direction.EAST);
                break;
            case LEFT:
                arena.getPlayer().stopMovingInDirection(Direction.WEST);
                break;
            case DOWN:
                arena.getPlayer().stopMovingInDirection(Direction.SOUTH);
                break;
            case UP:
                arena.getPlayer().stopMovingInDirection(Direction.NORTH);
                break;
        }
    }
}
