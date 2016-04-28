package carlorolf;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class ArenaObject extends VisibleObject {
    protected Vector coords;
    private boolean movable;
    private Shape shape;
    protected Direction movingDirection;
    private double movementSpeed;
    private Vector recoil;
    protected CollisionHandler collisionHandler;
    private Vector oldCoords;
    protected int hp;
    protected int maximumHp;
    protected List<VisibleObject> layers;
    private boolean dead;
    protected Armor armor;

    public ArenaObject(double x, double y, double width, double height, double movementSpeed, int hp, ShapeEnum shapeEnum,
                       boolean movable, Image image, CollisionHandler collisionHandler, Arena arena) {
        super(x, y, width, height, image, arena);
        dead = false;
        coords = new Vector(x, y);
        this.hp = hp;
        maximumHp = hp;
        this.movingDirection = Direction.NONE;
        this.collisionHandler = collisionHandler;
        this.movable = movable;
        this.movementSpeed = movementSpeed;
        recoil = new Vector(0, 0);
        if (shapeEnum == ShapeEnum.RECTANGLE) this.shape = new Shape(width, height);
        else if (shapeEnum == ShapeEnum.CIRCLE) this.shape = new Shape(width / 2);
        oldCoords = new Vector(x, y);
        layers = new ArrayList<>();
        armor = new Armor(0, this, arena, image);
    }

    public Shape getShape() {
        return shape;
    }


    public boolean isMovable() {
        return movable;
    }

    public abstract void collision(CollisionEvent e);

    public void weaponCollision(Weapon weapon) {
        if (isMovable()) {
            double temp = weapon.getDamage();
            addRecoil(weapon.getHittingDirection().getVector().times(temp));
            Random rand = new Random();
            double randomWidth = width / (rand.nextInt(3) + 1.5);
            double randomHeight = height / (rand.nextInt(3) + 1.5);

            arena.addLayer(new VisibleObject(x, y, randomWidth, randomHeight, Images.getImage("blood_superlowopacity.png"), arena) {
                @Override
                public void update(double deltaTime) {

                }
            });
        }
        if (this.hp > 0 && armor.getToughness() > 0) {
            this.hp -= (int) (((double) (armor.getMaxToughness() - armor.getToughness()) / armor.getMaxToughness()) * weapon.getDamage());
            armor.damage(weapon.getDamage());
        } else {
            this.hp -= weapon.getDamage();
        }
    }

    private void addRecoil(Vector v) {
        Vector vector = new Vector(v.getX() + recoil.getX(), v.getY() + recoil.getY());
        if (vector.getAbs() < 50) {
            recoil.add(v);
        }
        System.out.println(recoil.getAbs());
    }

    private void applyRecoil(double deltaTime) {
        x += recoil.getX() * deltaTime;
        y += recoil.getY() * deltaTime;
        reduceRecoil();
    }

    private void reduceRecoil() {
        recoil.scale(0.94);
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
        arena.addLayer(new VisibleObject(x, y, width * 2, height * 2, Images.getImage("blood_superlowopacity.png"), arena) {
            @Override
            public void update(double deltaTime) {

            }
        });
        arena.removeObject(this);

        collisionHandler.removeObject(this);
        arena.removeObject(this);
        dead = true;

        if (this instanceof DragonBoss) {
            arena.getPlayer().addHealth(80);
            arena.getPlayer().getArmor().repairArmor(80);
        } else if (this instanceof StandardEnemy) {
            arena.getPlayer().getArmor().repairArmor(15);
            arena.getPlayer().addHealth(7);
        }
    }

    public Armor getArmor() {
        return armor;
    }

    @Override
    public void update(double deltaTime) {
        if (!dead) {
            if (hp <= 0) {
                death();
            }
            oldCoords.set(x, y);
            move(movementSpeed * deltaTime);
            applyRecoil(deltaTime);
            coords.set(x, y);
            // Checking the distance moved so that objects doesn't move to far so they'll jump over other objects.
            assert oldCoords.getDistance(coords) < 0.5;
            for (VisibleObject layer : layers) {
                layer.update(deltaTime);
            }
            updateDirection();
            updateImage();
        }
        armor.update(deltaTime);
    }

    @Override
    public void draw(Graphics screen, Dimension tileSize) {
        super.draw(screen, tileSize);

        int xPos = (int) (tileSize.getWidth() * ((this.getX() - width / 2) - arena.getPlayer().getX() + (arena.getWidth() + 2) / 2));
        int yPos = (int) (tileSize.getHeight() * ((this.getY() - height / 2) - arena.getPlayer().getY() + (arena.getHeight() + 0.5) / 2));

        // Drawing health bar
        if (hp != maximumHp && isMovable() && hp > 0) {
            screen.setColor(new Color((int) (255 * (maximumHp - hp) / (double) maximumHp), 255 * hp / maximumHp, 0));
            screen.fillRect(xPos, yPos - 10, (int) ((width * hp / maximumHp) * tileSize.getWidth()), 5);

            screen.setColor(Color.BLACK);
            screen.drawRect(xPos, yPos - 10, (int) (width * tileSize.getWidth()), 5);
        }

        // Drawing armor
        if (armor.getToughness() > 0) {
            screen.setColor(Color.BLUE);
            screen.fillRect(xPos, yPos - 18, (int) ((width * armor.getToughness() / armor.getMaxToughness()) * tileSize.getWidth()), 5);

            screen.setColor(Color.BLACK);
            screen.drawRect(xPos, yPos - 18, (int) (width * tileSize.getWidth()), 5);

            armor.draw(screen, tileSize);
        }
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public Vector getCoords() {
        return coords;
    }
}
