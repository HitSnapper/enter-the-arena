package se.liu.ida.carro311rolsi701.tddd78.carlorolf.enemies;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.*;
import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

/**
 * The standard enemy for the player with the only goal to kill him.
 */
public class StandardEnemy extends Enemy {

    //These are static so it can be accessed in the super constructor
    private final static int MOVEMENTSPEED = 3;
    private final static int ATTACKSPEED = 1;
    private final static int HEALTH = 50;

    public StandardEnemy(final double x, final double y, final CollisionHandler collisionHandler, final Arena arena) {
        super(x, y, 1.5, MOVEMENTSPEED, HEALTH, ATTACKSPEED, "enemy", collisionHandler, arena);
        final double weaponAttackSpeed = 0.5;
        final int armorToughness = 20;
        //Assigning a unique weapon for StandardEnemy
        //noinspection AssignmentToSuperclassField
        weapon = new Weapon(x, y, 7, 2 * width / 6, weaponAttackSpeed, this);
        //Assigning a unique armor for StandardEnemy
        //noinspection AssignmentToSuperclassField
        armor = new Armor(armorToughness, this, arena, Images.getImage("helmet"));
    }

    @Override
    public void death() {
        super.death();
        final int repair = 15;
        final int heal = 7;
        for (Player player : arena.getAlivePlayers()) {
            player.getArmor().repairArmor(repair);
            player.heal(heal);
        }
    }
}
