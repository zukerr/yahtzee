package ai;

import java.util.concurrent.TimeUnit;

import application.Main;
import application.controllers.ControllerGameWindow;
import javafx.application.Platform;

public class Bot 
{
	private ControllerGameWindow cGameWindow;
	
	private AiDiceConditions aiDC;
	
	private void wait(int seconds)
	{
		try 
		{
			TimeUnit.SECONDS.sleep(seconds);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void botTurn()
	{
		wait(2);
		cGameWindow.startTurnAction();
		wait(2);
		
		//Algorithm involving choosing dices to reroll, rerolling, checking if it wants to reroll again, rerolling (or not) and then choosing a category
		if(!aiDC.getReadyToConfirm())
		{
			botThrow();
			wait(2);
			cGameWindow.rerollAction();
			wait(2);
		}
		
		if(!aiDC.getReadyToConfirm())
		{
			botThrow();
			wait(2);
			cGameWindow.rerollAction();
			wait(2);
		}
		
		else
		{
			aiDC.setReadyToConfirm(false);
		}
		
		aiDC.makeChoice();
		
		cGameWindow.typeCategoryAction(aiDC.getChoice());
		
		wait(2);
		
		//Main.sceneHandler.setScene("MainMenu.fxml");
		Platform.runLater
		(
			new Runnable() 
			{
				@Override
				public void run() 
				{
					// Update UI here.
					cGameWindow.confirmScoreAction();
				}
			}
		);
	}
	
	public void botThrow()
	{
		aiDC.firstTurnStrat();
		for(Integer i : aiDC.getToThrow())
		{
			cGameWindow.clickDiceAction(i.intValue());
		}
	}
	
	public Bot(ControllerGameWindow cGameWindow)
	{
		this.cGameWindow = cGameWindow;
		aiDC = new AiDiceConditions(Main.game.getDiceManager());
	}
}
