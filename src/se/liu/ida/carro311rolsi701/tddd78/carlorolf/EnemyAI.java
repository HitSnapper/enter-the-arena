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

    public EnemyAI(Character character, CollisionHandler collisionHandler, Arena arena) {
        this.character = character;
        coords = character.getCoords();
        this.collisionHandler = collisionHandler;
        this.arena = arena;
    }

    public void findPathToTarget() {
        /*
        Path path = new Path();
        path.add(character.getCoords());
        while (path.size() > 0 && path.getLast() != target.getCoords()) {
            List<ArenaObject> objectsBetween = collisionHandler.objectsBetween(character, target);

        }
        */
        System.out.println(collisionHandler.objectsBetween(character, target));
        if (collisionHandler.objectsBetween(character, target).size() == 0){
            nextPoint = new Vector(target.getCoords());
        }
        else{
            nextPoint = new Vector(character.getCoords());
        }
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

    public void update() {
        updateTarget();
        findPathToTarget();
    }
}
