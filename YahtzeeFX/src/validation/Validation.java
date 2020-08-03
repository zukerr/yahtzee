package validation;

public abstract class Validation 
{
	public static boolean roundsAndPlayersCount(String roundsCount, String playersCount)
	{
		try
		{
			int rc = Integer.parseInt(roundsCount);
			int pc = Integer.parseInt(playersCount);
			if(rc > 0)
			{
				if(pc > 0)
				{
					return true;
				}
			}
			return false;
		}
		catch(NumberFormatException nfe)  
		{
			return false;
		}
	}
	public static boolean playerName(String name)
	{
		return !name.isEmpty() ? true : false;
	}
	public static boolean categoryNumber(String numberString)
	{
		try
		{
			int ns = Integer.parseInt(numberString);
			if((ns >= 1)&&(ns <= 13))
			{
				return true;
			}
			return false;
		}
		catch(NumberFormatException nfe)  
		{
			return false;
		}
	}
}
