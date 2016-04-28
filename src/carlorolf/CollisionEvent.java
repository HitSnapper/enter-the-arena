package carlorolf;

class CollisionEvent {
    private ArenaObject object;
    private Direction collisionDirection;

    public CollisionEvent(ArenaObject arenaObject, Direction collisionDirection) {
        object = arenaObject;
        this.collisionDirection = collisionDirection;
    }

    public ArenaObject getObject() {
        return object;
    }

    public Direction getDirection() {
        return  collisionDirection;
    }
}
