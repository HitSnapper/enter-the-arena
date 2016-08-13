package se.liu.ida.carro311rolsi701.tddd78.carlorolf.obstacles;

import se.liu.ida.carro311rolsi701.tddd78.carlorolf.*;

/**
 * A tree which the player can chop down.
 */
public class Tree extends ArenaObject {

    public Tree(final double x, final double y, final double diameter, CollisionHandler collisionHandler, Arena arena) {
        super(new Body(new Vector(x, y), ShapeMaker.getHexagon(diameter/2), false), 10, 500, Images.getImage("leaves"), 3.95, collisionHandler, arena);
    }

    @Override
    protected void move(final double movementSpeed) {

    }

    @Override
    public void death(){

    }

    @Override
    protected void updateImage() {
        if (hp / (double)maximumHp > 0.8){
            image = Images.getImage("leaves");
        }
        else if (hp / (double)maximumHp > 0.6){
            image = Images.getImage("leaves_damaged0");
        }
        else if(hp / (double)maximumHp > 0.4){
            image = Images.getImage("leaves_damaged1");
        }
        else if(hp / (double)maximumHp > 0.2){
            image = Images.getImage("leaves_damaged2");
        }
        else if(hp / (double)maximumHp > 0){
            image = Images.getImage("leaves_damaged3");
        }
        else{
            setImageProportionalSize(1);
            image = Images.getImage("tree");
        }
    }
}
