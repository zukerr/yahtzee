package core.players;

import java.io.Serializable;

import core.staticfunctions.TextEvent;
import core.table.DiceCombinationManager;
import core.table.DiceConditionsManager;

public class PlayerInRound implements Serializable
{
	private static final long serialVersionUID = 1341496078727853066L;
	private int nrGracza;
	private int nrRundy;
	private Player[] gracze;
	private DiceConditionsManager manager;
	
	public void givePoints(int points)
	{
		gracze[nrGracza].setPunktyWRundzie(nrRundy, gracze[nrGracza].getPunktyWRundzie(nrRundy) + points);
	}
	
	public void givePremia()
	{
		if(gracze[nrGracza].getPunktyDoPremiiWRundzie(nrRundy) >= 63)
		{
			this.givePoints(35);
		}
	}
	
	public void przydzielPkt1(boolean condition, int points)
	{
		if(!czyJoker())
		{
			if(!condition)
			{
				points = 0;
			}
		}
		this.givePoints(points);
	}
	public void przydzielPunktyZDoluTabeli(int c)
	{
		switch(c)
		{
		case 1: przydzielPkt1(manager.areThereXsameDices(3), manager.addAllDices());
			break;
		case 2: przydzielPkt1(manager.areThereXsameDices(4), manager.addAllDices());
			break;
		case 3: przydzielPkt1(manager.full(), 25);
			break;
		case 4: przydzielPkt1(manager.strit(true), 30);
			break;
		case 5: przydzielPkt1(manager.strit(false), 40);
			break;
		case 6: przydzielPkt1(manager.areThereXsameDices(5), 50);
			break;
		case 7:
			this.givePoints(manager.addAllDices());
			break;
		}
	}
	
	public void przydzielPunktyZGoryTabeli(int c)
	{
		int sum = ((DiceCombinationManager)manager).upperSectionPoints(c);
		givePoints(sum);
		gracze[nrGracza].setPunktyDoPremiiWRundzie(nrRundy, gracze[nrGracza].getPunktyDoPremiiWRundzie(nrRundy)+sum);
	}
	
	public boolean isCategoryBusy(int inpt)
	{
		if(gracze[nrGracza].getKategorie(inpt-1))
		{
			return true;
		}
		else
			return false;
	}
	
	public void przydzielPunkty(int indexKategoriiRaw)
	{
		jokerPremiaHandler();
		if((indexKategoriiRaw >= 1)&&(indexKategoriiRaw <= 6))
		{
			przydzielPunktyZGoryTabeli(indexKategoriiRaw);
			gracze[nrGracza].setKategorie((indexKategoriiRaw - 1), true);
		}
		else
		{
			przydzielPunktyZDoluTabeli(indexKategoriiRaw - 6);
			gracze[nrGracza].setKategorie((indexKategoriiRaw - 1), true);
		}
	}
	
	public boolean czyJoker()
	{
		if(manager.areThereXsameDices(5))
		{
			if(gracze[nrGracza].getKategorie(11))
			{
				int liczba_na_kostkach = manager.getKostki()[0].getValue();
				if(gracze[nrGracza].getKategorie(liczba_na_kostkach - 1))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public void jokerPremiaHandler()
	{
		if(czyJoker())
		{
			TextEvent.outCongratulationJoker();
			givePoints(100);
		}
	}
	
	public PlayerInRound(int nrGracza, int nrRundy, Player[] gracze, DiceConditionsManager manager)
	{
		this.nrGracza = nrGracza;
		this.nrRundy = nrRundy;
		this.gracze = gracze;
		this.manager = manager;
	}
}
