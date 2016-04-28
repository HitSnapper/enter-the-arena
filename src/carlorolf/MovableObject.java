package carlorolf;


/**
 * The players children wich you can hit and gain its health
 */
public class MovableObject extends ArenaObject {
    final static double SIZE = 0.7;

    public MovableObject(final double x, final double y, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, SIZE, SIZE, 1, 100, ShapeEnum.RECTANGLE, true, Images.getImage("object_none.png"), collisionHandler, arena);
    }

    @Override
    protected void move(final double movementSpeed) {
        Player unTarget = arena.getPlayer();
        if (coords.getDistance(unTarget.getCoords()) < 4) {
            if (coords.getDistance(unTarget.coords) > width / 2) {
                double pX = unTarget.getX() - x;
                double pY = unTarget.getY() - y;
                double absP = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
                double k = absP / movementSpeed;
                double dX = pX / k;
                double dY = pY / k;
                x -= dX;
                y -= dY;
            }
        }
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