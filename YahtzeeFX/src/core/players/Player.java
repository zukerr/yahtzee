package core.players;

import java.io.Serializable;

import core.DiceGame;

public class Player implements Comparable<Player>, Serializable
{
	private static final long serialVersionUID = -1417133167986419841L;
	private String imie;
	private boolean[] kategorie;
	private int[] punktyWRundzie;
	private int[] punktyDoPremiiWRundzie;
	protected boolean artificial;
	
	public boolean isArtificial()
	{
		return artificial;
	}
	
	public int getPunktyDoPremiiWRundzie(int nr) 
	{
		return punktyDoPremiiWRundzie[nr];
	}
	
	public int getPunktyWRundzie(int nr) 
	{
		return punktyWRundzie[nr];
	}
	
	public String getImie() 
	{
		return imie;
	}
	
	public void setImie(String imie) 
	{
		this.imie = imie;
	}
	
	public boolean getKategorie(int nr) 
	{
		return kategorie[nr];
	}
	
	public void setKategorie(int i, boolean wartosc)
	{
		this.kategorie[i] = wartosc;
	}
	
	public void setPunktyWRundzie(int i, int wartosc)
	{
		this.punktyWRundzie[i] = wartosc;
	}
	
	public void setPunktyDoPremiiWRundzie(int i, int wartosc)
	{
		this.punktyDoPremiiWRundzie[i] = wartosc;
	}
	
	public void resetKategorie()
	{
		for(int i = 0; i < 13; i++)
		{
			this.kategorie[i] = false;
		}
	}
	
	public Player(String imie) 
	{
		super();
		this.imie = imie;
		this.kategorie = new boolean[13];
		this.punktyWRundzie = new int[DiceGame.liczbaRund];
		this.punktyDoPremiiWRundzie = new int[DiceGame.liczbaRund];
		this.artificial = false;
		resetKategorie();
		for(int i = 0; i < DiceGame.liczbaRund; i++)
		{
			punktyWRundzie[i] = 0;
			punktyDoPremiiWRundzie[i] = 0;
		}
	}
	
	@Override
	public int compareTo(Player o) 
	{
		int temp;
		for(int i = 0; i < punktyWRundzie.length; i++)
		{
			temp = Integer.compare(punktyWRundzie[i], o.getPunktyWRundzie(i));
			if(temp != 0)
			{
				return temp;
			}
		}
		return 0;
	}
	
}
