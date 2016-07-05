package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

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

    public EnemyAI(Character character, CollisionHandler collisionHandler, Arena arena) {
        this.character = character;
        this.collisionHandler = collisionHandler;
        this.arena = arena;
    }

    public void findPathToTarget() {
        Path path = new Path();
        path.add(character.getCoords());
        while (path.size() > 0 && path.getLast() != target.getCoords()) {
            List<ArenaObject> objectsBetween = collisionHandler.objectsBetween(character, target);

        }
    }

    public void update() {
        findPathToTarget();
    }
}
