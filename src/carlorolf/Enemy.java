package carlorolf;

public class Enemy extends Character {
    private ArenaObject target;

    public Enemy(final double x, final double y, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, 1, 1, 3, 100, ShapeEnum.RECTANGLE, true, Images.getImage("enemy_none.png"), collisionHandler, arena);
        this.target = arena.getPlayer();
        weapon = new Weapon(x, y, 10, 2 * width / 6, 0.5, this);
	armor = new Armor(20, this, arena, Images.getImage("helmet.png"));
    }

    @Override
    protected void move(double movementSpeed) {
        if (coords.getDistance(target.coords) > width / 2) {
            double pX = target.getX() - x;
            double pY = target.getY() - y;
            double absP = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
            double k = absP / movementSpeed;
            double dX = pX / k;
            double dY = pY / k;
            x += dX;
            y += dY;
        }
    }

    @Override
    protected void updateImage() {
        image = Images.getImage("enemy_" + Direction.toString(movingDirection) + ".png");
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (coords.getDistance(target.coords) - target.getWidth() - width / 2 < weapon.getRange()) {
            hit();
        }

        // Perhaps add an ArenaObject as a main target?
    }

    @Override
    protected void hit() {
        if (canAttack && weapon != null) {
            double dX = x - target.getX();
            double dY = y - target.getY();
            double wAbs = width / 2 + weapon.getRange() / 2;
            double k = wAbs / coords.getDistance(target.getCoords());
            double wX = x - k * dX;
            double wY = y - k * dY;

            weapon.setHittingDirection(movingDirection, wX, wY);
            collisionHandler.addWeapon(weapon);
        }
    }

    @Override
    public void collision(final CollisionEvent e) {
    }
}
