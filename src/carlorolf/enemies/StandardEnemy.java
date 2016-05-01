package carlorolf.enemies;

import carlorolf.Arena;
import carlorolf.Armor;
import carlorolf.CollisionHandler;
import carlorolf.Images;
import carlorolf.Weapon;

/**
 * The standard enemy for the player with the only goal to kill him.
 */
public class StandardEnemy extends Enemy {

    //These are static so it can be accessed in the super constructor
    private final static int MOVEMENTSPEED = 3;
    private final static int ATTACKSPEED = 1;
    private final static int HEALTH = 50;

    public StandardEnemy(final double x, final double y, final CollisionHandler collisionHandler, final Arena arena) {
        super(x, y, 1, 1, MOVEMENTSPEED, HEALTH, ATTACKSPEED, "enemy", collisionHandler, arena);
        final double weaponAttackSpeed = 0.5;
        final int armorToughness = 20;
        //Assigning a unique weapon for StandardEnemy
        //noinspection AssignmentToSuperclassField
        weapon = new Weapon(x, y, 7, 2 * width / 6, weaponAttackSpeed, this);
        //Assigning a unique armor for StandardEnemy
        //noinspection AssignmentToSuperclassField
        armor = new Armor(armorToughness, this, arena, Images.getImage("helmet.png"));
    }

    @Override public void death(){
        super.death();
        final int repair = 15;
        final int heal = 7;
        arena.getPlayer().getArmor().repairArmor(repair);
        arena.getPlayer().addHealth(heal);
    }
}
