package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

/**
 * The character is a movable object which can attack and move in different ways depending on it's stats.
 */
public abstract class Character extends ArenaObject {
    protected Weapon weapon;
    protected double attackSpeed;
    protected double attackTimer;
    protected boolean canAttack;
    private String imageName;
    protected Direction movingDirection;

    protected Character(Body body, final double movementSpeed,
                        final int hp, final double attackSpeed, final boolean movable, Weapon weapon, final String imageName,
                        final CollisionHandler collisionHandler, final Arena arena) {
        super(body, movementSpeed, hp, Images.getImage(imageName + "_none"), 1, collisionHandler, arena);
        this.weapon = weapon;
        weapon.setOwner(this);
        this.attackSpeed = attackSpeed;
        canAttack = true;
        attackTimer = 0;
        this.imageName = imageName;
        movingDirection = Direction.NONE;
    }

    @Override
    protected void updateImage() {
        image = Images.getImage(imageName + "_" + movingDirection.getName());
    }

    public void heal(final int hp) {
        this.hp += hp;
        if (this.hp > maximumHp) this.hp = maximumHp;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public double getAttackSpeed() {
        return attackSpeed * weapon.getAttackSpeed();
    }

    public double getAttackTimer() {
        return attackTimer;
    }

    public void startAttackDelay() {
        attackTimer = attackSpeed * weapon.getAttackSpeed();
        canAttack = false;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (attackTimer > 0) {
            attackTimer -= deltaTime;
        } else if (!canAttack) {
            canAttack = true;
        }
    }
}
