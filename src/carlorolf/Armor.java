package carlorolf;

public class Armor extends VisibleObject
{
    private int toughness;
    private int maxToughness;
    private ArenaObject owner;

    public Armor(final int toughness, ArenaObject owner, Arena arena) {
	super(owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight()*0.3, Images.getImage("helmet.png"), arena);
	this.toughness = toughness;
	this.maxToughness = toughness;
	this.owner = owner;
    }


    @Override public void update(final double deltaTime) {
	x = owner.getX();
	y = owner.getY();
    }

    public int getToughness() {
	return toughness;
    }

    public int getMaxToughness() {
	return maxToughness;
    }

    public void damage(int damage){
	toughness -= damage;
    }
}
