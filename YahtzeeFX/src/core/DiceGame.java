package core;

import java.io.Serializable;

import ai.ArtificialPlayer;
import core.players.Player;
import core.players.PlayerInRound;
import core.staticfunctions.TextEvent;
import core.table.DiceCombinationManager;
import core.table.DiceConditionsManager;
import gamelog.GameLog;

public class DiceGame implements Serializable
{
	private static final long serialVersionUID = 8903769756562268694L;
	private Player currentPlayer;
	private PlayerInRound currentPIR;
	private Player[] gracze;
	private int licznikGraczy;
	public static int liczbaRund;
	private int licznikRundy;
	private int liczbaGraczy;
	private int obslugaTury;
	private int licznikKolejki;
	private final int liczbaKostek = 5;
	private DiceConditionsManager diceManager;
	private boolean gameOver;
	private GameLog gameLog;
	private boolean firstTime;
	
	public boolean getGameOver()
	{
		return gameOver;
	}
	
	public boolean getFirstTime()
	{
		return firstTime;
	}
	
	public void firstTimeHappened()
	{
		firstTime = false;
	}
	
	public GameLog getGameLog()
	{
		return gameLog;
	}
	
	public void exitProgram()
	{
		if(gameLog != null)
		{
			gameLog.closeStream();
		}
		System.exit(0);
	}
	
	public Player getPlayerNumber(int index)
	{
		if(gracze==null)
		{
			System.out.println(index);
		}
		return gracze[index];
	}
	
	public int getLiczbaGraczy()
	{
		return liczbaGraczy;
	}
	
	public DiceConditionsManager getDiceManager()
	{
		return diceManager;
	}
	
	public DiceCombinationManager getDiceCombinationManager()
	{
		return (DiceCombinationManager)diceManager;
	}
	
	public PlayerInRound getCurrentPIR()
	{
		return currentPIR;
	}
	
	public boolean tura()
	{
		if(obslugaTury == 0)
		{
			obslugaTury++;
			return true;
		}
		else
		{
			obslugaTury = 0;
			return false;
		}
	}
	
	public void setCurrentPIR()
	{
		currentPIR = new PlayerInRound((licznikGraczy - 1), licznikRundy, gracze, diceManager);
		obslugaTury = 0;
	}
	
	public void calculateTurn(int ind)
	{
		currentPIR.przydzielPunkty(ind);
		gameLog.log
		(
			currentPlayer.getImie() 
			+ " has chosen category number " 
			+ Integer.toString(ind) 
			+ " and now has " 
			+ currentPlayer.getPunktyWRundzie(licznikRundy) 
			+ " points."
		);
		if(licznikGraczy == liczbaGraczy)
		{
			nastepnaKolejka();
		}
	}
	
	public int getLicznikRundy()
	{
		return licznikRundy;
	}
	
	private void nastepnaRunda()
	{
		if(licznikRundy < (liczbaRund - 1))
		{
			licznikRundy++;
		}
		else
		{
			//koniec gry
			gameOver = true;
		}
	}
	
	private void nastepnaKolejka()
	{
		if(licznikKolejki < 12)
		{
			licznikKolejki++;
		}
		else
		{
			licznikKolejki = 0;
			nastepnaRunda();
		}
	}
	
	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	private Player getNextPlayer()
	{
		if(licznikGraczy == liczbaGraczy)
		{
			licznikGraczy = 0;
		}
		currentPlayer = gracze[licznikGraczy];
		return gracze[licznikGraczy++];
	}
	
	public void licznikGraczyGoBack()
	{
		if(licznikGraczy == 0)
		{
			licznikGraczy = liczbaGraczy;
		}
		licznikGraczy--;
	}
	
	public String getNextPlayersTurnString()
	{
		return TextEvent.outNextPlayersTurn(getNextPlayer().getImie());
	}
	
	public String getPlayerCounterPromptText()
	{
		return TextEvent.outTypeNameOfPlayerNumber(licznikGraczy+1);
	}

	public void setRoundsAndPlayers(String players, String rounds)
	{
		liczbaRund = Integer.parseInt(rounds);
		liczbaGraczy = Integer.parseInt(players);
		gracze = new Player[liczbaGraczy];
		diceManager = new DiceCombinationManager(liczbaKostek);
		obslugaTury = 0;
	}
	
	public void setNewPlayer(String name, boolean artificial)
	{
		if(!artificial)
		{
			gracze[licznikGraczy] = new Player(name);
		}
		else
		{
			gracze[licznikGraczy] = new ArtificialPlayer(name);
		}
		licznikGraczy++;
	}
	
	public boolean isNewPlayerPossible()
	{
		return licznikGraczy < liczbaGraczy ? true : false;
	}
	
	public DiceGame()
	{
		licznikGraczy = 0;
		licznikRundy = 0;
		licznikKolejki = 0;
		gameOver = false;
		firstTime = true;
		gameLog = new GameLog();
	}
}
