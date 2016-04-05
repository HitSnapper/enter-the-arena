package carlorolf;

public enum GameState
{
    MENU(), INGAME();
    private SubState subState;

    GameState() {
	this.subState = SubState.NONE;
    }

    public SubState getSubState() {
	return subState;
    }

    public void setSubState(final SubState subState) {
	this.subState = subState;
    }
}