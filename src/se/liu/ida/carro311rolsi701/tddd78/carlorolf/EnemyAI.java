package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.friendlycharacters.Player;

import java.util.ArrayList;
import java.util.List;

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
        nextPoint = new Vector(target.getCoords());
    }

    public void move(double movementSpeed){
        if (target != null && coords.getDistance(nextPoint) > character.getWidth() / 2) {
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
            double dX = getX() - target.getX();
            double dY = getY() - target.getY();
            double wAbs = character.getWidth() / 2 + weapon.getRange() / 2;
            double k = wAbs / coords.getDistance(target.getCoords());
            double wX = getX() - k * dX;
            double wY = getY() - k * dY;

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
}
