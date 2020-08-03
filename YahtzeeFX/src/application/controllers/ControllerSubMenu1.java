package application.controllers;

import com.jfoenix.controls.JFXTextField;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import validation.Validation;

public class ControllerSubMenu1 extends ControllerMainMenu {

    @FXML
    private JFXTextField textField1;

    @FXML
    private JFXTextField textField2;
    
    @FXML
    private Button confirmButton;
    
    @FXML
    private Text errorText;

    @FXML
    void confirmSettings1(ActionEvent event) 
    {
    	if(Validation.roundsAndPlayersCount(textField1.getText(), textField2.getText()))
		{
    		errorText.setVisible(false);
    		Main.game.setRoundsAndPlayers(textField1.getText(), textField2.getText());
			Main.sceneHandler.setScene("fxmls/SubMenu2.fxml");
		}
    	else
    	{
    		errorText.setVisible(true);
    	}
    }
}