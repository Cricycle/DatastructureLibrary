package alex.checks;

public final class Assert
{
	public static void ArgInRangeIncl(int arg, int low, int high, String argName)
	{
	if (arg < low || arg > high)
			throw new IllegalArgumentException(format("Incl", argName, ""+low, ""+high, ""+arg));
	}
	
	public static void ArgInRangeIncl(long arg, long low, long high, String argName)
	{
		if (arg < low || arg > high)
			throw new IllegalArgumentException(format("Incl", argName, ""+low, ""+high, ""+arg));
	}
	
	public static void ArgInRangeIncl(float arg, float low, float high, String argName)
	{
		if (arg < low || arg > high)
			throw new IllegalArgumentException(format("Incl", argName, ""+low, ""+high, ""+arg));
	}
	
	public static void ArgInRangeIncl(double arg, double low, double high, String argName)
	{
		if (arg < low || arg > high)
			throw new IllegalArgumentException(format("Incl", argName, ""+low, ""+high, ""+arg));
	}
	
	public static void ArgInRangeExcl(int arg, int low, int high, String argName)
	{
		if (arg <= low || arg >= high)
			throw new IllegalArgumentException(format("Excl", argName, ""+low, ""+high, ""+arg));
	}
	
	public static void ArgInRangeExcl(long arg, long low, long high, String argName)
	{
		if (arg <= low || arg >= high)
			throw new IllegalArgumentException(format("Excl", argName, ""+low, ""+high, ""+arg));
	}
	
	public static void ArgInRangeExcl(float arg, float low, float high, String argName)
	{
		if (arg <= low || arg >= high)
			throw new IllegalArgumentException(format("Excl", argName, ""+low, ""+high, ""+arg));
	}
	
	public static void ArgInRangeExcl(double arg, double low, double high, String argName)
	{
		if (arg <= low || arg >= high)
			throw new IllegalArgumentException(format("Excl", argName, ""+low, ""+high, ""+arg));
	}
	
	private static String format(String check, String argName, String low, String high, String arg)
	{
		return String.format("Error: %s must be between %s and %s, %s, but has value %s.", argName, low, high, check, arg);
	}
}