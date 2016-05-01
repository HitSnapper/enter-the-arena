package se.liu.ida.carro311rolsi701.tddd78.carlorolf;


/**
 * GameState indicates what phase and sub-phases the game is currently in, for example "MENU" or "INGAME"
 */
public class GameState
{
    private Phase phase;
    private State state;

    public GameState() {
	state = State.NONE;
	phase = Phase.MENU;
    }

    State getState() {
	return state;
    }

    public void setState(final State state) {
	this.state = state;
    }

    Phase getPhase() {
	return phase;
    }

    public void setPhase(final Phase phase) {
	this.phase = phase;
    }
}
