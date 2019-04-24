import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class MainMenu extends Application {
	File chosenInFile;
	Scanner in = new Scanner(System.in);
	boolean debug = false;
	int gameViewSize;
	int buttonSize = 100;
	String font = "Armalite Rifle";
	int screenSize;
	Pane mainPane = new Pane();
	Scene primaryScene = new Scene(mainPane, 500, 500);
	int gridSizeChoice;
	int numberOfExtraAmmo;
	int numberOfCreatures;
	int numberOfTraps;
	
	
	/**
	 * Method that starts the start menu of the game.
	 */
	public void start(Stage primaryStage) throws Exception {
		StartMenu(primaryStage);
		primaryStage.setScene(primaryScene);
		primaryStage.show();

	}
	
	/**
	 * Method that builds all the labels, text fields, and radio buttons for the start menu.
	 * @param primaryStage passes primary stage to build menu.
	 */
	public void StartMenu(Stage primaryStage) {
		Image startImage = new Image("images/startscreen.jpg");
		ImageView startScreen = new ImageView(startImage);
		startScreen.setFitHeight(500);
		startScreen.setFitWidth(500);
		mainPane.getChildren().add(startScreen);
		
		Label gamename = new Label("Alien Hunter");
		gamename.setFont(Font.font(font, 60));
		gamename.setLayoutX(50);
		gamename.setLayoutY(35);
		gamename.setTextFill(Color.WHITE);
		mainPane.getChildren().add(gamename);
		
		Label gridChoice = new Label("Grid Size");
		gridChoice.setFont(Font.font(font, 30));
		gridChoice.setLayoutX(195);
		gridChoice.setLayoutY(280);
		gridChoice.setTextFill(Color.WHITE);
		mainPane.getChildren().add(gridChoice);
		
		Label gameNumSet = new Label( " Ammo: " + 
									"\n" +
								    "\n Creatures: " +
									"\n"+
								    "\n Traps: ");  
		gameNumSet.setFont(Font.font(font, 20));
		gameNumSet.setLayoutX(15);
		gameNumSet.setLayoutY(380);
		gameNumSet.setTextFill(Color.WHITE);
		mainPane.getChildren().add(gameNumSet);
		
		TextField ammoC = new TextField();
		ammoC.setFont(Font.font(font, 15));
		ammoC.setLayoutX(90);
		ammoC.setLayoutY(375);
		ammoC.setText("2");
		ammoC.setMaxWidth(50);;
		mainPane.getChildren().add(ammoC);
		
		TextField creatC = new TextField();
		creatC.setFont(Font.font(font, 15));
		creatC.setLayoutX(150);
		creatC.setLayoutY(420);
		creatC.setText("3");
		creatC.setMaxWidth(50);
		mainPane.getChildren().add(creatC);
		
		TextField trapC = new TextField();
		trapC.setFont(Font.font(font, 15));
		trapC.setLayoutX(110);
		trapC.setLayoutY(465);
		trapC.setText("3");
		trapC.setMaxWidth(50);
		mainPane.getChildren().add(trapC);
		
		Label mustSelect = new Label ("You Must Select a Grid Size!");
		mustSelect.setFont(Font.font(font,15));
		mustSelect.setLayoutX(175);
		mustSelect.setLayoutY(355);
		mustSelect.setTextFill(Color.RED);
		
		Label numError = new Label ("- Each value must be greater than \n"
		                   		  + "  0 and cannot exceed 5 "
								  + "\n- Must be in Interger form");
		numError.setFont(Font.font(font, 15));
		numError.setLayoutX(200);
		numError.setLayoutY(390);
		numError.setTextFill(Color.RED);

		RadioButton grid5x5 = new RadioButton("5x5");
		grid5x5.setFont(Font.font(font, 15));
		grid5x5.setLayoutX(115);
		grid5x5.setLayoutY(325);
		grid5x5.setTextFill(Color.WHITE);
		mainPane.getChildren().add(grid5x5);
		grid5x5.setOnAction(e->{
			mainPane.getChildren().remove(mustSelect);
			gameViewSize = 245;
			gridSizeChoice = 5;
		});
		
		
		RadioButton grid7x7 = new RadioButton("7x7");
		grid7x7.setFont(Font.font(font, 15));
		grid7x7.setLayoutX(235);
		grid7x7.setLayoutY(325);
		grid7x7.setTextFill(Color.WHITE);
		mainPane.getChildren().add(grid7x7);
		grid7x7.setOnAction(e->{
			mainPane.getChildren().remove(mustSelect);
			gameViewSize = 345;
			gridSizeChoice = 7;
		});
		
		
		RadioButton grid10x10 = new RadioButton("10x10");
		grid10x10.setFont(Font.font(font, 15));
		grid10x10.setLayoutX(350);
		grid10x10.setLayoutY(325);	
		grid10x10.setTextFill(Color.WHITE);
		mainPane.getChildren().add(grid10x10);
		grid10x10.setOnAction(e->{
			mainPane.getChildren().remove(mustSelect);
			gameViewSize = 495;
			gridSizeChoice = 10;
			
		});
		RadioButton debugMode = new RadioButton("Debug Mode");
		debugMode.setFont(Font.font(font,10));
		debugMode.setLayoutX(390);
		debugMode.setLayoutY(450);
		debugMode.setTextFill(Color.WHITE);
		mainPane.getChildren().add(debugMode);
		debugMode.setOnAction(e->{
			if(debugMode.isSelected()){
				debug = true;
			}
			else {
				debug = false;
			}
		});
		
		
		ToggleGroup gridToggle = new ToggleGroup();
		gridToggle.getToggles().add(grid10x10);
		gridToggle.getToggles().add(grid7x7);
		gridToggle.getToggles().add(grid5x5);
		
		
		
		
		
		Button startGame = new Button("Start Game");
		startGame.setFont(Font.font(font, 30));
		startGame.setLayoutX(155);
		startGame.setLayoutY(120);
		mainPane.getChildren().add(startGame);
		startGame.setOnAction(e->{
			mainPane.getChildren().remove(numError);
			mainPane.getChildren().remove(mustSelect);
			numberOfExtraAmmo = checkNum(ammoC);
			numberOfCreatures = checkNum(creatC);
			numberOfTraps = checkNum(trapC);
			if(numberOfExtraAmmo >= 1 && numberOfCreatures >= 1 && numberOfTraps >= 1 && 
	           numberOfExtraAmmo <= 5 && numberOfCreatures <= 5 && numberOfTraps <= 5 ) {
					if(grid5x5.isSelected() || grid7x7.isSelected() || grid10x10.isSelected()) {
						AlienGameScreen ags = new AlienGameScreen(numberOfExtraAmmo,numberOfCreatures , numberOfTraps, gridSizeChoice, gameViewSize, debug, primaryStage);
						primaryStage.close();
					}
					else {
						mainPane.getChildren().add(mustSelect);
					}
			}
			else {
				mainPane.getChildren().add(numError);
			}
			
		});
		
		Button loadGame = new Button("Load Game");
		loadGame.setFont(Font.font(font, 30));
		loadGame.setLayoutX(165);
		loadGame.setLayoutY(200);
		loadGame.setMinWidth(180);
		mainPane.getChildren().add(loadGame);
		loadGame.setOnAction(e->{
			JFileChooser jfc = new JFileChooser(".");
    		int jfcUserOption = jfc.showOpenDialog(null);
    		
    		if (jfcUserOption == JFileChooser.APPROVE_OPTION) {
    			chosenInFile = jfc.getSelectedFile();
    			
    			try {
    				in = new Scanner(chosenInFile);
    			} catch (FileNotFoundException e1) {
    				e1.printStackTrace();
    			}
    			
    			int aC, nA, nC, nT, gS, gVS, pPosX, pPosY, mPosX, mPosY;
        		ArrayList<Integer> posAmmoList = new ArrayList<>();
        		ArrayList<Integer> posCreatList = new ArrayList<>();
        		ArrayList<Integer> posTrapList = new ArrayList<>();
    			int dbg;
    			boolean dbg2;
        		
        		aC = in.nextInt();
        		nA = in.nextInt();
        		nC = in.nextInt();
        		nT = in.nextInt();
        		gS = in.nextInt();
        		gVS = in.nextInt();
        		pPosX = in.nextInt();
        		pPosY = in.nextInt();
        		mPosX = in.nextInt();
        		mPosY = in.nextInt();
        		
        		for(int i = 0; i < nA; i++) {
        			posAmmoList.add(in.nextInt());
        			posAmmoList.add(in.nextInt());
        		}
        		
        		for(int i =0; i < nC; i++) {
        			posCreatList.add(in.nextInt());
        			posCreatList.add(in.nextInt());
        		}
        		
        		for(int i = 0; i < nT; i++) {
        			posTrapList.add(in.nextInt());
        			posTrapList.add(in.nextInt());
        		}
        		
        		dbg = in.nextInt();
        		if(dbg == 1) {
        			dbg2 = true;
        		}
        		else  {
        			dbg2 = false;
        		}
        		
        			
        		
        		AlienGameScreen sags = new AlienGameScreen(aC, nA, nC, nT, gS, gVS, pPosX, pPosY, mPosX, mPosY, posAmmoList, posCreatList,
        									posTrapList, dbg2, primaryStage);
        		primaryStage.close();
    		
    		}	
		});
		
	}
	
	/**
	 * Makes sure that the given ammo, creatures, and traps are in integer form.
	 * @param text grabs the player given text.
	 * @return -1 if the text field is not an integer, returns the player given value if it a integer. 
	 */
	public int checkNum(TextField text) {
		int num = -1;
		
		try {
			num = Integer.parseInt(text.getText());
		}catch (Exception e2) {
			
		}
		return num;
		
	}
	
	public static void main(String[] WrongLeverCronk) {
		launch(WrongLeverCronk);
	}

}
