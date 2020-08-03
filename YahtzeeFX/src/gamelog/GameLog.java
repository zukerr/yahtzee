package gamelog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.text.Text;

public class GameLog implements Serializable
{
	private static final long serialVersionUID = -6981958919795289689L;
	private transient ObservableList<Node> nodeList;
	private int nodeAmount;
	private int pointer;
	private transient PrintWriter save;
	private ArrayList<String> nodeListValues;
	
	public void log(String text)
	{
		if(pointer == (nodeAmount - 1))
		{
			nodeListValues.remove(0);
			//move log 1-up
			moveLog();
		}
		
		setLogNumber(pointer, text);
		nodeListValues.add(text);
		
		
		if(pointer < (nodeAmount - 1))
		{
			pointer++;
		}
	}
	
	public void logToFile(String text)
	{
		save.println(text);
	}
	
	private void setLogNumber(int n, String text)
	{
		((Text)nodeList.get(n)).setText(text);
		logToFile(text);
	}
	
	private String getLogNumber(int n)
	{
		return ((Text)nodeList.get(n)).getText();
	}
	
	private void moveLog()
	{
		for(int i = 0; i < (nodeAmount -  1); i++)
		{
			setLogNumber(i, getLogNumber(i + 1));
		}
	}
	
	private void resetLog()
	{
		for(int i = 0; i < nodeAmount; i++)
		{
			((Text)nodeList.get(i)).setText("");
		}
	}
	
	public void closeStream()
	{
		save.close();
	}
	
	public void openStream()
	{
		try 
		{
			String temp = new File(GameLog.class.getProtectionDomain().getCodeSource().getLocation()
					.toURI()).getParentFile().getPath();
			save = new PrintWriter(temp + "/logs/log.txt");
		} 
		catch (FileNotFoundException | URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setup(ObservableList<Node> nodeList)
	{
		this.nodeList = nodeList;
		nodeAmount = nodeList.size();
		resetLog();
		reloadLog();
	}
	
	private void reloadLog()
	{
		for(int i = 0; i < nodeListValues.size(); i++)
		{
			((Text)nodeList.get(i)).setText((String)nodeListValues.get(i));
		}
	}
	
	public GameLog()
	{
		pointer = 0;
		openStream();
		nodeListValues = new ArrayList<String>();
	}
	
}
