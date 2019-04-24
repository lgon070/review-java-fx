import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AlienGameScreen {
	String font = "Armalite Rifle";
	Stage primaryStage;
	Button up,down,left,right,atUp, atDown,atLeft, atRight, confirm, restart, save;
	File chosenFile;
	Scanner in = new Scanner(System.in);
	PrintWriter pw;
	Label ammoCounter, mKilledp, tKilledp, creatureMsg, monMsg, trapMsg, creatStole, ammoFound, arano;
	
	static int ammoCount;
	int roomSize = 45;
	int spriteSize = 45;
	int numOfAmmo;
	int numOfCreatures;
	int numOfTraps;
	int gridSize;
	int gameVSize;
	boolean debug;

	boolean spaceEmpty[][];
	
	
	Pane gamePane = new Pane();
	Scene gameScene = new Scene(gamePane);
	Stage gameStage = new Stage();
	
	Pane buttonPane = new Pane();
	Scene buttonScene = new Scene(buttonPane, 250, 375);
	Stage buttonStage = new Stage();
	
	Pane gameOverPane = new Pane();
	Scene gameOverScene = new Scene(gameOverPane, 500, 500);
	Stage gameOverStage = new Stage();
	
	Pane gameWonPane = new Pane();
	Scene gameWonScene = new Scene(gameWonPane, 500,500);
	Stage gameWonStage = new Stage();
	
	
	
	Player mainPlayer;
	ShootAction shoot;
	EnemyMonster mainMonster;
	AmmoPouch exAmmo;
	FriendlyCreature friendCreat;
	Trap guTrap;
	ImageView shootMode;
	ImageView plModel;
	ImageView monModel;
	
	ArrayList<AmmoPouch> arrayAmmo= new ArrayList<AmmoPouch>(); 
	ArrayList<FriendlyCreature> arrayCreat = new ArrayList<FriendlyCreature>();
	ArrayList<Trap> arrayTrap = new ArrayList<Trap>();
	
	ArrayList<ImageView> ammoView= new ArrayList<ImageView>(); 
	ArrayList<ImageView> creatView = new ArrayList<ImageView>();
	ArrayList<ImageView> trapView = new ArrayList<ImageView>();
	
	ArrayList<Rectangle> arrayRec = new ArrayList<>();
	
	
	
	/**
	 * Constructs a new game.
	 * @param numOfAmmo number of extra ammunition.
	 * @param numOfCreatures number of creatures.
	 * @param numOfTraps number of traps.
	 * @param gridSize chosen size of either 5x5, 7x7, or 10x10.
	 * @param gameVSize the actual pixel size of the game.
	 * @param debug whether or not debug is on or off.
	 * @param primaryStage used when restarting a game.
	 */
	public AlienGameScreen(int numOfAmmo, int numOfCreatures, int numOfTraps, int gridSize, int gameVSize, boolean debug, Stage primaryStage) {
		this.numOfAmmo = numOfAmmo;
		this.numOfCreatures = numOfCreatures;
		this.numOfTraps = numOfTraps;
		this.gridSize = gridSize;
		this.gameVSize = gameVSize;
		this.debug = debug;
		this.primaryStage = primaryStage;
		
		ammoCount =3;

		spawnSpritesInCave();
		emptyStartCave();
		coverCave();
		Buttons();
		setWorldDialog();
		collisionMsgCheck();
		setMaxPaneSize();
		
		
		gameStage.setScene(gameScene);
		gameStage.setX(650);
		gameStage.setY(150);
		gameStage.show();
		
	}
	
	/**
	 * Constructs a saved game.
	 * @param aC number of ammunition left.
	 * @param nA number of extra ammunition.
	 * @param nC number of creatures.	
	 * @param nT number of traps.
	 * @param gS girdSize chosen size of either 5x5, 7x7, or 10x10.
	 * @param gVS the actual pixel size of the game. 
	 * @param pPX player position X.
	 * @param pPY player position Y.
	 * @param mPX monster position X.
	 * @param mPY monster position Y.
	 * @param pAmmo positions of the extra ammo crates.
 	 * @param pCreat positions of the creatures.
	 * @param pTrap positions of the traps.
	 * @param dbg whether debug is on or off
	 * @param primaryStage used to restarting a game.
	 */
	public AlienGameScreen(int aC, int nA, int nC, int nT, int gS, int gVS, int pPX, int pPY, int mPX, int mPY, 
			               ArrayList<Integer> pAmmo, ArrayList<Integer> pCreat, ArrayList<Integer> pTrap, boolean dbg, Stage primaryStage) {
		this.ammoCount = aC;
		this.numOfAmmo = nA;
		this.numOfCreatures = nC;
		this.numOfTraps = nT;
		this.gridSize = gS;
		this.gameVSize = gVS;
		
		mainPlayer = new Player(pPX, pPY);
		mainMonster = new EnemyMonster(mPX, mPY);
		
		for(int i = 0; i < pAmmo.size(); i+=2){
			exAmmo = new AmmoPouch(pAmmo.get(i), pAmmo.get(i+1));
			arrayAmmo.add((i/2), exAmmo);
			ammoView.add(arrayAmmo.get((i/2)).setAmmoModel(spriteSize));
		}
		
		for(int i = 0; i < pCreat.size(); i+=2) {
			friendCreat = new FriendlyCreature(pCreat.get(i), pCreat.get(i+1));
			arrayCreat.add((i/2), friendCreat);
			creatView.add(arrayCreat.get((i/2)).setCreatureModel(spriteSize));
		}
		
		for(int i = 0; i < pTrap.size(); i+=2) {
			guTrap = new Trap(pTrap.get(i), pTrap.get(i+1));
			arrayTrap.add((i/2), guTrap);
			trapView.add(arrayTrap.get((i/2)).setTrapModel(spriteSize));
		}
		
		this.debug = dbg;
		this.primaryStage = primaryStage;
		
		shoot = new ShootAction(0 , gVS);
		
		emptyStartCave();
		coverCave();
		Buttons();
		setWorldDialog();
		collisionMsgCheck();
		setMaxPaneSize();
		
		
		gameStage.setScene(gameScene);
		gameStage.setX(650);
		gameStage.setY(150);
		gameStage.show();
		
		
	}
	/**
	 * Sets the maximum width and height of the game screen.
	 */
	public void setMaxPaneSize() {
		gamePane.setMaxSize(gameVSize, gameVSize + 250); 
	}
	/**
	 * Closes game and button windows when the player has died.
	 */
	public void gameOver() {
		buttonStage.close();
		gameStage.close();
		gameOverLabelsButtons();
		gameOverStage.setScene(gameOverScene);
		gameOverStage.show();
	}
	/**
	 * Closes game and button windows when the player has killed the monster.
	 */
	public void gameWon() {
		buttonStage.close();
		gameStage.close();
		gameWonLabelsButtons();
		gameWonStage.setScene(gameWonScene);
		gameWonStage.show();
		
	}
	/**
	 * Calls the methods that updates the game.
	 */
	public void updateGame() {
		seppuku();
		collisionCheck();
		collisionMsgCheck();
		setupCaveRoom();
		coverCave();
	}
	
	/**
	 * Updates the player's position to allow the coverCave method to cover the cave around the player.
	 * @see coverCave()
	 */
	public void updatePlayer() {
		
		for(Rectangle rec: arrayRec) {
			gamePane.getChildren().remove(rec);
		}
		
		mainPlayer.setPlayerPosition(mainPlayer.getPlayerPositionX(), mainPlayer.getPlayerPositionY());
		
		gamePane.getChildren().remove(plModel);
		plModel = mainPlayer.setPlayerModel(spriteSize);
		gamePane.getChildren().add(plModel);
		
		arrayRec.clear();
		
		
	}
	/**
	 * Generates the empty cave room in which the game will be played.
	 */
	public void emptyStartCave() {
		Rectangle rec = new Rectangle();
		rec.setHeight(gameVSize);
		rec.setWidth(gameVSize);	
		gamePane.getChildren().add(rec);
		for(int i = 0; i <gameVSize; i += (roomSize +5) ) {
			for(int j = 0; j < gameVSize; j += (roomSize +5)) {
				EmptyCave  cave = new EmptyCave(i,j);
				ImageView caveM = cave.setCave(roomSize);
				gamePane.getChildren().add(caveM);
				
			}
		}
		
	 	Rectangle coverX = new Rectangle();
	 	coverX.setHeight(250);
		coverX.setWidth(gameVSize);
		coverX.setLayoutX(0);
		coverX.setLayoutY(gameVSize);
		coverX.setFill(Color.BLACK);
		coverX.toFront();
		gamePane.getChildren().add(coverX);
		
		setupCaveRoom();
	}
	/**
	 * Covers the cave around the player to ensure maximum immersion. 
	 */
	public void coverCave() {
		if(!debug) {
			
			updatePlayer();
			
			for(int i = 0; i < gameVSize; i += 50) {
				for(int j = 0; j < gameVSize; j += 50) {
					
					Rectangle coverC = new Rectangle(roomSize, roomSize);
					coverC.setX(j);
					coverC.setY(i);
					coverC.setFill(Color.BEIGE);
					
					if(j == mainPlayer.getPlayerPositionX() && i == (mainPlayer.getPlayerPositionY())){
							
						arrayRec.add(coverC);
						continue;
						
					}
					else {
						arrayRec.add(coverC);
						gamePane.getChildren().add(coverC);
					}
				}
			}
		}
	}
	/**
	 * Method used for updating the state of the game, changes the actual position of the Player and other sprites to simulate movement.
	 */
	public void setupCaveRoom() {
		
		
		
		gamePane.getChildren().remove(plModel);
		plModel = mainPlayer.setPlayerModel(spriteSize);
		gamePane.getChildren().add(plModel);
		
		gamePane.getChildren().remove(shootMode);
		shootMode = shoot.setShootModel(spriteSize);
		gamePane.getChildren().add(shootMode);
		
		gamePane.getChildren().remove(monModel);
		monModel = mainMonster.setEnemyModel(spriteSize);
		gamePane.getChildren().add(monModel);
		
		for(int i = 0; i < creatView.size(); i++) {
			gamePane.getChildren().remove(creatView.get(i));
			creatView.remove(i);
			creatView.add(i, arrayCreat.get(i).setCreatureModel(spriteSize));
			gamePane.getChildren().add(creatView.get(i));
		}
		for(int i = 0; i < ammoView.size(); i++ ) {
			gamePane.getChildren().remove(ammoView.get(i));
			ammoView.remove(i);
			ammoView.add(i, arrayAmmo.get(i).setAmmoModel(spriteSize));
			gamePane.getChildren().add(ammoView.get(i));
		}
		for(int i = 0; i < trapView.size(); i++ ) {
			gamePane.getChildren().remove(trapView.get(i));
			trapView.remove(i);
			trapView.add(i, arrayTrap.get(i).setTrapModel(spriteSize));
			gamePane.getChildren().add(trapView.get(i));
		}
		
		
}
/**
 * Spawns in the Player, Monster, and other game sprites.
 */
	public void spawnSpritesInCave() {
		spaceEmpty = new boolean[gameVSize][gameVSize];
		for(int i =0; i <gameVSize; i++) {
			for (int j =0; j <gameVSize; j++) {
				spaceEmpty[i][j] =true;
			}
		}
		
		findSpriteMax();
		
		mainPlayer = new Player(randomSpot(), randomSpot());
		spaceEmpty[mainPlayer.posY][mainPlayer.posX] = false;
		plModel = mainPlayer.setPlayerModel(spriteSize);
		gamePane.getChildren().add(plModel);
		
		shoot = new ShootAction(0,gameVSize);
		
		
		do{mainMonster = new EnemyMonster(randomSpot(), randomSpot());
		}while (spaceEmpty[mainMonster.posY][mainMonster.posX] == false);
		
		spaceEmpty[mainMonster.posY][mainMonster.posX] = false;
		monModel = mainMonster.setEnemyModel(spriteSize);
		gamePane.getChildren().add(monModel);
		
		for(int i = 0; i < numOfCreatures; i++) {
			do { friendCreat = new FriendlyCreature(randomSpot(), randomSpot());
			}while(spaceEmpty[friendCreat.posY][friendCreat.posX]==false);
			spaceEmpty[friendCreat.posY][friendCreat.posX] = false;
			arrayCreat.set(i, friendCreat);
			creatView.add(arrayCreat.get(i).setCreatureModel(spriteSize));
			gamePane.getChildren().add(creatView.get(i));
		}
		
		for(int i = 0; i < numOfAmmo; i++) {
			do { exAmmo = new AmmoPouch(randomSpot(), randomSpot());
			}while(spaceEmpty[exAmmo.posY][exAmmo.posX]==false);
			spaceEmpty[exAmmo.posY][exAmmo.posX] = false;
			arrayAmmo.set(i, exAmmo);
			ammoView.add(arrayAmmo.get(i).setAmmoModel(spriteSize));
			gamePane.getChildren().add(ammoView.get(i));
		}
		
		 for(int i =0 ; i < numOfTraps; i++) {
			 do { guTrap = new Trap(randomSpot(), randomSpot());
			 }while (spaceEmpty[guTrap.posY][guTrap.posX] ==false);
			 spaceEmpty[guTrap.posY][guTrap.posX] = false;
			 arrayTrap.set(i, guTrap);
			 trapView.add(arrayTrap.get(i).setTrapModel(spriteSize));
			 gamePane.getChildren().add(trapView.get(i));
		 }
		 	
			
		
	}
	/**
	 * Finds the maximum number (n) of sprites to generate an (n) size ArrayList of sprites. 
	 */
	public void findSpriteMax() {
		int cm = 0;
		
		if(numOfCreatures >= numOfTraps && numOfCreatures >= numOfAmmo) {
			cm = numOfCreatures;
		}
		else if(numOfTraps >= numOfCreatures && numOfTraps >= numOfAmmo) {
			cm = numOfTraps;
		}
		else if(numOfAmmo >= numOfCreatures && numOfAmmo >= numOfTraps) {
			cm = numOfAmmo;
		}
		
		for (int i =0; i < cm; i++) {
			arrayAmmo.add(new AmmoPouch(-100, -100));
			arrayCreat.add(new FriendlyCreature(-100, -100));
			arrayTrap.add(new Trap(-100, -100));
		}
	}
	
	/**
	 * Generates a random coordinate to place all of the game objects.
	 * @return pos random generated integer within the game boundaries.
	 */
	public int randomSpot() {
		int pos = (int)(Math.random() * gameVSize);
		pos -= pos%(roomSize + 5);
		return pos;
	}
	
	/**
	 * Places the stage of the buttons on the right side of the game area for easy viewing.
	 */
	public void setButtonStage() {
		buttonStage.setScene(buttonScene);
		if(gridSize == 5) {
			buttonStage.setX(900);
			buttonStage.setY(150);
		}
		else if (gridSize == 7) {
			buttonStage.setX(1000);
			buttonStage.setY(150);
		}
		else {
			buttonStage.setX(1150);
			buttonStage.setY(150);
		}
		buttonStage.show();
	}
	/**
	 * Creates the buttons used for the movement of the player and the attack options for the player.
	 */
	public void Buttons(){
		
		setButtonStage();
		
		 save = new Button("SAVE GAME");
		 up = new Button("UP");
		 down = new Button("DOWN");
	     left  = new Button("LEFT");
		 right = new Button("RIGHT");
		
		 atUp = new Button("AIM UP");
		 atDown = new Button("AIM DOWN");
		 atLeft = new Button("AIM LEFT");
		 atRight = new Button("AIM RIGHT");
		 confirm = new Button("SHOOT");
		
		save.setFont(Font.font(font, 15));
		up.setFont(Font.font(font, 15));
		down.setFont(Font.font(font, 15));
		left.setFont(Font.font(font, 15));
		right.setFont(Font.font(font, 15));
		atUp.setFont(Font.font(font, 10));
		atDown.setFont(Font.font(font, 10));
		atLeft.setFont(Font.font(font, 10));
		atRight.setFont(Font.font(font, 10));
		confirm.setFont(Font.font(font, 10));
		
		save.setLayoutX(gameVSize - 110);
		save.setLayoutY(gameVSize + 185);
		save.setMinSize(50, 50);
		gamePane.getChildren().add(save);
		
		up.setLayoutX(90);
		up.setLayoutY(50);
		up.setMinSize(60, 50);
		buttonPane.getChildren().add(up);
		
		down.setLayoutX(90);
		down.setLayoutY(100);
		down.setMinSize(50, 50);
		buttonPane.getChildren().add(down);
		
		left.setLayoutX(35);
		left.setLayoutY(100);
		left.setMinSize(55, 50);
		buttonPane.getChildren().add(left);
		
		right.setLayoutX(150);
		right.setLayoutY(100);
		right.setMinSize(50,50);
		buttonPane.getChildren().add(right);
		
		atUp.setLayoutX(80);
		atUp.setLayoutY(200);
		atUp.setMinSize(80,  50);
		buttonPane.getChildren().add(atUp);
		ButtonMovement();
		
		atDown.setLayoutX(80);
		atDown.setLayoutY(300);
		atDown.setMinSize(80,50);
		buttonPane.getChildren().add(atDown);
		
		atLeft.setLayoutX(15);
		atLeft.setLayoutY(250);
		atLeft.setMinSize(70,50);
		buttonPane.getChildren().add(atLeft);
		
		atRight.setLayoutX(160);
		atRight.setLayoutY(250);
		atRight.setMinSize(70, 50);
		buttonPane.getChildren().add(atRight);
		
		confirm.setLayoutX(80);
		confirm.setLayoutY(250);
		confirm.setMinSize(80, 50);
		buttonPane.getChildren().add(confirm);
		
		
		
	}
	/**
	 * Handles all player movement and player attack actions. Contains updateGame(), removeMsg(), and collisionCheck() 
	 * methods to update the state of the game. Also contains the save button to save game progress.
	 * @see updateGame()
	 * @see removeMsg()
	 * @see collisionCheck()
	 */
    public void ButtonMovement() {
    	
    	save.setOnAction(e->{
    		JFileChooser jfc = new JFileChooser(".");
    		int jfcUserOption = jfc.showOpenDialog(null);
    		
    		if (jfcUserOption == JFileChooser.APPROVE_OPTION) {
    			chosenFile = jfc.getSelectedFile();
    			try {
    				pw = new PrintWriter(chosenFile);
    			} catch (FileNotFoundException e2) {
    				e2.printStackTrace();
    			}
    			

        		pw.println(ammoCount);
        		pw.println(numOfAmmo);
        		pw.println(numOfCreatures);
        		pw.println(numOfTraps);
        		pw.println(gridSize);
        		pw.println(gameVSize);
        		pw.println(mainPlayer.getPlayerPositionX());
        		pw.println(mainPlayer.getPlayerPositionY());
        		pw.println(mainMonster.getEnemyPositionX());
        		pw.println(mainMonster.getEnemyPositionY());
        		
        		
        		for(int i = 0; i< numOfAmmo; i++) {
        			pw.println(arrayAmmo.get(i).getAmmoPositionX());
        			pw.println(arrayAmmo.get(i).getAmmoPositionY());
        		}
        		
        		for(int i = 0; i< numOfCreatures; i++) {
        			pw.println(arrayCreat.get(i).getCreaturePositionX());
        			pw.println(arrayCreat.get(i).getCreaturePositionY());
        		}
        		
        		for(int i = 0; i< numOfTraps; i++) {
        			pw.println(arrayTrap.get(i).getTrapPositionX());
        			pw.println(arrayTrap.get(i).getTrapPositionY());
        		}
        		
        		if(debug) {
        			pw.println(1);
        		}
        		else {
        			pw.println(0);
        		}
        		
        		pw.flush();
        		pw.close();
    		}
    	});
    
    	up.setOnAction(e->{
    		if(mainPlayer.getPlayerPositionY() > 0) {
    		mainPlayer.setPlayerPosition(mainPlayer.getPlayerPositionX(),mainPlayer.getPlayerPositionY() - (roomSize + 5));
    		shoot.setShootPosition(0, gameVSize);
    		removeMsg();
    		updateGame();
    		
    		}
    		
    	});
    	
    	down.setOnAction(e->{
    		if(mainPlayer.getPlayerPositionY() < (gameVSize-50)) {
    		mainPlayer.setPlayerPosition(mainPlayer.getPlayerPositionX(), mainPlayer.getPlayerPositionY() + (roomSize + 5));
    		shoot.setShootPosition(0, gameVSize);
    		removeMsg();
    		updateGame();
    		}
    	});
   
    	left.setOnAction(e->{
    		if(mainPlayer.getPlayerPositionX() > 0) {
    		mainPlayer.setPlayerPosition(mainPlayer.getPlayerPositionX() - (roomSize + 5), mainPlayer.getPlayerPositionY());
    		shoot.setShootPosition(0, gameVSize);
    		removeMsg();
    		updateGame();
    		}
    	});
    
    	right.setOnAction(e->{
    		if(mainPlayer.getPlayerPositionX() < gameVSize- 50) {
    		mainPlayer.setPlayerPosition(mainPlayer.getPlayerPositionX() + (roomSize + 5), mainPlayer.getPlayerPositionY());
    		shoot.setShootPosition(0, gameVSize);
    		removeMsg();
    		updateGame();
    		}
    	});
    	
    	atUp.setOnAction(e->{
    		if(mainPlayer.getPlayerPositionY() > 0) {
    			gamePane.getChildren().remove(shootMode);
    			shoot.setShootPosition(mainPlayer.getPlayerPositionX(), mainPlayer.getPlayerPositionY() - (roomSize+5));
    			shootMode = shoot.setShootModel(spriteSize);
    			collisionCheck();
    			gamePane.getChildren().add(shootMode);
    		}
    	});
    	
    	atDown.setOnAction(e->{
    		if(mainPlayer.getPlayerPositionY() < (gameVSize-50)) {
    			gamePane.getChildren().remove(shootMode);
    			shoot.setShootPosition(mainPlayer.getPlayerPositionX(), mainPlayer.getPlayerPositionY() + (roomSize + 5));
    			shootMode = shoot.setShootModel(spriteSize);
    			collisionCheck();
    			gamePane.getChildren().add(shootMode);
    		}
    	});
    	
    	atLeft.setOnAction(e->{
    		if(mainPlayer.getPlayerPositionX() > 0) {
    			gamePane.getChildren().remove(shootMode);
    			shoot.setShootPosition(mainPlayer.getPlayerPositionX() - (roomSize +5), mainPlayer.getPlayerPositionY());
    			shootMode = shoot.setShootModel(spriteSize);
    			collisionCheck();
    			gamePane.getChildren().add(shootMode);
    		}
    	});
    	
    	atRight.setOnAction(e->{
    		if(mainPlayer.getPlayerPositionX() < gameVSize - 50) {
    			gamePane.getChildren().remove(shootMode);
    			shoot.setShootPosition(mainPlayer.getPlayerPositionX() + (roomSize + 5), mainPlayer.getPlayerPositionY());
    			shootMode = shoot.setShootModel(spriteSize);
    			collisionCheck();
    			gamePane.getChildren().add(shootMode);
    		}
    	});
    	
    
    	confirm.setOnAction(e->{
    		if(ammoCount > 0) {
    		if(shoot.getShootPositionX() == mainMonster.getEnemyPositionX() && shoot.getShootPositionY() == mainMonster.getEnemyPositionY()) {
    				gamePane.getChildren().remove(monModel);
    				gameWon();
    				
    			}
    			else {	
    			moveMonster();
    			ammoCount--;
    			updateGame();
    			ammoDisplay(ammoCount);
    			}
    		}
    	});
    }
    /**
     * Creates the game over screen and implements the restart button for the game.
     */
    public void gameOverLabelsButtons() {
    	Image gameOverImage = new Image("images\\gameover.jpg");
    	ImageView goi = new ImageView(gameOverImage);
    	
    	goi.setFitHeight(500);
    	goi.setFitWidth(500);
    	gameOverPane.getChildren().add(goi);
    	
    	arano = new Label("YOU RAN OUT OF AMMO! \n "
    			+     "the alien took his \n chance and ate you");
		arano.setFont(Font.font(font, 30));
		arano.setLayoutX(85);
		arano.setLayoutY(390);
		arano.setTextFill(Color.WHITE);
		
		tKilledp = new Label("YOU STEPPED ON A TRAP!");
		tKilledp.setFont(Font.font(font, 30));
		tKilledp.setLayoutX(80);
		tKilledp.setLayoutY(415);
		tKilledp.setTextFill(Color.WHITE);
		
		mKilledp = new Label("THE ALIEN KILLED YOU!");
		mKilledp.setFont(Font.font(font, 30));
		mKilledp.setLayoutX(85);
		mKilledp.setLayoutY(415);
		mKilledp.setTextFill(Color.WHITE);
		
		
	
		restart = new Button("Restart");
		restart.setFont(Font.font(font, 30));
		restart.setLayoutX(180);
		restart.setLayoutY(35);
		
		gameOverPane.getChildren().add(restart);
		restart.setOnAction(e->{
			gameOverStage.close();
			primaryStage.show();
			
			
		});
	}
    /**
     * Creates the game won screen and implements the restart for the game.
     */
    public void gameWonLabelsButtons() {
    	Image gameWonImage = new Image("images\\won.png");
    	ImageView gwi = new ImageView(gameWonImage);
    	
    	gwi.setFitHeight(500);
    	gwi.setFitWidth(500);
    	gameWonPane.getChildren().add(gwi);
    	
    	Label youwin = new Label("YOU KILLED THE ALIEN!");
    	youwin.setLayoutX(90);
    	youwin.setLayoutY(350);
    	youwin.setFont(Font.font(font, 30));
    	youwin.setTextFill(Color.WHITE);
    	gameWonPane.getChildren().add(youwin);
    	
    	
    	restart = new Button("Restart");
		restart.setFont(Font.font(font, 30));
		restart.setLayoutX(180);
		restart.setLayoutY(35);
		gameWonPane.getChildren().add(restart);
		restart.setOnAction(e->{
			gameWonStage.close();
			primaryStage.show();
		});
    	
    	
    }
    
    /**
     * Checks the collision between the player and various other objects in the game. 
	 * Checks collision between Player and Monster.
	 * Checks collision between Player and Ammo.
	 * Checks collision between Player and Creature.
	 * Checks collision between Player and Trap.
	 * Checks collision between a Shoot Action and a Monster.
	 */
	public void collisionCheck() {
		//Player and Monster
		
		if(mainPlayer.getPlayerPositionX() == mainMonster.getEnemyPositionX() && mainPlayer.getPlayerPositionY() == mainMonster.getEnemyPositionY()) {
			gameOver();
			gameOverPane.getChildren().add(mKilledp);
		}
		
		// Player and Ammo
		for(int i = 0; i < numOfAmmo; i++) {
		if(mainPlayer.getPlayerPositionX() == arrayAmmo.get(i).getAmmoPositionX() && mainPlayer.getPlayerPositionY() == arrayAmmo.get(i).getAmmoPositionY()) {
			arrayAmmo.get(i).setPos(-100, -100);
			ammoCount++;
			ammoDisplay(ammoCount);
			ammoFound.setText("You found some Ammo!");
			}
		}
		
		//Player and Creatures
		for(int i = 0; i < numOfCreatures; i++) {
		if(mainPlayer.getPlayerPositionX() ==  arrayCreat.get(i).getCreaturePositionX()  && mainPlayer.getPlayerPositionY() == arrayCreat.get(i).getCreaturePositionY()) {
			ammoCount--;
			ammoDisplay(ammoCount);
			creatStole.setText("A creature stole ammo!");
			seppuku();
		}
		}
		
		//Player and Trap
		for(int i =0; i < numOfTraps; i++) {
		if(mainPlayer.getPlayerPositionX() == arrayTrap.get(i).getTrapPositionX() && mainPlayer.getPlayerPositionY() == arrayTrap.get(i).getTrapPositionY()) {
			gameOver();
			gameOverPane.getChildren().add(tKilledp);
			}
		}
		
	}
	/**
	 * Checks the collision of nearby objects to warn of hint the player of the surroundings.
	 * Checks the proximity of Player and Creature.
	 * Checks the proximity of Player and Trap.
	 * Checks the proximity of Player and Monster.
	 */
	public void collisionMsgCheck(){
		//Player and Monster
		if(mainPlayer.getPlayerPositionX() - 50 == mainMonster.getEnemyPositionX() && mainPlayer.getPlayerPositionY() == mainMonster.getEnemyPositionY()
		|| mainPlayer.getPlayerPositionX() + 50 == mainMonster.getEnemyPositionX() && mainPlayer.getPlayerPositionY() == mainMonster.getEnemyPositionY()  
		|| mainPlayer.getPlayerPositionY() - 50 == mainMonster.getEnemyPositionY() && mainPlayer.getPlayerPositionX() == mainMonster.getEnemyPositionX()
		|| mainPlayer.getPlayerPositionY() + 50 == mainMonster.getEnemyPositionY() && mainPlayer.getPlayerPositionX() == mainMonster.getEnemyPositionX()) {
				monMsg.setText("You hear a low growl nearby!");
	    	}
		
		//Player and Creature
		for(int i = 0; i < numOfCreatures; i++) {
		if(mainPlayer.getPlayerPositionX() - 50 == arrayCreat.get(i).getCreaturePositionX() && mainPlayer.getPlayerPositionY() == arrayCreat.get(i).getCreaturePositionY() 
		|| mainPlayer.getPlayerPositionX() + 50 == arrayCreat.get(i).getCreaturePositionX() && mainPlayer.getPlayerPositionY() == arrayCreat.get(i).getCreaturePositionY() 
		|| mainPlayer.getPlayerPositionY() - 50 == arrayCreat.get(i).getCreaturePositionY() && mainPlayer.getPlayerPositionX() == arrayCreat.get(i).getCreaturePositionX()
		|| mainPlayer.getPlayerPositionY() + 50 == arrayCreat.get(i).getCreaturePositionY() && mainPlayer.getPlayerPositionX() == arrayCreat.get(i).getCreaturePositionX()) {
			   creatureMsg.setText("You hear a slight ruffle nearby!");
			}
		}
		//Player and Trap
		for(int i = 0; i < numOfTraps; i++) {
			if(mainPlayer.getPlayerPositionX() - 50 == arrayTrap.get(i).getTrapPositionX() && mainPlayer.getPlayerPositionY() == arrayTrap.get(i).getTrapPositionY() 
			|| mainPlayer.getPlayerPositionX() + 50 == arrayTrap.get(i).getTrapPositionX() && mainPlayer.getPlayerPositionY() == arrayTrap.get(i).getTrapPositionY() 
			|| mainPlayer.getPlayerPositionY() - 50 == arrayTrap.get(i).getTrapPositionY() && mainPlayer.getPlayerPositionX() == arrayTrap.get(i).getTrapPositionX()
			|| mainPlayer.getPlayerPositionY() + 50 == arrayTrap.get(i).getTrapPositionY() && mainPlayer.getPlayerPositionX() == arrayTrap.get(i).getTrapPositionX()) {
				trapMsg.setText("You hear a subtle tud nearby!");
				}
			}
		
		
		
		
	}
	
	/**
	 * Displays the amount of ammunition the player has left.
	 * @param x integer which holds the number of ammo.
	 */
	public void ammoDisplay(int x) {
		if(x > 0) {
			ammoCounter.setText("Ammunition: " + x);
		}
		else if(x <= 0) {
			ammoCounter.setText("Ammunition:  0");
		}
	}
	/**
	 * Creates the world dialog box that gives hints to the player.
	 */
	public void setWorldDialog() {
		int fontSize;
		
		if(gridSize == 5) {
			fontSize = 10;
		}
		else if(gridSize == 7) {
			fontSize = 15;
		}
		else {
			fontSize = 20;
		}
		
		creatStole = new Label("");
		creatStole.setLayoutX(35);
		creatStole.setLayoutY(gameVSize);
		creatStole.setFont(Font.font(font, fontSize));
		creatStole.setTextFill(Color.WHITE);
		gamePane.getChildren().add(creatStole);
		
		ammoFound = new Label("");
		ammoFound.setLayoutX(35);
		ammoFound.setLayoutY(gameVSize);
		ammoFound.setFont(Font.font(font, fontSize));
		ammoFound.setTextFill(Color.WHITE);
		gamePane.getChildren().add(ammoFound);
		
		monMsg = new Label("");
		monMsg.setLayoutX(35);
    	monMsg.setLayoutY(gameVSize + 50);
    	monMsg.setTextFill(Color.WHITE);
    	monMsg.setFont(Font.font(font, fontSize));
    	gamePane.getChildren().add(monMsg);	
    	
    	trapMsg = new Label("");
    	trapMsg.setLayoutX(35);
    	trapMsg.setLayoutY(gameVSize + 100);
    	trapMsg.setTextFill(Color.WHITE);
    	trapMsg.setFont(Font.font(font, fontSize));
    	gamePane.getChildren().add(trapMsg);
    	
    	creatureMsg = new Label("");
    	creatureMsg.setLayoutX(35);
    	creatureMsg.setLayoutY(gameVSize + 150);
    	creatureMsg.setTextFill(Color.WHITE);
    	creatureMsg.setFont(Font.font(font, fontSize));
    	gamePane.getChildren().add(creatureMsg);
    	
    	ammoCounter = new Label("Ammunition: " + ammoCount);
		ammoCounter.setLayoutX(15);
		ammoCounter.setLayoutY(gameVSize + 200);
		ammoCounter.setFont(Font.font(font, fontSize +5));
		ammoCounter.setTextFill(Color.WHITE);
		gamePane.getChildren().add(ammoCounter);
    	
    
    	
	}
	/**
	 * Removes the hints and warnings given to the player every time the player moves.
	 */
	public void removeMsg() {
		trapMsg.setText("");
		creatureMsg.setText("");
		monMsg.setText("");
		ammoFound.setText("");
		creatStole.setText("");
	
	}
	/**
	 * Set the monster to an adjacent position when the player fires but misses the alien.
	 */
	public void moveMonster() {
		int randomSpot2 = 1 + (int)(Math.random() * (4 - 1 + 1));
		switch (randomSpot2) {
		case 1:
			if(mainMonster.getEnemyPositionX() > (roomSize +5)) {
				mainMonster.setEnemyPosition(mainMonster.getEnemyPositionX() - 50, mainMonster.getEnemyPositionY());
				setupCaveRoom();
				collisionCheck();
				coverCave();
			}
			break;
		case 2:
			if(mainMonster.getEnemyPositionY() > (roomSize + 5)) {
				mainMonster.setEnemyPosition(mainMonster.getEnemyPositionX(), mainMonster.getEnemyPositionY() -50);
				setupCaveRoom();
				collisionCheck();
				coverCave();
			}
			break;
		case 3:
			if(mainMonster.getEnemyPositionX()  < gameVSize - (roomSize + 5)) {
				mainMonster.setEnemyPosition(mainMonster.getEnemyPositionX() + 50, mainMonster.getEnemyPositionY());
				setupCaveRoom();
				collisionCheck();
				coverCave();
			}
			break;
		case 4:
			if(mainMonster.getEnemyPositionY() < gameVSize - (roomSize + 5)) {
				mainMonster.setEnemyPosition(mainMonster.getEnemyPositionX(), mainMonster.getEnemyPositionY() +50);
				setupCaveRoom();
				collisionCheck();
				coverCave();
			}
			break;
			
		}
	}
	/**
	 * Checks to see if the player still has ammo left to kill alien if not the player 
	 * commits seppuku as he has disgraced the human race.
	 */
	public void seppuku() {
		if(ammoCount <= 0) {
			gameOver();	
			gameOverPane.getChildren().add(arano);
		}
		
	}
	
	public static void main(String[] args) {
		
	}

}
