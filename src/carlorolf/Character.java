package carlorolf;

import java.awt.*;

public abstract class Character extends ArenaObject
{
    protected Weapon weapon;
    protected int attackSpeed;
    protected double attackTimer;
    protected boolean canAttack;
    public Character(final double x, final double y, final double width, final double height, final double movementSpeed,
		     final int hp, final ShapeEnum shapeEnum, final boolean movable, final Image image,
		     final CollisionHandler collisionHandler, final Arena arena)
    {
	super(x, y, width, height, movementSpeed, hp, shapeEnum, movable, image, collisionHandler, arena);
	weapon = new Weapon(0, 0, 0, 0, 0, this);
	attackSpeed = 1;
	canAttack = true;
	attackTimer = 0;
    }

    @Override public void Collision(final CollisionEvent e) {

    }

    @Override protected void move(final double movementSpeed) {

    }

    @Override protected void updateImage() {

    }

    public boolean canAttack() {
	return canAttack;
    }

    public void startAttackDelay(){
	attackTimer = attackSpeed * weapon.getAttackSpeed();
	canAttack = false;
    }

    @Override public void update(double deltaTime){
	super.update(deltaTime);
	armor.update(deltaTime);
	if (attackTimer > 0){
	    attackTimer -= deltaTime;
	}
	else if (!canAttack){
	    canAttack = true;
	}

    }

    protected abstract void hit();
}
