package carlorolf;

import carlorolf.friendlycharacters.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * All objects in the arena
 */
public abstract class ArenaObject extends VisibleObject {
    private final static Logger LOGGER = Logger.getLogger(ArenaObject.class.getName());
    protected Vector coords;
    private boolean movable;
    private Shape shape;
    protected Direction movingDirection;
    private double movementSpeed;
    private Vector recoil;
    protected CollisionHandler collisionHandler;
    protected Vector oldCoords;
    protected int hp;
    protected int maximumHp;
    protected List<VisibleObject> layers;
    private boolean dead;
    protected Armor armor;

    protected ArenaObject(double x, double y, double width, double height, double movementSpeed, int hp,
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
        this.shape = new Shape(width, height);
        oldCoords = new Vector(x, y);
        layers = new ArrayList<>();
        armor = new Armor(0, this, arena, image);
    }


    public boolean isMovable() {
        return movable;
    }


    public void weaponCollision(Weapon weapon) {
        if (movable) {
            double temp = weapon.getDamage();
            addRecoil(weapon.getHittingDirection().getVector().times(temp));
            Random rand = new Random();
            final double randomWidth = width / (rand.nextInt(3) + 1.5);
            final double randomHeight = height / (rand.nextInt(3) + 1.5);

            arena.addBackgroundLayer(new VisibleObject(x, y, randomWidth, randomHeight, Images.getImage("blood"), arena) {
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
        final int maximumMovingDistance = 50;
        if (vector.getAbs() < maximumMovingDistance) {
            recoil.add(v);
        }
    }

    private void applyRecoil(double deltaTime) {
        x += recoil.getX() * deltaTime;
        y += recoil.getY() * deltaTime;
        reduceRecoil(deltaTime);
    }

    private void reduceRecoil(double deltaTime) {
        final double reduceRecoil = Math.pow(0.001, deltaTime);
        recoil.scale(reduceRecoil);
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
        // Checking that coords and size is exactly the same
        // noinspection FloatingPointEquality
        return this.getX() == that.getX() && this.getY() == that.getY() &&
                this.getWidth() == that.getWidth() && this.getHeight() == that.getHeight() &&
                shape == that.shape;
    }

    public boolean isDead() {
        return dead;
    }

    public void death() {
        arena.addBackgroundLayer(new VisibleObject(x, y, width * 2, height * 2, Images.getImage("blood"), arena) {
            @Override
            public void update(double deltaTime) {

            }
        });
        arena.removeObject(this);

        collisionHandler.removeObject(this);
        arena.removeObject(this);
        dead = true;
    }

    public Armor getArmor() {
        return armor;
    }

    @Override
    public void update(double deltaTime) {
        if (x < -1 || x > arena.getWidth()*2 || y < -1 || y > arena.getHeight()*2)
            LOGGER.log(Level.SEVERE, "Object outside of arena. Coordinates: {0}", coords);
        if (!dead) {
            if (hp <= 0) {
                death();
            }
            oldCoords.set(x, y);
            move(movementSpeed * deltaTime);
            applyRecoil(deltaTime);
            coords.set(x, y);
            // Checking the distance moved so that objects doesn't move to far so they'll jump over other objects.
            assert coords.getDistance(oldCoords) < 1;
            for (VisibleObject layer : layers) {
                layer.update(deltaTime);
            }
            updateImage();
        }
        armor.update(deltaTime);
    }

    @Override
    public void draw(Graphics screen, Dimension tileSize, int screenWidth, int screenHeight) {
        super.draw(screen, tileSize, screenWidth, screenHeight);

        Player p = arena.getPlayer();

        int xPos = (int) (tileSize.getWidth() * (this.getX() - width / 2 - p.getX()) + screenWidth);
        int yPos = (int) (tileSize.getHeight() * (this.getY() - height / 2 - p.getY()) + screenHeight);

        // Drawing health bar
        if (hp != maximumHp && movable && hp > 0) {
            final int maxColorValue = 250;
            screen.setColor(new Color((int) (maxColorValue * (maximumHp - hp) / (double) maximumHp), maxColorValue * hp / maximumHp, 0));
            screen.fillRect(xPos, yPos - 10, (int) ((width * hp / maximumHp) * tileSize.getWidth()), 5);

            screen.setColor(Color.BLACK);
            screen.drawRect(xPos, yPos - 10, (int) (width * tileSize.getWidth()), 5);
        }

        // Drawing armor
        if (armor.getToughness() > 0) {
            final int armorOffset = 18;
            screen.setColor(Color.BLUE);
            screen.fillRect(xPos, yPos - armorOffset, (int) ((width * armor.getToughness() / armor.getMaxToughness()) * tileSize.getWidth()), 5);

            screen.setColor(Color.BLACK);
            screen.drawRect(xPos, yPos - armorOffset, (int) (width * tileSize.getWidth()), 5);

            armor.draw(screen, tileSize, screenWidth, screenHeight);
        }
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public Vector getCoords() {
        return coords;
    }
}