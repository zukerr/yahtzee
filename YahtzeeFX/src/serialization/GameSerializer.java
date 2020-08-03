package serialization;

import java.io.*;
import java.net.URISyntaxException;

import application.Main;
import core.DiceGame;
import gamelog.GameLog;

public class GameSerializer 
{
	private static String temp;

	static {
		try {
			temp = new File(GameLog.class.getProtectionDomain().getCodeSource().getLocation()
						.toURI()).getParentFile().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private static String path = temp + "/save/core_save.bin";
	
	public static void saveGame()
	{
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) 
		{
		    outputStream.writeObject(Main.game);
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void loadGame()
	{
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))) 
		{
			Main.game = (DiceGame)inputStream.readObject();
			Main.game.getGameLog().openStream();
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
