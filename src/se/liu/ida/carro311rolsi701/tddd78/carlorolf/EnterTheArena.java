package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

/**
 * This is the main class with timer for screen updates and timer for physics.
 */
final class EnterTheArena {
    //This is the main method, that's why it's static
    public static void main(String[] args) {
        final int arenaWidth = 20;
        final int arenaHeight = 20;
        final int frameWidth = 1000;
        final int frameHeight = 600;
        final int outsideFrame = 52;
        final ArenaComponent arenaComponent = new ArenaComponent(frameWidth, frameHeight - outsideFrame, arenaWidth, arenaHeight);
        final ArenaFrame arenaFrame = new ArenaFrame(frameWidth, frameHeight, arenaComponent);

        // Zero = unlocked
        final int frameTick = 60;
        final int physicsTick = 200;

        FrameThread frameThread = new FrameThread(arenaFrame, frameTick, arenaComponent);
        frameThread.start();

        PhysicsThread physicsThread = new PhysicsThread(arenaComponent, physicsTick);
        physicsThread.start();
    }
}
