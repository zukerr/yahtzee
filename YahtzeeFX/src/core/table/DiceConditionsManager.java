package core.table;

import java.io.Serializable;

public class DiceConditionsManager extends DiceBasicManagement implements Serializable
{
	private static final long serialVersionUID = -8669844229796280400L;

	private int maxSameDices()
	{
		int max = 0;
		int[] tab = getValuesTab();
		for(int i = 0; i < 6; i++)
		{
			if(tab[i] > max)
			{
				max = tab[i];
			}
		}
		return max;
	}

	public int[] getValuesTab() 
	{
		int[] tab = new int[6];
		for(int i = 0; i < 6; i++)
		{
			tab[i] = 0;
		}
		for(int i = 0; i < 5; i++)
		{
			tab[kostki[i].getValue() - 1]++;
		}
		return tab;
	}
	
	public boolean areThereXsameDices(int ilosc)
	{
		return maxSameDices() >= ilosc ? true : false;
	}
	public boolean areThereMaxXsameDices(int ilosc)
	{
		return maxSameDices() == ilosc ? true : false;
	}
	
	public boolean full()
	{
		if(this.areThereMaxXsameDices(3))
		{
			int valueOnThreeDice = getValueOnAtLeastXDice(3);
			boolean[] analyzedDice = new boolean[5];
			for(int i = 0; i < 5; i++)
			{	
				analyzedDice[i] = (kostki[i].getValue() == valueOnThreeDice) ? true : false;
			}
			int k1 = 0;
			for(int i = 0; i < 5; i++)
			{	
				if(analyzedDice[i] == false)
				{
					if(k1 == 0)
					{
						k1 = kostki[i].getValue();
					}
					else
					{
						if(k1 == kostki[i].getValue())
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public int getValueOnAtLeastXDice(int x) 
	{
		int valueOnXDice = -1;
		for(int i = 1; i < 7; i++)
		{
			int counter = 0;
			for(int j = 0; j < 5; j++)
			{
				if(kostki[j].getValue() == i)
				{
					counter++;
					if(counter == x)
					{
						valueOnXDice = kostki[j].getValue();
						i = 7;
						j = 6;
					}
				}
			}
		}
		return valueOnXDice;
	}
	
	public boolean strit(boolean maly)
	{
		int max_oczek = 0;
		for(int i = 0; i < 5; i++)
		{
			if(kostki[i].getValue() > max_oczek)
			{
				max_oczek = kostki[i].getValue();
			}
		}
		if(maly)
		{
			return smallStraight();
		}
		else
		{
			if((max_oczek == 5)||(max_oczek == 6))
			{
				if(this.doesThisNumberExist(max_oczek - 1)
					&&this.doesThisNumberExist(max_oczek - 2)
					&&this.doesThisNumberExist(max_oczek - 3)
					&&this.doesThisNumberExist(max_oczek - 4))
				{
					return true;
				}
				else
					return false;
			}
			return false;
		}
	}
	
	private boolean smallStraight()
	{
		boolean temp1 = false;
		boolean temp2 = false;
		boolean temp3 = false;
		
		if(this.doesThisNumberExist(1))
		{
			temp1 = subSmallStraight(2, 3, 4);
		}
		if (this.doesThisNumberExist(2))
		{
			temp2 = subSmallStraight(3, 4, 5);
		}
		if (this.doesThisNumberExist(3))
		{
			temp3 = subSmallStraight(4, 5, 6);
		}
		else
		{
			return false;
		}
		return (temp1 || temp2 || temp3);
	}
	
	private boolean subSmallStraight(int a, int b, int c)
	{
		if(this.doesThisNumberExist(a))
		{
			if(this.doesThisNumberExist(b))
			{
				if(doesThisNumberExist(c))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public DiceConditionsManager(int liczbaKostek) 
	{
		super(liczbaKostek);
	}
}
