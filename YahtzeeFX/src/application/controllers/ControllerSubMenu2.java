package application.controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import validation.Validation;

public class ControllerSubMenu2 extends ControllerMainMenu
{
    @FXML
    private JFXTextField textField1;

    @FXML
    private Button confirmButton;
    
    @FXML
    private Text errorText;
    
    @FXML
    private JFXCheckBox cBox;

    @FXML
    void confirmPlayer(ActionEvent event) 
    {
    	if(Validation.playerName(textField1.getText()))
    	{
    		errorText.setVisible(false);
    		//Create either normal player or bot
    		Main.game.setNewPlayer(textField1.getText(), cBox.isSelected());
        	if(Main.game.isNewPlayerPossible())
        	{
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxmls/SubMenu2.fxml"));
        		Parent root = null;
        		try 
        		{
        			root = (Parent)loader.load();
        		} 
        		catch (IOException e) 
        		{
        			e.printStackTrace();
        		}
        		ControllerSubMenu2 controller = (ControllerSubMenu2)loader.getController();
    			controller.changePrompt();
    			Main.sceneHandler.mainStage.setScene(new Scene(root));
        	}
        	else
        	{
        		loadGameWindow();
        	}
    	}
    	else
    	{
    		errorText.setVisible(true);
    	}
    }
    
    public void changePrompt()
    {
    	textField1.setPromptText(Main.game.getPlayerCounterPromptText());
    }
    

    
    
}
