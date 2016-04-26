package carlorolf;

import java.awt.Image;

public class Armor extends VisibleObject
{
    private int toughness;
    private int maxToughness;
    private ArenaObject owner;
    private static final double HEIGHT = 1;
    private Image image;

    public Armor(final int toughness, ArenaObject owner, Arena arena, Image image) {
	super(owner.getX(), owner.getY() - owner.getHeight()/2 + HEIGHT/2*owner.getHeight(), owner.getWidth(), owner.getHeight()*HEIGHT, image, arena);
	this.toughness = toughness;
	this.maxToughness = toughness;
	this.owner = owner;
    this.image = image;
    }


    @Override public void update(final double deltaTime) {
	x = owner.getX();
	y = owner.getY() - owner.getHeight()/2 + HEIGHT/2*owner.getHeight();
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
