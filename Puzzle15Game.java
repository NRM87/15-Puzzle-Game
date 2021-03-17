import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javafx.geometry.Pos;
import java.net.URL;
public class Puzzle15Game extends Application{
	//Warning: Node sizes are meant for game board size of 4
	public static final int GAME_BOARD_SIZE = 4; 
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		int totalTiles = GAME_BOARD_SIZE*GAME_BOARD_SIZE; 
		
		//creates title image
		URL titleImage = Puzzle15Game.class.getResource("Textures/Title.png");
		ImageView titleTile = new ImageView("" + titleImage);
		titleTile.setFitWidth(200);
		titleTile.setPreserveRatio(true);
		titleTile.setSmooth(true);
		titleTile.setCache(true);	
		StackPane title = new StackPane();
		title.getChildren().add(titleTile);
		title.setAlignment(Pos.CENTER);

		//creates menu buttons
		String idleSkin = "-fx-background-color: #ffec9d; -fx-font: 16pt \"Times New Roman\";";
		String hoverSkin = "-fx-background-color: #ebc54e; -fx-font: 16pt \"Times New Roman\";";
		Button shuffle = new Button("Shuffle"); 
		shuffle.setPrefWidth(100);
		shuffle.setPrefHeight(100);
		shuffle.setFocusTraversable(false);
		shuffle.setStyle(idleSkin);
		shuffle.setOnMouseEntered(e -> shuffle.setStyle(hoverSkin));
        shuffle.setOnMouseExited(e -> shuffle.setStyle(idleSkin));
		Button quit = new Button("Quit");
		quit.setPrefWidth(100);
		quit.setPrefHeight(100);
		quit.setFocusTraversable(false);
		quit.setStyle(idleSkin);
		quit.setOnMouseEntered(e -> quit.setStyle(hoverSkin));
		quit.setOnMouseExited(e -> quit.setStyle(idleSkin));
		
		//creates the menu
		HBox menu = new HBox();
		menu.setPrefWidth(GAME_BOARD_SIZE*100);
		menu.setPrefHeight(100);
		menu.setAlignment(Pos.CENTER);
		menu.getChildren().addAll(shuffle, title, quit);
		menu.setStyle("-fx-background-color: #ffec9d");
		
		//creates game board
		Image[] images = new Image[totalTiles];
		for (int i = 0; i < totalTiles-1; i++) {
			URL tileImage = Puzzle15Game.class.getResource("Textures/" + (i+1) + ".png");
			images[i] = new Image("" + tileImage);
		}
		URL blankImage = Puzzle15Game.class.getResource("Textures/blank.png");
		images[totalTiles-1] = new Image("" + blankImage);
		GameBoard board = new GameBoard(GAME_BOARD_SIZE, images);
		board.shuffle();
		
		//creates win screen
		URL tileImage = Puzzle15Game.class.getResource("Textures/win.png");
		ImageView winScreen = new ImageView("" + tileImage);
		winScreen.setFitWidth(400);
		winScreen.setPreserveRatio(true);
		winScreen.setSmooth(true);
		winScreen.setCache(true);	
		
		//creates border rectangles
		Rectangle borderH1 = new Rectangle(GAME_BOARD_SIZE*100, 25, Color.MAROON);
		Rectangle borderH2 = new Rectangle(GAME_BOARD_SIZE*100 + 50, 25, Color.MAROON);
		Rectangle borderH3 = new Rectangle(GAME_BOARD_SIZE*100 + 50, 25, Color.MAROON);
		Rectangle borderV1 = new Rectangle(25, GAME_BOARD_SIZE*100 + 125, Color.MAROON);
		Rectangle borderV2 = new Rectangle(25, GAME_BOARD_SIZE*100 + 125, Color.MAROON);
		
		//arranges the menu and board
		VBox sceneCenter = new VBox();
		sceneCenter.setAlignment(Pos.CENTER);
		sceneCenter.setPrefHeight(GAME_BOARD_SIZE*100 + 100);
		sceneCenter.setPrefWidth(GAME_BOARD_SIZE*100);
		sceneCenter.getChildren().addAll(menu,borderH1,board);
		
		//arranges the scene center and border
		BorderPane scenePanel = new BorderPane();
		scenePanel.setCenter(sceneCenter);
		scenePanel.setLeft(borderV1);
		scenePanel.setRight(borderV2);
		scenePanel.setTop(borderH2);
		scenePanel.setBottom(borderH3);
		
		//sets the scene
		primaryStage.setTitle("15 Puzzle Game");
		Scene scene = new Scene(scenePanel, GAME_BOARD_SIZE*100 + 50, GAME_BOARD_SIZE*100 + 175, Color.rgb(255, 236, 157));
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);//perhaps find a way to resize window along with tiles later
		primaryStage.show();
        
		//sets button and keyboard events
		quit.setOnAction(e -> primaryStage.close());
		shuffle.setOnAction(e -> shuffle(sceneCenter, board, winScreen));
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
            	if (ke.getCode() == KeyCode.UP || ke.getCode() == KeyCode.W) {
                	board.up();
                	checkWin(sceneCenter, board, winScreen); 
            	}
            	if (ke.getCode() == KeyCode.DOWN || ke.getCode() == KeyCode.S) {
                	board.down();
                	checkWin(sceneCenter, board, winScreen);
            	}
            	if (ke.getCode() == KeyCode.RIGHT || ke.getCode() == KeyCode.D) {
                	board.right();
                	checkWin(sceneCenter, board, winScreen);
            	}
            	if (ke.getCode() == KeyCode.LEFT || ke.getCode() == KeyCode.A) {
                	board.left();
                	checkWin(sceneCenter, board, winScreen);
            	}
            	if (ke.getCode() == KeyCode.R) {
            		shuffle(sceneCenter, board, winScreen);
            	}
            	if (ke.getCode() == KeyCode.ESCAPE) {
            		primaryStage.close();
            	}
            }
        });
	}
	
	//displays win screen if board is completed
	public static void checkWin(VBox sceneCenter, GameBoard board, Node winScreen) {
		if (sceneCenter.getChildren().contains(board) && board.completed()) {
			sceneCenter.getChildren().remove(board);
			sceneCenter.getChildren().add(winScreen);
		}
	}
	
	//resets board
	public static void shuffle(VBox sceneCenter, GameBoard board, Node winScreen) {
		board.shuffle();
    	if (sceneCenter.getChildren().contains(winScreen)) {
        	sceneCenter.getChildren().remove(winScreen);
        	sceneCenter.getChildren().add(board);
    	}
	}
}
