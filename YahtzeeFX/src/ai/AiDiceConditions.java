package ai;

import java.util.ArrayList;

import application.Main;
import core.table.DiceConditionsManager;

public class AiDiceConditions
{
	private DiceConditionsManager dcm;
	
	private ArrayList<Integer> toThrow;
	
	private boolean readyToConfirm;
	
	private int choice;
	
	public int getChoice()
	{
		return choice;
	}
	
	public boolean getReadyToConfirm()
	{
		return readyToConfirm;
	}
	
	public void setReadyToConfirm(boolean readyToConfirm)
	{
		this.readyToConfirm = readyToConfirm;
	}
	
	public ArrayList<Integer> getToThrow()
	{
		return toThrow;
	}
	
	public void firstTurnStrat()
	{
		toThrow.clear();
		noConditions();
		conditionPair();
		conditionThreeOfAKind();
		conditionSmallStraight();
		conditionLargeStraight();
		conditionTwoPairs();
		conditionFull();
		conditionFourOfAKind();
		conditionGeneral();
	}
	
	public void makeChoice()
	{
		if(dcm.areThereMaxXsameDices(5))
		{
			choice = 12;
		}
		else if(dcm.strit(false))
		{
			choice = 11;
		}
		else if(dcm.strit(true))
		{
			choice = 10;
		}
		else if(dcm.full())
		{
			choice = 9;
		}
		else if(dcm.areThereMaxXsameDices(4))
		{
			choice = dcm.getValueOnAtLeastXDice(4);
			if(Main.game.getCurrentPlayer().getKategorie(choice - 1))
			{
				choice = 8;
			}
		}
		else if(dcm.areThereMaxXsameDices(3))
		{
			choice = dcm.getValueOnAtLeastXDice(3);
			if(Main.game.getCurrentPlayer().getKategorie(choice - 1))
			{
				choice = 7;
			}
		}
		else if((dcm.areThereMaxXsameDices(2))||(dcm.areThereMaxXsameDices(1)))
		{
			choice = 1;
		}
		lastResort();
	}
	
	public void lastResort()
	{
		if(Main.game.getCurrentPlayer().getKategorie(choice - 1))
		{
			for(int i = 0; i < 13; i++)
			{
				if(!Main.game.getCurrentPlayer().getKategorie(i))
				{
					choice = (i + 1);
				}
			}
		}
	}
	
	public void conditionFourOfAKind()
	{
		condition1and2(dcm.areThereMaxXsameDices(4), 4);
	}
	
	public void conditionGeneral()
	{
		if(dcm.areThereMaxXsameDices(5))
		{
			toThrow.clear();
			readyToConfirm = true;
			choice = 12;
		}
	}
	
	public void noConditions()
	{
		toThrow.clear();
		for(int i = 0; i < 4; i++)
		{
			toThrow.add(i);
		}
	}
	
	public void conditionFull()
	{
		condition1and2(dcm.full(), 3);
	}
	
	public void conditionTwoPairs()
	{
		condition1and2(pairs(2), 2);
	}
	
	public void conditionPair()
	{
		condition1and2(pairs(1), 2);
	}
	public void conditionThreeOfAKind()
	{
		condition1and2(dcm.areThereMaxXsameDices(3), 3);
	}
	
	public void conditionLargeStraight()
	{
		if(dcm.strit(false))
		{
			toThrow.clear();
			readyToConfirm = true;
			choice = 11;
		}
	}
	
	public void conditionSmallStraight()
	{
		if(dcm.strit(true))
		{
			smallStraightInsight(1);
			smallStraightInsight(2);
			smallStraightInsight(3);
		}
	}

	public void smallStraightInsight(int n) 
	{
		if(dcm.doesThisNumberExist(n))
		{
			toThrow.clear();
			for(int j = 0; j < 5; j++)
			{
				toThrow.add(j);
				for(int i = n; i <= (n+3); i++)
				{
					if(dcm.getKostki()[j].getValue() == i)
					{
						toThrow.remove((Integer)j);
					}
				}
			}
		}
	}
	
	public void condition1and2(boolean mainCondition, int number)
	{
		if(mainCondition)
		{
			toThrow.clear();
			int temp = dcm.getValueOnAtLeastXDice(number);
			for(int i = 0; i < 5; i++)
			{
				if(dcm.getKostki()[i].getValue() != temp)
				{
					toThrow.add(i);
				}
			}
		}
	}
	
	public boolean pairs(int howMany)
	{
		if(!dcm.full())
		{
			if(dcm.areThereMaxXsameDices(2))
			{
				int[] tab = dcm.getValuesTab();
				int counter = 0;
				for(int i = 0; i < 6; i++)
				{
					if(tab[i]==2)
					{
						counter++;
					}
				}
				if(counter == howMany)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public AiDiceConditions(DiceConditionsManager dcm)
	{
		this.dcm = dcm;
		this.toThrow = new ArrayList<Integer>();
		readyToConfirm = false;
	}
}
