package carlorolf;

/**
 * The standard enemy for the player with the only goal to kill him.
 */
public class StandardEnemy extends Enemy {

    final static int MOVEMENTSPEED = 3;
    final static int ATTACKSPEED = 1;
    final static int HEALTH = 50;

    public StandardEnemy(final double x, final double y, final CollisionHandler collisionHandler, final Arena arena) {
        super(x, y, 1, 1, MOVEMENTSPEED, HEALTH, ATTACKSPEED, Images.getImage("enemy_none.png"), collisionHandler, arena);
        final double weaponAttackSpeed = 0.5;
        final int armorToughness = 20;
        //Assigning a unique weapon for StandardEnemy
        //noinspection AssignmentToSuperclassField
        weapon = new Weapon(x, y, 7, 2 * width / 6, weaponAttackSpeed, this);
        //Assigning a unique armor for StandardEnemy
        //noinspection AssignmentToSuperclassField
        armor = new Armor(armorToughness, this, arena, Images.getImage("helmet.png"));
    }

    @Override
    protected void updateImage() {
        image = Images.getImage("enemy_" + Direction.toString(movingDirection) + ".png");
    }

}
