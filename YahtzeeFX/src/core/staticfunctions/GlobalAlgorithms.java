package core.staticfunctions;

public abstract class GlobalAlgorithms 
{
	//parsing string of ints divided by " "
	public static int[] parseStringOfIntegers(String s)
	{
		String[] integerStrings = s.split(" "); 
		int[] integers = new int[integerStrings.length]; 
		for (int i = 0; i < integers.length; i++)
		{
		    integers[i] = Integer.parseInt(integerStrings[i]); 
		}
		return integers;
	}
}
