package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.awt.Image;

/**
 * ArenaObjects in the arena uses their armor to lower enemies damage on its health.
 */
public class Armor extends VisibleObject {
    private int toughness;
    private int maxToughness;
    private ArenaObject owner;
    //This is static so it can be accessed in the super constructor
    private static final double HEIGHT = 1;

    public Armor(final int toughness, ArenaObject owner, Arena arena, Image image) {
        super(owner.getX(), owner.getY() - owner.getHeight() / 2 + HEIGHT / 2 * owner.getHeight(), owner.getWidth(),
                owner.getHeight() * HEIGHT, image, arena);
        this.toughness = toughness;
        this.maxToughness = toughness;
        this.owner = owner;
    }


    @Override
    public void update(final double deltaTime) {
        x = owner.getX();
        y = owner.getY() - owner.getHeight() / 2 + HEIGHT / 2 * owner.getHeight();
    }

    public int getToughness() {
        return toughness;
    }

    public int getMaxToughness() {
        return maxToughness;
    }

    public void damage(int damage) {
        toughness -= damage;
        assert (damage >= 0);
    }

    public void repairArmor(int repair) {
        toughness += repair;
        if (toughness > maxToughness) toughness = maxToughness;
    }
}
