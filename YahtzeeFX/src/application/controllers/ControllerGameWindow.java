package application.controllers;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jfoenix.controls.JFXTextField;

import ai.Bot;
import application.Main;
import core.DiceGame;
import core.players.Player;
import gamelog.GameLog;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import serialization.GameSerializer;
import validation.Validation;

public class ControllerGameWindow extends ControllerSubMenu2
{
	@FXML
    private AnchorPane corePanel;
	
    @FXML
    private Text playerNameText;

    @FXML
    private Text subtitle;

    @FXML
    private Pane dice;

    @FXML
    private Pane checkmarks;

    @FXML
    private TilePane table1;

    @FXML
    private TilePane table2;

    @FXML
    private Button goButton;
    
    @FXML
    private Button rerollButton;
    
    @FXML
    private Button confirmScoreButton;
    
    @FXML
    private JFXTextField categoryTextField;
    
    @FXML
    private Pane scoreboard;
    
    @FXML
    private Text nameScoreboardLabel;
    
    @FXML
    private Text pointsScoreboardLabel;
    
    @FXML
    private AnchorPane summaryScoreboard;
    
    @FXML
    private AnchorPane escMenu;
    
    @FXML
    private AnchorPane gameLog;
    
    @FXML
    private Pane summaryPointsPane;
    
    @FXML
    private Pane summaryPlayersPane;
    
    @FXML
    private Pane gameLogPane;
    
    @FXML
    private Text errorText1;
    
    @FXML
    private Text errorText2;
    
    @FXML
    private Button escMenuButton;
    
    //List of dice nodes
    private ObservableList<Node> diceList;
    
    //List of checkmark nodes
    private ObservableList<Node> checkmarkList;
    
    private boolean diceClickable;
    
    private boolean pauseMenuOn;
    
    private boolean gameLogOn;
    
    //List of upper section scoreboard numbers
    private ObservableList<Node> upperScoreboardList;
    
    //List of lower section scoreboard numbers
    private ObservableList<Node> lowerScoreboardList;
    
    //List of possible images on dices
    private ArrayList<Image> diceNumeredImages;
    
    //Players sorted by points
    private TreeSet<Player> playersSet;
    
    //Texts representing players on summary scoreboard
    private ObservableList<Node> nameTexts;
    
    //Texts representing points on summary scoreboard
    private ObservableList<Node> pointsTexts;
    
    //object responsible for ai
    private Bot bot;
    
    public void startBotTurn()
    {
    	corePanel.setMouseTransparent(true);
    	ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit
        (
        		new Runnable() 
        		{
        			public void run() 
        			{
        				bot.botTurn();
        			}
        		}
        ); 
    }
    
    @FXML
    void startTurn(ActionEvent event) 
    {
    	startTurnAction();
    }

	public void startTurnAction() 
	{
		//There could be an animation
    	Main.game.getDiceManager().rollAllDices();
    	Main.game.setCurrentPIR();
    	setDiceImages();
    	displayPossibleScore();
    	scoreboard.setVisible(true);
    	diceClickable = true;
    	setupSecondThrow();
    	Main.game.getGameLog().log
    	(
    		Main.game.getCurrentPlayer().getImie()
    		+ " started their turn and rolled: "
    		+ Main.game.getDiceManager().displayAllDices()
    	);
	}
    
