package carlorolf;


/**
 *
 */
public class GameState {
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
