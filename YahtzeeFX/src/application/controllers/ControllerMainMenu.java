package application.controllers;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import serialization.GameSerializer;

public class ControllerMainMenu
{
    @FXML
    void omEnterHighlightButton(MouseEvent event) 
    {
    	((Button)event.getTarget()).setTextFill(Color.GREY);
    }

    @FXML
    void omExitHighlightButton(MouseEvent event) 
    {
    	((Button)event.getTarget()).setTextFill(Color.WHITE);
    }

    @FXML
    void startGame(ActionEvent event) 
    {	
    	Main.sceneHandler.setScene("/application/fxmls/SubMenu1.fxml");
    }
    
    @FXML
    void exitGame(ActionEvent event) 
    {	
    	Main.game.exitProgram();
    }
    
    @FXML
    void loadGame(ActionEvent event)
    {
    	loadGameAction();
    }
    
    public void loadGameAction()
    {
    	GameSerializer.loadGame();
    	Main.game.licznikGraczyGoBack();
    	loadGameWindow();
    }
    
    public void loadGameWindow()
    {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxmls/GameWindow.fxml"));
		Parent root = null;
		try 
		{
			root = (Parent)loader.load();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		ControllerGameWindow controller = (ControllerGameWindow)loader.getController();
		controller.setup();
		Scene temp = new Scene(root);
		keyBinding(controller, temp, KeyCode.CONTROL, KeyCode.ESCAPE);
		Main.sceneHandler.mainStage.setScene(temp);
		
		//here the new currentPlayer's setup and game window scene setup is complete and I need to call a function if the player is a bot
		if(Main.game.getCurrentPlayer().isArtificial())
		{
			controller.startBotTurn();
		}
    }

	private void keyBinding(ControllerGameWindow controller, Scene temp, KeyCode scoreboardHotkey, KeyCode escHotkey) 
	{
		temp.setOnKeyPressed
		(e->{
				if (e.getCode() == scoreboardHotkey) 
				{
					controller.summaryDisplayOn();
				}
				if (e.getCode() == escHotkey) 
				{
					if(!controller.getGameLogOn())
					{
						if(controller.getPauseMenuOn())
						{
							controller.stopDisplayEscMenu();
						}
						else
						{
							controller.displayEscMenu();
						}
					}
					else
					{
						controller.stopDisplayGameLog();
					}
				}
		});
		temp.setOnKeyReleased
		(e->{
				if (e.getCode() == scoreboardHotkey) 
				{
					controller.summaryDisplayOff();
				}
		});
	}
}
