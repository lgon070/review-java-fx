import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Player {
	
	ImageView playerModel;
	int posX, posY;
	/**
	 * Constructor of the player.
	 * @param posX position X of the player.
	 * @param posY position Y of the player.
	 */
	public Player(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		
	}
	/**
	 * Sets the position of the player.
	 * @param posX position X of the player.
	 * @param posY position Y of the player.
	 */
	public void setPlayerPosition(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	/**
	 * Returns the position X of the player.
	 * @return posX positionX of the player.
	 */
	public int getPlayerPositionX(){
		return this.posX;
	}
	/**
	 * Returns the position Y of the player.
	 * @return posY position Y of the player.
	 */
	public int getPlayerPositionY() {
		return this.posY;
	}
	/**
	 * Sets the model of the player.
	 * @param size pixel size of the model. 
	 * @return playerModel model of the player.
	 */
	public ImageView setPlayerModel(int size) {
		Image playerpicture = new Image("images/playermodel.png");
		ImageView playerModel = new ImageView(playerpicture);
		playerModel.setFitHeight(size);
		playerModel.setFitWidth(size);
		playerModel.setX(this.posX);
		playerModel.setY(this.posY);
		return playerModel;
		
	}
	
	
	public static void main(String[] args) {
		

	}


}