package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

import java.awt.*;

/**
 * Created by HitSnapper on 2016-07-03.
 */
public class EnemyAI {
    private Character character;
    private CollisionHandler collisionHandler;
    private Arena arena;
    private Vector nextPoint;
    private ArenaObject target;
    private Vector coords;
    private Weapon weapon;
    private Path path;

    public EnemyAI(Character character, CollisionHandler collisionHandler, Arena arena) {
        this.character = character;
        coords = character.getCoords();
        this.collisionHandler = collisionHandler;
        this.arena = arena;
        this.weapon = character.getWeapon();
    }

    private double getX(){
        return coords.getX();
    }
    private double getY(){
        return coords.getY();
    }

    private boolean targetInReach(){
        return coords.getDistance(target.getCoords()) - target.getWidth()/2 - character.getWidth() / 2 < weapon.getRange() && !target.isDead();
    }

    public void findPathToTarget() {
        path = collisionHandler.getPath(coords, target.getCoords());
        if (!path.isEmpty()) {
            nextPoint = path.getFirst();
        }
        else{
            nextPoint = coords;
        }
    }

    public void move(double movementSpeed){
        if (target != null && nextPoint != null && coords.getDistance(nextPoint) > character.getWidth() / 2) {
            double pX = nextPoint.getX() - coords.getX();
            double pY = nextPoint.getY() - coords.getY();
            double absP = Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2));
            double k = absP / movementSpeed;
            double dX = pX / k;
            double dY = pY / k;
            coords.add(dX, dY);
        }
    }

    private void updateTarget(){
        ArenaObject temp = arena.getPlayer(0);
        for (Player player : arena.getPlayers()) {
            if (character.getCoords().getDistance(temp.getCoords()) > character.getCoords().getDistance(player.getCoords()) && !player.isDead()){
                temp = player;
            }
            else if (temp.isDead()){
                temp = player;
            }
        }

        //Perhaps chase healers if player is dead?
        target = temp;
    }

    private void hit() {
        if (character.canAttack && weapon != null) {
            character.startAttackDelay();
            double dX = target.getX() - getX();
            double dY = target.getY() - getY();
            double wAbs = character.getWidth() / 2 + weapon.getRange() / 2;
            double k = wAbs / coords.getDistance(target.getCoords());
            double wX = getX() + k * dX;
            double wY = getY() + k * dY;

            weapon.setHittingDirection(character.movingDirection, wX, wY);
            collisionHandler.addWeapon(weapon);
        }
    }

    public void update() {
        updateTarget();
        findPathToTarget();
        if (targetInReach()){
            hit();
        }
    }

    public void drawPath(Graphics2D screen, Vector target, Dimension tileSize, double screenWidth, double screenHeight){
        if (path != null){
            path.draw(screen, target, tileSize, screenWidth, screenHeight, arena);
        }
    }
}
