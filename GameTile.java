import javafx.scene.image.*;

public class GameTile extends ImageView{
	private int id; //identifier for the tile
	
	//creates a new tile with given pixel size, image, and id
	public GameTile(double size, Image image, int id) {
		super();
		this.id = id;
		this.setImage(image);
		this.setFitWidth(size);
		this.setPreserveRatio(true);
		this.setSmooth(true);
		this.setCache(true);	
	}
	
	//returns the id
	public int getID() {
		return id;
	}
}
