package ai;

import core.players.Player;

public class ArtificialPlayer extends Player
{
	private static final long serialVersionUID = -870040656677193921L;

	public ArtificialPlayer(String imie) 
	{
		super(imie);
		this.artificial = true;
	}
}
