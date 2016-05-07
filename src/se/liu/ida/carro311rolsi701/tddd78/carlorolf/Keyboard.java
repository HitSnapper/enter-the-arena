package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Keyboard is added as a keyboardListener in ArenaComponent to register key presses and key releases.
 */
class Keyboard extends KeyAdapter {
    private GameState gameState;
    private ArenaComponent arenaComponent;
    private Arena arena;
    //Name "UP" and "R" is equivalent to button name and they're always the same, that's why they are static
    @SuppressWarnings("ConstantNamingConvention")
    private static final int RIGHT = 39, LEFT = 37, UP = 38, DOWN = 40, ESCAPE =
            27, R = 82, SPACE = 32, SHIFT = 16, F3 = 114, W = 87, D = 68, A = 65, S = 83, CTRL = 17;

    Keyboard(Arena arena, ArenaComponent arenaComponent) {
        this.gameState = arenaComponent.getGameState();
        this.arena = arena;
        this.arenaComponent = arenaComponent;
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (gameState.getPhase() == Phase.INGAME) {
            if (gameState.getState() != State.PAUSEMENU) {
                switch (e.getKeyCode()) {
                    case RIGHT:
                        arena.getPlayer(0).movePlayer(Direction.EAST);
                        break;
                    case LEFT:
                        arena.getPlayer(0).movePlayer(Direction.WEST);
                        break;
                    case DOWN:
                        arena.getPlayer(0).movePlayer(Direction.SOUTH);
                        break;
                    case UP:
                        arena.getPlayer(0).movePlayer(Direction.NORTH);
                        break;
                    case R:
                        for (Player player : arena.getPlayers()) {
                            player.revive();
                        }
                        break;
                    case SPACE:
                        arena.getPlayer(0).hit();
                        break;
                    case F3:
                        arenaComponent.toggleDebug();
                }
                if (arena.getNumberOfPlayers() > 1) {
                    switch (e.getKeyCode()) {
                        case W:
                            arena.getPlayer(1).movePlayer(Direction.NORTH);
                            break;
                        case D:
                            arena.getPlayer(1).movePlayer(Direction.EAST);
                            break;
                        case S:
                            arena.getPlayer(1).movePlayer(Direction.SOUTH);
                            break;
                        case A:
                            arena.getPlayer(1).movePlayer(Direction.WEST);
                            break;
                        case CTRL:
                            arena.getPlayer(1).hit();
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
        if (e.getKeyCode() == SHIFT) {
            if (gameState.getState() == State.PLAYMENU) {
                gameState.setState(State.NONE);
            } else {
                gameState.setState(State.PLAYMENU);
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case RIGHT:
                arena.getPlayer(0).stopMovingInDirection(Direction.EAST);
                break;
            case LEFT:
                arena.getPlayer(0).stopMovingInDirection(Direction.WEST);
                break;
            case DOWN:
                arena.getPlayer(0).stopMovingInDirection(Direction.SOUTH);
                break;
            case UP:
                arena.getPlayer(0).stopMovingInDirection(Direction.NORTH);
                break;
        }
        if (arena.getNumberOfPlayers() > 1){
            switch(e.getKeyCode()){
                case W:
                    arena.getPlayer(1).stopMovingInDirection(Direction.NORTH);
                    break;
                case D:
                    arena.getPlayer(1).stopMovingInDirection(Direction.EAST);
                    break;
                case S:
                    arena.getPlayer(1).stopMovingInDirection(Direction.SOUTH);
                    break;
                case A:
                    arena.getPlayer(1).stopMovingInDirection(Direction.WEST);
                    break;
            }
        }
    }
}
