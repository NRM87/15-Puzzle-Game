import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import java.util.Random;
import java.util.ArrayList;

public class GameBoard extends GridPane{
	private GameTile[][] board; //keeps track of tiles
	private int emptyX;         //keeps track of empty tile x
	private int emptyY;         //keeps track of empty tile y
	private int size;           //board size
	
	public GameBoard() {
		this(0);
	}
	
	//creates a blank board of specified size
	public GameBoard(int size) {
		this.size = size;
		board = new GameTile[size][size];
	}
	
	//creates a board filled with tiles created with the given images
	public GameBoard(int size, Image[] images) {
		this(size);
		loadTextures(images);
	}
	
	//adds a tile to the board
	public void add(GameTile child, int columnIndex, int rowIndex) {
		super.add(child, columnIndex, rowIndex);
		board[columnIndex][rowIndex] = child;
	}
	
	//loads an array of images into the game board
	public void loadTextures(Image[] images) {
		int totalTiles = size*size;
		if (images.length > totalTiles) throw new IllegalArgumentException("Too many textures loaded");
		for (int i = 0; i < totalTiles-1; i++) {
			add(new GameTile(100,images[i],i+1),i%size,i/size);
		}
		add(new GameTile(100,images[totalTiles-1],totalTiles),size-1,size-1);
		emptyX = size-1;
		emptyY = size-1;
	}
	
	//shuffles the board
	public void shuffle() {
		int totalTiles = size*size;
		ArrayList<GameTile> tiles = new ArrayList<>();
		for (int i = 0; i < totalTiles; i++) {
			GameTile tile = board[i%size][i/size];
			this.getChildren().remove(tile);    
			tiles.add(tile);
		}	
		Random random = new Random();
		for (int i = 0; i < totalTiles; i++) {
			GameTile tile = tiles.remove(random.nextInt(tiles.size())); 
			this.add(tile,i%size,i/size);
			if (tile.getID()==totalTiles) {
				emptyX = i%size;
				emptyY = i/size;
			}
		}
	}
	
	//moves a tile down into the blank space
	public void down() {
		if (indexCheck(emptyY-1)) {
			GameTile blank = board[emptyX][emptyY];
			GameTile target = board[emptyX][emptyY-1];
			this.getChildren().remove(blank);
			this.getChildren().remove(target);
			this.add(target, emptyX, emptyY);
			this.add(blank, emptyX, emptyY-1);    
			emptyY--;
		}
	}
	
	//moves a tile up into the blank space
	public void up() {
		if (indexCheck(emptyY+1)) {
			GameTile blank = board[emptyX][emptyY];
			GameTile target = board[emptyX][emptyY+1];
			this.getChildren().remove(blank);
			this.getChildren().remove(target);
			this.add(target, emptyX, emptyY);
			this.add(blank, emptyX, emptyY+1);
			emptyY++;
		}
	}
	
	//moves a tile right into the blank space
	public void right() {
		if (indexCheck(emptyX-1)) {
			GameTile blank = board[emptyX][emptyY];
			GameTile target = board[emptyX-1][emptyY];
			this.getChildren().remove(blank);
			this.getChildren().remove(target);
			this.add(target, emptyX, emptyY);
			this.add(blank, emptyX-1, emptyY);
			emptyX--;
		}
	}
	
	//moves a tile left into the blank space
	public void left() {
		if (indexCheck(emptyX+1)) {
			GameTile blank = board[emptyX][emptyY];
			GameTile target = board[emptyX+1][emptyY];
			this.getChildren().remove(blank);
			this.getChildren().remove(target);
			this.add(target, emptyX, emptyY);
			this.add(blank, emptyX+1, emptyY);
			emptyX++;
		}
	}
	
	//checks if the board is completed
	public boolean completed() {
		int prev = 0;
		for (int i = 0; i < size*size; i++) {
			int id = board[i%size][i/size].getID();
			if (id <= prev) return false;
			prev = id;
		}
		return true;
	}
	
	//presents the board in string form
	public String print() {
		int totalTiles = size*size;
		int formatSize = ("" + totalTiles).length();
		String str = "";
		for (int i = 0; i < totalTiles; i++) {
			int id = board[i%size][i/size].getID();
			str += String.format("[%" + formatSize + "s]", (id == totalTiles)?" ":("" + id));
			if (i%size == size-1) str += "\n";
		}
		return str;
	}
	
	//checks if the tile can move to a certain index
	public boolean indexCheck(int index) {
		return (index < size && index >= 0);
	}
}
