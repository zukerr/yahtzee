package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import java.nio.file.Paths;

public class SceneHandler 
{
	public Stage mainStage;
	
	public void setScene(String path)
	{
    	try
    	{
    		mainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(path))));
    		//mainStage.setScene(new Scene(FXMLLoader.load(Paths.get("C:\\Users\\Zuker\\Programming\\JAVA\\Java Projects\\YahtzeeFX\\src\\application\\fxmls\\" + path).toUri().toURL())));
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
	}
}
