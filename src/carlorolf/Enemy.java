package carlorolf;

public class Enemy extends ArenaObject {
    private ArenaObject target;
    private double weaponRange;
    private int weaponDamage;

    public Enemy(final double x, final double y, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, 1, 1, 3, 200, ShapeEnum.RECTANGLE, true, Images.getImage("enemy_none.png"), collisionHandler, arena);
        this.target = arena.getPlayer();
        weaponRange = 2 * width / 6;
        weaponDamage = 10;
    }

    @Override
    protected void move(double movementSpeed) {
        if (coords.getDistance(target.coords) > width / 2) {
            double pX = target.getX() - x;
            double pY = target.getY() - y;
            double absP = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
            double konstant = absP / movementSpeed;
            double dX = pX / konstant;
            double dY = pY / konstant;
            x += dX;
            y += dY;
        }
    }

    @Override
    protected void updateImage() {
        image = Images.getImage("enemy_" + Direction.toString(movingDirection) + ".png");
    }

    @Override
    public void update() {
        super.update();
        if (coords.getDistance(target.coords) - target.getWidth() - width / 2 < weaponRange) {
            hit();
        }

        // Perhaps add an ArenaObject as a main target?
    }

    private void hit() {
        double dX = x - target.getX();
        double dY = y - target.getY();
        double wAbs = width / 2 + weaponRange / 2;
        double konstant = wAbs / coords.getDistance(target.getCoords());
        double wX = x - konstant * dX;
        double wY = y - konstant * dY;

        collisionHandler.addWeapon(new Weapon(movingDirection, wX, wY, weaponDamage, weaponRange, this));
    }

    @Override
    public void Collision(final CollisionEvent e) {
    }
}
