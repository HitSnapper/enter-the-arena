package carlorolf;

import java.awt.*;

public abstract class Character extends ArenaObject
{
    protected Armor armor;
    protected Weapon weapon;
    public Character(final double x, final double y, final double width, final double height, final double movementSpeed,
		     final int hp, final ShapeEnum shapeEnum, final boolean movable, final Image image,
		     final CollisionHandler collisionHandler, final Arena arena)
    {
	super(x, y, width, height, movementSpeed, hp, shapeEnum, movable, image, collisionHandler, arena);
	armor = new Armor(0, this, arena);
	weapon = new Weapon(x, y, 0, 0, this);
    }

    @Override public void Collision(final CollisionEvent e) {

    }

    @Override protected void move(final double movementSpeed) {

    }

    @Override protected void updateImage() {

    }

    @Override public void update(double deltaTime){
	super.update(deltaTime);
	armor.update(deltaTime);
	weapon.update();
    }

    protected abstract void hit();
}
