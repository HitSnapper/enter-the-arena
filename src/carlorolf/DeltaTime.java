package carlorolf;

public final class DeltaTime {
    private static final DeltaTime INSTANCE = new DeltaTime();

    private static double dt;

    public static double getDt() {
        return dt * 0.001;
    }

    public static void setDt(final double dT) {
        DeltaTime.dt = dT;
    }

    public static DeltaTime getInstance() {
        return INSTANCE;
    }
}
