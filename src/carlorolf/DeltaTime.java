package carlorolf;

public final class DeltaTime
{
    private static final DeltaTime INSTANCE = new DeltaTime();

    private static double dT;

    public static double getdT() {
	return dT*0.001;
    }

    public static void setdT(final double dT) {
	DeltaTime.dT = dT;
    }

    public static DeltaTime getInstance(){
	return INSTANCE;
    }
}
