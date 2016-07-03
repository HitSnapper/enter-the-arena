package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

/**
 * Created by HitSnapper on 2016-07-03.
 */
public class AI {
    private Character character;
    private CollisionHandler collisionHandler;
    private Arena arena;

    public AI(Character character, CollisionHandler collisionHandler, Arena arena) {
        this.character = character;
        this.collisionHandler = collisionHandler;
        this.arena = arena;
    }
}
