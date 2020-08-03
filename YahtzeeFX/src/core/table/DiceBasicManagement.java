package core.table;

import java.util.ArrayList;
import java.util.Objects;

public class DiceBasicManagement 
{
	protected Dice[] kostki;
	
	//string containing values of all dice separated by space
	private String logText;

	//list containing all selected dice, ready to be rerolled
	private ArrayList<Integer> diceToReroll;
	
	public Dice[] getKostki() 
	{
		return kostki;
	}
	
	public ArrayList<Integer> getDiceToReroll()
	{
		return diceToReroll;
	}
	
	public String displayAllDices()
	{
		String temp = "";
		for(int i = 0; i < 5; i++)
		{
			temp += Integer.toString(kostki[i].getValue()) + " ";
		}
		return temp;
	}
	
	public int addAllDices()
	{
		int wynik = 0;
		for(int i = 0; i < 5; i++)
		{
			wynik += kostki[i].getValue();
		}
		return wynik;
	}
	
	public boolean doesThisNumberExist(int num)
	{
		for(int i = 0; i < 5; i++)
		{
			if(kostki[i].getValue() == num)
			{
				return true;
			}
		}
		return false;
	}
	
	public void rollAllDices()
	{
		for(Dice d1 : kostki)
		{
			d1.roll();
		}
	}
	
	public void rollDicesByIndexes(int[] tab)
	{
		logText = "";
		for(int i = 0; i < tab.length; i++)
		{
			logText += Integer.toString(kostki[tab[i]].getValue()) + " ";
			kostki[tab[i]].roll();
		}
	}
	
	public void rollSelectedDice()
	{
		int[] primitive = diceToReroll.stream().filter(Objects::nonNull).mapToInt(Integer::intValue).toArray();
		rollDicesByIndexes(primitive);
	}
	
	public String getLogText()
	{
		return logText;
	}
	
	public DiceBasicManagement(int liczbaKostek)
	{
		this.kostki = new Dice[liczbaKostek];
		diceToReroll = new ArrayList<Integer>();
		for(int i = 0; i < liczbaKostek; i++)
		{
			this.kostki[i] = new Dice(6);
		}
	}
	
	//default constructor for Serializable interface
	public DiceBasicManagement()
	{
		this(5);
	}
}
