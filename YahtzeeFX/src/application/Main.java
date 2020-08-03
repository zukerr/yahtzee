package application;
	
import core.DiceGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application 
{
	public static SceneHandler sceneHandler;
	public static DiceGame game;
	
    @Override
    public void start(Stage primaryStage) 
    {
    	try
    	{
    		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/MainMenu.fxml"));
    		Scene scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.show();
    		sceneHandler = new SceneHandler();
    		sceneHandler.mainStage = primaryStage;
    		game = new DiceGame();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
