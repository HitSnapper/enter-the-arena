package carlorolf;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ArenaObject extends VisibleObject {
    protected Vector coords;
    private boolean movable;
    private Shape shape;
    protected Direction movingDirection;
    private double movementSpeed;
    private Vector recoil;
    protected CollisionHandler collisionHandler;
    private Vector oldCoords;
    private int hp;
    private int maximumHp;
    protected int armor;
    protected int maximumArmor;
    private Arena arena;
    protected List<VisibleObject> layers;
    private boolean dead;

    public ArenaObject(double x, double y, double width, double height, double movementSpeed, int hp, ShapeEnum shapeEnum, boolean movable,
                       Image image, Image armor_pic, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, width, height, image, armor_pic);
        dead = false;
        coords = new Vector(x, y);
        this.arena = arena;
        this.hp = hp;
        maximumHp = hp;
        armor = 0;
        maximumArmor = 0;
        this.movingDirection = Direction.NONE;
        this.collisionHandler = collisionHandler;
        this.movable = movable;
        this.movementSpeed = movementSpeed;
        recoil = new Vector(0, 0);
        if (shapeEnum == ShapeEnum.RECTANGLE) this.shape = new Shape(width, height);
        else if (shapeEnum == ShapeEnum.CIRCLE) this.shape = new Shape(width / 2);
        oldCoords = new Vector(x, y);
        layers = new ArrayList<>();
    }

    public Shape getShape() {
        return shape;
    }

    public boolean isMovable() {
        return movable;
    }

    public abstract void Collision(CollisionEvent e);

    public void weaponCollision(Weapon weapon) {
        if (isMovable()) {
            double temp = weapon.getDamage();
            switch (weapon.getHittingDirection()) {
                case NORTH:
                    addRecoil(new Vector(0, -temp));
                    break;
                case NORTHEAST:
                    addRecoil(new Vector(temp, -temp));
                    break;
                case EAST:
                    addRecoil(new Vector(temp, 0));
                    break;
                case SOUTHEAST:
                    addRecoil(new Vector(temp, temp));
                    break;
                case SOUTH:
                    addRecoil(new Vector(0, temp));
                    break;
                case SOUTHWEST:
                    addRecoil(new Vector(-temp, temp));
                    break;
                case WEST:
                    addRecoil(new Vector(-temp, 0));
                    break;
                case NORTHWEST:
                    addRecoil(new Vector(-temp, -temp));
                    break;
            }
        }
        if (armor <= 0)
                armor_pic = null;
        if (this.hp > 0 && maximumArmor != 0)
            this.hp -= ((double)(maximumArmor - armor) / (double)maximumArmor) * weapon.getDamage();
        else {
            this.hp -= weapon.getDamage();
        }
        armor -= weapon.getDamage();
    }

    private void addRecoil(Vector v) {
        recoil.add(v);
    }

    private void applyRecoil() {
        x += recoil.getX() * DeltaTime.getDt();
        y += recoil.getY() * DeltaTime.getDt();
        reduceRecoil();
    }

    private void reduceRecoil() {
        recoil.scale(0.9);
    }

    protected abstract void move(double movementSpeed);

    protected abstract void updateImage();

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ArenaObject)) {
            return false;
        }

        ArenaObject that = (ArenaObject) other;

        // Custom equality check here.
        return this.getX() == that.getX() && this.getY() == that.getY() &&
                this.getWidth() == that.getWidth() && this.getHeight() == that.getHeight() &&
                this.getShape() == that.getShape();
    }

    private void updateDirection() {
        if (!(this instanceof Player)) {
            double dX = x - oldCoords.getX();
            double dY = y - oldCoords.getY();

            double angle = dY / dX;
            double temp = 0.5;

            // Calculating what direction the enemy is looking in
            if (Math.abs(dY / dX) < temp || Math.abs(dX / dY) < temp) {
                if (Math.abs(dX) > Math.abs(dY) && dX > 0) {
                    movingDirection = Direction.EAST;
                } else if (Math.abs(dX) < Math.abs(dY) && dY > 0) {
                    movingDirection = Direction.SOUTH;
                } else if (Math.abs(dX) > Math.abs(dY) && dX < 0) {
                    movingDirection = Direction.WEST;
                } else if (Math.abs(dX) < Math.abs(dY) && dY < 0) {
                    movingDirection = Direction.NORTH;
                }
            } else {
                if (angle > 0 && dX > 0) {
                    movingDirection = Direction.SOUTHEAST;
                } else if (angle < 0 && dX > 0) {
                    movingDirection = Direction.NORTHEAST;
                } else if (angle > 0 && dX < 0) {
                    movingDirection = Direction.NORTHWEST;
                } else if (angle < 0 && dX < 0) {
                    movingDirection = Direction.SOUTHWEST;
                }
            }
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void death() {
        arena.addLayer(new VisibleObject(x, y, width * 2, height * 2, Images.getImage("blood_superlowopacity.png"), null) {
            @Override
            public void update() {

            }
        });

        collisionHandler.removeObject(this);
        arena.removeObject(this);
        dead = true;
    }

    @Override
    public void update() {
        if (!dead) {
            if (hp <= 0) {
                death();
            }
            oldCoords.set(x, y);
            move(movementSpeed * DeltaTime.getDt());
            applyRecoil();
            coords.set(x, y);
            layers.forEach(VisibleObject::update);
            updateDirection();
            updateImage();
        }
    }

    @Override
    public void draw(Graphics screen, Dimension tileSize) {
        super.draw(screen, tileSize);
        for (VisibleObject layer : layers) {
            layer.draw(screen, tileSize);
        }

        // Drawing health bar
        if (hp != maximumHp && isMovable() && hp > 0) {
            screen.setColor(new Color((int) (255 * (maximumHp - hp) / (double) maximumHp), 255 * hp / maximumHp, 0));
            screen.fillRect((int) ((x - width / 2) * tileSize.getWidth()), (int) ((y - height / 2) * tileSize.getHeight() - 10),
                    (int) ((width * hp / maximumHp) * tileSize.getWidth()), 5);

            screen.setColor(Color.BLACK);
            screen.drawRect((int) ((x - width / 2) * tileSize.getWidth()), (int) ((y - height / 2) * tileSize.getHeight() - 10),
                    (int) (width * tileSize.getWidth()), 5);
        }

        // Drawing armor
        if (armor > 0) {
            screen.setColor(Color.BLUE);
            screen.fillRect((int) ((x - width / 2) * tileSize.getWidth()), (int) ((y - height / 2) * tileSize.getHeight() - 18),
                    (int) ((width * armor / maximumArmor) * tileSize.getWidth()), 5);

            screen.setColor(Color.BLACK);
            screen.drawRect((int) ((x - width / 2) * tileSize.getWidth()), (int) ((y - height / 2) * tileSize.getHeight() - 18),
                    (int) (width * tileSize.getWidth()), 5);
        }
    }

    public Vector getCoords() {
        return coords;
    }
}
