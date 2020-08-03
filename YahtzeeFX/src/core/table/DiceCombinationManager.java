package core.table;

public class DiceCombinationManager extends DiceConditionsManager
{
	private static final long serialVersionUID = -5676740387594705039L;

	public int upperSectionPoints(int val)
	{
		int sum = 0;
		for(int i = 0; i < 5; i++)
		{
			if(kostki[i].getValue() == val)
			{
				sum += kostki[i].getValue();
			}
		}
		return sum;
	}
	
	public int threeOfAKindPoints()
	{
		return this.areThereXsameDices(3) ? this.addAllDices() : 0;
	}
	
	public int fourOfAKindPoints()
	{
		return this.areThereXsameDices(4) ? this.addAllDices() : 0;
	}
	
	public int fullHousePoints()
	{
		return this.full() ? 25 : 0;
	}
	
	public int smallStraightPoints()
	{
		return this.strit(true) ? 30 : 0;
	}
	
	public int largeStraightPoints()
	{
		return this.strit(false) ? 40 : 0;
	}
	
	public int yahtzeePoints()
	{
		return this.areThereXsameDices(5) ? 50 : 0;
	}
	
	public int chancePoints()
	{
		return this.addAllDices();
	}
	
	public DiceCombinationManager(int liczbaKostek) 
	{
		super(liczbaKostek);
	}
}
