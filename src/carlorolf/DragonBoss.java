package carlorolf;


public class DragonBoss extends Enemy
{
    private ArenaObject target;

    public DragonBoss(final double x, final double y, final CollisionHandler collisionHandler, final Arena arena)
    {
	super(x, y, 2, 2, 1, 100, Images.getImage("dragon_none.png"), collisionHandler, arena);
	this.target = arena.getPlayer();
	weapon = new Weapon(x, y, 15, 2 * width / 6, 0.5, this);
	armor = new Armor(100, this, arena, Images.getImage("dragon_armor.png"));
    }

    @Override
       protected void updateImage() {
           image = Images.getImage("dragon_" + Direction.toString(movingDirection) + ".png");
       }



}
