package core.table;

import java.util.*;

public class Dice 
{
	private int d;
	private int value;
	
	public int getValue() 
	{
		return value;
	}
	
	public void setValue(int value) 
	{
		this.value = value;
	}
	
	public void roll()
	{
		Random rng = new Random();
		value = rng.nextInt(d) + 1;
	}
	
	public Dice(int d)
	{
		this.d = d;
	}
}
