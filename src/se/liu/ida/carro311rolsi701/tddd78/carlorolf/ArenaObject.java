package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * All objects in the arena
 */
public abstract class ArenaObject extends VisibleObject {
    private final static Logger LOGGER = Logger.getLogger(ArenaObject.class.getName());
    private boolean movable;
    private double movementSpeed;
    private Vector recoil;
    protected CollisionHandler collisionHandler;
    protected Vector oldCoords;
    protected int hp;
    protected int maximumHp;
    protected List<VisibleObject> layers;
    protected boolean dead;
    protected Armor armor;
    protected Body body;

    protected ArenaObject(Body body, double movementSpeed, int hp, boolean movable,
                          Image image, CollisionHandler collisionHandler, Arena arena) {
        super(body.getCoords(), body.getWidth(), body.getHeight(), image, arena);
        this.body = body;

        dead = false;
        this.hp = hp;
        maximumHp = hp;
        this.collisionHandler = collisionHandler;
        this.movable = movable;
        this.movementSpeed = movementSpeed;
        recoil = new Vector(0, 0);
        oldCoords = new Vector(coords);
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
            final double randomWidth = getWidth() / (rand.nextInt(3) + 1.5);
            final double randomHeight = getHeight() / (rand.nextInt(3) + 1.5);

            arena.addBackgroundLayer(new Blood(getX(), getY(), randomWidth, randomHeight, arena));
        }
        if (this.hp > 0 && armor.getToughness() > 0) {
            this.hp -= (int) (((double) (armor.getMaxToughness() - armor.getToughness()) / armor.getMaxToughness()) *
                    weapon.getDamage());
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
        body.addX(recoil.getX() * deltaTime);
        body.addY(recoil.getY() * deltaTime);
        reduceRecoil(deltaTime);
    }

    private void reduceRecoil(double deltaTime) {
        final double reduceRecoil = Math.pow(0.001, deltaTime);
        recoil.scale(reduceRecoil);
    }

    protected abstract void move(double movementSpeed);

    protected abstract void updateImage();

    public boolean isDead() {
        return dead;
    }

    public void death() {
        arena.addBackgroundLayer(new Blood(getX(), getY(), getWidth() * 2, getHeight() * 2, arena));
        collisionHandler.removeObject(this);
        arena.removeObject(this);
        dead = true;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (getX() < -1 || getX() > arena.getWidth() * 2 || getY() < -1 || getY() > arena.getHeight() * 2) {
            //LOGGER.log(Level.SEVERE, "Object outside of arena. Coordinates: {0} , Object: " + this, coords);
        }
        if (!dead) {
            if (hp <= 0) {
                death();
            }
            oldCoords.set(getX(), getY());
            move(movementSpeed * deltaTime);
            applyRecoil(deltaTime);
            coords.set(getX(), getY());
            for (VisibleObject layer : layers) {
                layer.update(deltaTime);
            }
            updateImage();
        }
        armor.update(deltaTime);
    }

    @Override
    public void draw(Graphics2D screen, Vector target, Dimension tileSize, int screenWidth, int screenHeight) {
        super.draw(screen, target, tileSize, screenWidth, screenHeight);

        int numberOfPlayers = arena.getNumberOfAlivePlayers();
        if (numberOfPlayers == 0) {
            numberOfPlayers = 1;
        }
        int xPos = (int) (tileSize.getWidth() * (getX() - getWidth() / 2 - target.getX()) + screenWidth / numberOfPlayers);
        int yPos = (int) (tileSize.getHeight() * (getY() - getHeight() / 2 - target.getY()) + screenHeight);

        // Drawing health bar
        if (hp != maximumHp && movable && hp > 0) {
            final int maxColorValue = 250;
            screen.setColor(
                    new Color((int) (maxColorValue * (maximumHp - hp) / (double) maximumHp), maxColorValue * hp / maximumHp, 0));
            screen.fillRect(xPos, yPos - 10, (int) ((getWidth() * hp / maximumHp) * tileSize.getWidth()), 5);

            screen.setColor(Color.BLACK);
            screen.drawRect(xPos, yPos - 10, (int) (getWidth() * tileSize.getWidth()), 5);
        }

        // Drawing armor
        if (armor.getToughness() > 0) {
            final int armorOffset = 18;
            screen.setColor(Color.BLUE);
            screen.fillRect(xPos, yPos - armorOffset,
                    (int) ((getWidth() * armor.getToughness() / armor.getMaxToughness()) * tileSize.getWidth()), 5);

            screen.setColor(Color.BLACK);
            screen.drawRect(xPos, yPos - armorOffset, (int) (getWidth() * tileSize.getWidth()), 5);

            armor.draw(screen, target, tileSize, screenWidth, screenHeight);
        }
    }

    public Body getBody() {
        return body;
    }

    public Vector getCoords() {
        return coords;
    }

    public Armor getArmor() {
        return armor;
    }

    public double getX() {
        return body.getX();
    }

    public double getY() {
        return body.getY();
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public void addX(double add) {
        body.addX(add);
    }

    public void addY(double add) {
        body.addY(add);
    }

    public void addCoords(Vector vector){
        body.addCoords(vector);
    }
}