import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Trap {
	int posX, posY;
	
	/**
	 * Constructor of the trap.
	 * @param posX position X of the trap.
	 * @param posY position Y of the trap.
	 */
	public Trap (int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	/**
	 * Sets position of the trap.
	 * @param posX position X of the trap.
	 * @param posY position Y of the trap.
	 */
	public void setTrapPosition(int posX, int posY) {
		this.posX = posY;
		this.posY = posY;
	}
	/**
	 * Returns position X of the trap.
	 * @return posX position X of the trap.
	 */
	public int getTrapPositionX() {
		return this.posX;
	}
	/**
	 * Returns position Y of the trap.
	 * @return posY position Y of the trap
	 */
	public int getTrapPositionY() {
		return this.posY;
	}
	/**
	 * Sets the model of the trap.
	 * @param size pixel size of the trap model.
	 * @return trapModel model of the trap.
	 */
	public ImageView setTrapModel(int size){
		Image trappicture = new Image("images\\trap.png");
		ImageView trapModel = new ImageView(trappicture);
		
		trapModel.setFitHeight(size);
		trapModel.setFitWidth(size);
		trapModel.setX(this.posX);
		trapModel.setY(this.posY);
		
		return trapModel;
	}
	
	public static void main(String[] args) {
		

	}

}
