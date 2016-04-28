package carlorolf;


/**
 * The players children wich you can hit and gain its health
 */
public class MovableObject extends ArenaObject {
    final static double SIZE = 0.7;

    public MovableObject(final double x, final double y, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, SIZE, SIZE, 10, 100, ShapeEnum.RECTANGLE, true, Images.getImage("object_none.png"), collisionHandler, arena);
        //Assings a unique armor for MovableObject
        //noinspection AssignmentToSuperclassField
        armor = new Armor(100, this, arena, Images.getImage("helmet.png"));
    }

    @Override
    protected void move(final double movementSpeed) {

    }
    @Override public void weaponCollision(Weapon weapon) {
        super.weaponCollision(weapon);
        if (this.hp > 0 && armor.getToughness() > 0) {
                 arena.getPlayer().addHealth((int) (((double) (armor.getMaxToughness() - armor.getToughness()) / armor.getMaxToughness()) * arena.getPlayer().getWeapon().getDamage()));
             } else {
                 arena.getPlayer().addHealth( arena.getPlayer().getWeapon().getDamage());
             }

    }

    @Override
    protected void updateImage() {

    }
}