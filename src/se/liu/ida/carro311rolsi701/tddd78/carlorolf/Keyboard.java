package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static se.liu.ida.carro311rolsi701.tddd78.carlorolf.Keys.*;

/**
 * Keyboard is added as a keyboardListener in ArenaComponent to register key presses and key releases.
 */
class Keyboard extends KeyAdapter {
    private GameState gameState;
    private ArenaComponent arenaComponent;
    private Arena arena;

    Keyboard(ArenaComponent arenaComponent) {
        this.gameState = arenaComponent.getGameState();
        this.arenaComponent = arenaComponent;
    }

    public void setArena(Arena arena){
        this.arena = arena;
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (gameState.getPhase() == Phase.INGAME) {
            if (gameState.getState() != State.PAUSEMENU) {
                for (Player player : arena.getAlivePlayers()) {
                    Controls controls = player.getControls();
                    int i = e.getKeyCode();
                    if (i == controls.getRight()) {
                        player.movePlayer(Direction.EAST);

                    } else if (i == controls.getLeft()) {
                        player.movePlayer(Direction.WEST);

                    } else if (i == controls.getDown()) {
                        player.movePlayer(Direction.SOUTH);

                    } else if (i == controls.getUp()) {
                        player.movePlayer(Direction.NORTH);

                    } else if (i == controls.getHit()) {
                        player.hit();
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
        else if (gameState.getPhase() == Phase.MENU){
            if (e.getKeyCode() == ENTER){
                if (gameState.getState() == State.NONE){
                    gameState.setState(State.PLAYMENU);
                    arenaComponent.showPlayMenu();
                }
                else if (gameState.getState() == State.PLAYMENU){
                    arenaComponent.hidePlayMenu();
                    if (arena == null) {
                        arenaComponent.initializeArena(20, 20, 1);
                    } else {
                        arena.restart(1);
                        arenaComponent.generateBackground();
                    }
                    gameState.setPhase(Phase.INGAME);
                    gameState.setState(State.NONE);
                }
            }
            if (e.getKeyCode() == ESCAPE){
                if (gameState.getState() == State.PLAYMENU){
                    arenaComponent.showStartMenu();
                }
            }
        }
        if (e.getKeyCode() == F3){
            arenaComponent.toggleDebug();
        }

        //For checking keys
        //System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (gameState.getPhase() == Phase.INGAME) {
            for (Player player : arena.getAlivePlayers()) {
                Controls controls = player.getControls();
                int i = e.getKeyCode();
                if (i == controls.getRight()) {
                    player.stopMovingInDirection(Direction.EAST);
                } else if (i == controls.getLeft()) {
                    player.stopMovingInDirection(Direction.WEST);
                } else if (i == controls.getDown()) {
                    player.stopMovingInDirection(Direction.SOUTH);
                } else if (i == controls.getUp()) {
                    player.stopMovingInDirection(Direction.NORTH);
                }
            }
        }
    }
}