    public void setupSecondThrow()
    {
    	subtitle.setText("You can select dice to reroll or choose category and confirm score");
    }
    public void setup()
    {
    	diceList = dice.getChildren();
    	checkmarkList = checkmarks.getChildren();
    	Main.game.getGameLog().setup(gameLogPane.getChildren());
    	setupScoreboard();
    	diceNumeredImages = new ArrayList<Image>();
		String temp = "";
		try {
			temp = new File(GameLog.class.getProtectionDomain().getCodeSource().getLocation()
					.toURI()).getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		diceNumeredImages.add(new Image(getClass().getResource("/assets/1.png").toString(), true));
    	diceNumeredImages.add(new Image(getClass().getResource("/assets/2.png").toString(), true));
    	diceNumeredImages.add(new Image(getClass().getResource("/assets/3.png").toString(), true));
    	diceNumeredImages.add(new Image(getClass().getResource("/assets/4.png").toString(), true));
    	diceNumeredImages.add(new Image(getClass().getResource("/assets/5.png").toString(), true));
    	diceNumeredImages.add(new Image(getClass().getResource("/assets/6.png").toString(), true));
    	diceClickable = false;
    	pauseMenuOn = false;
    	gameLogOn = false;
    	setupIDs();
    	setupSummary();
    	bot = new Bot(this);
    	
    	if(Main.game.getFirstTime())
    	{
    		Main.game.getGameLog().log
        	(
        		"Game has started with " 
        		+ Integer.toString(Main.game.getLiczbaGraczy()) 
        		+ " players for " 
        		+ Integer.toString(DiceGame.liczbaRund)
        		+ " rounds."
        	);
    		Main.game.firstTimeHappened();
    	}
    	
    	changePlayersName();
    }
    public void setupSummary()
    {
    	playersSet = new TreeSet<>(Collections.reverseOrder());
    	for(int i = 0; i < Main.game.getLiczbaGraczy(); i++)
    	{
    		playersSet.add(Main.game.getPlayerNumber(i));
    	}
    	nameTexts = summaryPlayersPane.getChildren();
    	pointsTexts = summaryPointsPane.getChildren();
    	//playersSet = new TreeSet<Player>(Collections.reverseOrder());
    	nameScoreboardLabel.setText("1. " + Main.game.getPlayerNumber(0).getImie());
    	pointsScoreboardLabel.setText(Integer.toString(Main.game.getPlayerNumber(0).getPunktyWRundzie(Main.game.getLicznikRundy())));
    	for(int i = 1; i < Main.game.getLiczbaGraczy(); i++)
    	{
    		newSummaryTemplate(Integer.toString(i+1) + ". " + Main.game.getPlayerNumber(i).getImie(), nameScoreboardLabel, summaryPlayersPane, i);
    		newSummaryTemplate(Integer.toString(Main.game.getPlayerNumber(i).getPunktyWRundzie(Main.game.getLicznikRundy())), pointsScoreboardLabel, summaryPointsPane, i);
    	}	
    	updateSummaryScoreboard();
    }
    private void updateSummaryScoreboard()
    {
    	int k = 0;
    	for(Player p : playersSet)
    	{
    		((Text)nameTexts.get(k)).setText(Integer.toString(k+1) + ". " + p.getImie());
    		((Text)pointsTexts.get(k)).setText(Integer.toString(p.getPunktyWRundzie(Main.game.getLicznikRundy())));
    		k++;
    	}
    }
    private void newSummaryTemplate(String text, Text pattern, Pane pane, int iteration)
    {
    	Text template = new Text();
    	pane.getChildren().add(template);
    	template.setFont(pattern.getFont());
    	template.setFill(pattern.getFill());
    	template.setWrappingWidth(pattern.getWrappingWidth());
    	template.setY(pattern.getY());
    	template.setX(pattern.getX());
    	template.setText(text);
    	template.setTextAlignment(TextAlignment.CENTER);
    	template.setTranslateY(25 + (25*iteration));
    	//return template;
    }
    public void setupScoreboard()
    {
    	upperScoreboardList = table1.getChildren();
    	lowerScoreboardList = table2.getChildren();
    }
    public void setupIDs()
    {
    	for(int i = 0; i < 5; i++)
    	{
    		diceList.get(i).setId("d" + i);
    		checkmarkList.get(i).setId("c" + i);
    	}
    }
    public void setDiceImages()
    {
    	for(int i = 0; i < 5; i++)
    	{
    		int tempValue = Main.game.getDiceManager().getKostki()[i].getValue();
    		((ImageView)diceList.get(i)).setImage(diceNumeredImages.get(tempValue - 1));
    	}
    }
    public void displayPossibleScore()
    {
    	for(int i = 6; i < 12; i++)
    	{
    		((Text)upperScoreboardList.get(i)).setText(Integer.toString(Main.game.getDiceCombinationManager().upperSectionPoints(i-5)));
    	}
    	((Text)lowerScoreboardList.get(7)).setText(Integer.toString(Main.game.getDiceCombinationManager().threeOfAKindPoints()));
    	((Text)lowerScoreboardList.get(8)).setText(Integer.toString(Main.game.getDiceCombinationManager().fourOfAKindPoints()));
    	((Text)lowerScoreboardList.get(9)).setText(Integer.toString(Main.game.getDiceCombinationManager().fullHousePoints()));
    	((Text)lowerScoreboardList.get(10)).setText(Integer.toString(Main.game.getDiceCombinationManager().smallStraightPoints()));
    	((Text)lowerScoreboardList.get(11)).setText(Integer.toString(Main.game.getDiceCombinationManager().largeStraightPoints()));
    	((Text)lowerScoreboardList.get(12)).setText(Integer.toString(Main.game.getDiceCombinationManager().yahtzeePoints()));
    	((Text)lowerScoreboardList.get(13)).setText(Integer.toString(Main.game.getDiceCombinationManager().chancePoints()));
    	setBusyCategoriesGreen();
    }
    private void setBusyCategoriesGreen()
    {
    	for(int i = 1; i <= 6; i++)
    	{
    		if(Main.game.getCurrentPIR().isCategoryBusy(i))
    		{
    			((Text)upperScoreboardList.get(i-1)).setFill(Color.GREEN);
    			((Text)upperScoreboardList.get(i+5)).setFill(Color.GREEN);
    		}
    	}
    	for(int i = 7; i <= 13; i++)
    	{
    		if(Main.game.getCurrentPIR().isCategoryBusy(i))
    		{
    			((Text)lowerScoreboardList.get(i-7)).setFill(Color.GREEN);
    			((Text)lowerScoreboardList.get(i)).setFill(Color.GREEN);
    		}
    	}
    }
    public void changePlayersName()
    {
    	playerNameText.setText(Main.game.getNextPlayersTurnString());
    }
    
    public void greyImage(ImageView img, boolean toGrey)
    {
    	ColorAdjust greyColor = new ColorAdjust();
    	double temp = toGrey ? -0.5 : 0.0;
    	greyColor.setBrightness(temp);
    	img.setEffect(greyColor);
    	img.setCache(toGrey);
    	CacheHint tempCache = toGrey ? CacheHint.SPEED : CacheHint.DEFAULT;
    	img.setCacheHint(tempCache);
    }
    
    @FXML
    void OMEnterImage(MouseEvent event) 
    {
    	if(diceClickable)
    	{
    		greyImage(((ImageView)event.getTarget()), true);
    	}
    }

    @FXML
    void OMExitImage(MouseEvent event) 
    {
    	if(diceClickable)
    	{
    		greyImage(((ImageView)event.getTarget()), false);
    	}
    }
    
    @FXML
    void OMEnterEsc(MouseEvent event) 
    {
    	greyImage(((ImageView)((Button)event.getTarget()).getGraphic()), true);
    }
    
    @FXML
    void OMExitEsc(MouseEvent event) 
    {
    	greyImage(((ImageView)((Button)event.getTarget()).getGraphic()), false);
    }
    
    @FXML
    void displayEscMenu()
    {
    	pauseMenuOn = true;
    	escMenu.setVisible(true);
    }
    
    @FXML
    void stopDisplayEscMenu()
    {
    	pauseMenuOn = false;
    	escMenu.setVisible(false);
    }
    
    @FXML
    void displayGameLog()
    {
    	gameLogOn = true;
    	gameLog.setVisible(true);
    }
    
    @FXML
    void stopDisplayGameLog()
    {
    	gameLogOn = false;
    	gameLog.setVisible(false);
    }
    
    @FXML
    void saveGame(ActionEvent event)
    {
    	GameSerializer.saveGame();
    }
    
    public boolean getPauseMenuOn()
    {
    	return pauseMenuOn;
    }
    
    public boolean getGameLogOn()
    {
    	return gameLogOn;
    }
    
    @FXML
    void loadMainMenu(ActionEvent event) 
    {
    	Main.game = new DiceGame();
    	Main.sceneHandler.setScene("./fxmls/MainMenu.fxml");
    }
    
    @FXML
    void exitProgram(ActionEvent event)
    {
    	Main.game.exitProgram();
    }
    
    public void summaryDisplayOff() 
    {
    	summaryScoreboard.setVisible(false);
    }

    public void summaryDisplayOn()
    {
    	updateSummaryScoreboard();
    	summaryScoreboard.setVisible(true);
    }
    
    @FXML
    void clickDice(MouseEvent event)
    {	
    	if(diceClickable)
		{
    		String id1 = ((Node)event.getTarget()).getId();
    		int numId = Integer.parseInt(id1.substring(1));
    		clickDiceAction(numId);
		}
    }

	public void clickDiceAction(int numId) 
	{
		if(!Main.game.getDiceManager().getDiceToReroll().contains(numId))
		{
			((ImageView)checkmarkList.get(numId)).setVisible(true);
			Main.game.getDiceManager().getDiceToReroll().add(numId);
		}
		else
		{
			((ImageView)checkmarkList.get(numId)).setVisible(false);
			Main.game.getDiceManager().getDiceToReroll().remove((Integer)numId);
		}
	}
    
    public void unselectAllDice()
    {
    	for(int i = 0;  i < 5; i++)
    	{
    		((ImageView)checkmarkList.get(i)).setVisible(false);
    		Main.game.getDiceManager().getDiceToReroll().remove((Integer)i);
    	}
    }
    
    @FXML
    void reroll(ActionEvent event)
    {
    	rerollAction();
    }

	public void rerollAction() 
	{
		Main.game.getDiceManager().rollSelectedDice();
    	setDiceImages();
    	displayPossibleScore();
    	unselectAllDice();
    	if(!Main.game.tura())
    	{
    		diceClickable = false;
    		goButton.setVisible(false);
    		rerollButton.setVisible(false);
    	}
    	Main.game.getGameLog().log
    	(
    		Main.game.getCurrentPlayer().getImie()
    		+ " rerolled dice with values "
    		+ Main.game.getDiceManager().getLogText()
    	);
    	Main.game.getGameLog().log
    	(
    		"Dice on the table now have values: "
    		+ Main.game.getDiceManager().displayAllDices()
    	);
	}
    
    @FXML
    public void confirmScore(ActionEvent event)
    {
    	confirmScoreAction();
    }

	public void confirmScoreAction() 
	{
		errorText1.setVisible(false);
    	errorText2.setVisible(false);
    	
    	if(Validation.categoryNumber(categoryTextField.getText()))
    	{
    		int catNumber = Integer.parseInt(categoryTextField.getText());
    		if(!Main.game.getCurrentPIR().isCategoryBusy(catNumber))
    		{
    			Main.game.calculateTurn(catNumber);
    			   			
    			if(Main.game.getGameOver())
    			{
    				//display scoreboard of all players, announce the winner etc
    				summaryDisplayOn();
    				
    				//display a button to close the program
    			}
    			else
    			{
    				//start next player's turn
    				loadGameWindow();
    			}
    		}
    		else
    		{
    			//Category is busy announcement
    			errorText1.setVisible(true);
    		}
    	}
    	else
    	{
    		//input invalid announcement
    		errorText2.setVisible(true);
    	}
	}
	
	public void typeCategoryAction(int n)
	{
		categoryTextField.setText(Integer.toString(n));
	}
}