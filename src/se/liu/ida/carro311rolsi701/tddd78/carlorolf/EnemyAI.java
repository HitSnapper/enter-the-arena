package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

import java.awt.*;
import java.util.Random;

/**
 * Created by HitSnapper on 2016-07-03.
 */
public class EnemyAI {
    private Character character;
    private CollisionHandler collisionHandler;
    private Arena arena;
    private Vector nextPoint;
    private ArenaObject target;
    private Vector targetOldCoords;
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
        if (targetOldCoords == null){
            targetOldCoords = new Vector(target.getX(), target.getY());
        }
        if (nextPoint == null){
            nextPoint = target.getCoords();
        }
        if (targetOldCoords.getDistance(target.getCoords()) > 1) {
            targetOldCoords = new Vector(target.getX(), target.getY());
            Path p = collisionHandler.getPath(coords, target.getCoords());
            if (!p.isEmpty()) {
                path = p;
                nextPoint = path.getLast();
                System.out.println("No Path" + new Random().nextInt(5));
            }
            else{
                nextPoint = path.getLast();
            }
        }
        if (nextPoint.getDistance(coords) < 0.2){
            nextPoint = target.getCoords();
            if (!path.isEmpty()) {
                path.removeLast();
            }
            //nextPoint = path.getLast();
        }
    }

    public void move(double movementSpeed){
        if (target != null && nextPoint != null){
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
        if (path != null && !path.isEmpty()){
            int numberOfPlayers = arena.getNumberOfAlivePlayers();
            if (numberOfPlayers == 0) {
                numberOfPlayers = 1;
            }
            screen.setColor(Color.green);

            int startX = (int)(tileSize.getWidth() * (getX() - target.getX()) + screenWidth / numberOfPlayers);
            int startY = (int)(tileSize.getHeight() * (getY() - target.getY()) + screenHeight);
            int endX = (int)(tileSize.getWidth() * (path.getLast().getX() - target.getX()) + screenWidth / numberOfPlayers);
            int endY = (int)(tileSize.getHeight() * (path.getLast().getY() - target.getY()) + screenHeight);
            screen.drawLine(startX, startY, endX, endY);
            path.draw(screen, target, tileSize, screenWidth, screenHeight, arena);
        }
    }
}
