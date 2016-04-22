package carlorolf;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Keyboard implements KeyListener {
    private GameState gameState;
    private ArenaComponent arenaComponent;
    private Arena arena;
    private final int RIGHT = 39, LEFT = 37, UP = 38, DOWN = 40, ESCAPE = 27, R = 82, SPACE = 32;

    public Keyboard(Arena arena, ArenaComponent arenaComponent) {
        this.gameState = arenaComponent.getGameState();
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
