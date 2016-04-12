package carlorolf;

import java.util.List;

public class CollisionHandler
{
    List<ArenaObject> objects;

    public CollisionHandler(Arena arena) {
	objects = arena.getObjectList();
    }

    public void update() {
	for (ArenaObject obj1 : objects) {
	    for (ArenaObject obj2 : objects) {
		if (obj1 != obj2) {
		    handleCollision(obj1, obj2);
		}
	    }
	}
    }

    private void handleCollision(ArenaObject a1, ArenaObject a2) {


    }
}
