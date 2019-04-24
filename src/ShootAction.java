import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShootAction {
 int posX;
 int posY;
     /**
	 * Constructor of the shoot action.
	 * @param posX position X of shoot action.
	 * @param posY position Y of shoot action.
	 */
 public ShootAction(int posX, int posY) {
	 this.posX = posX;
	 this.posY = posY;
	 
 }
 /**
	 * Sets position of the shot .
	 * @param posX position X of shot .
	 * @param posY position Y of shot .
	 */
 public void setShootPosition(int posX, int posY) {
	 this.posX =posX;
	 this.posY=posY;
	 
 }

	/**
	 * Returns position X of the shot.
	 * @return posX position X of the shot.
	 */
 public int getShootPositionX() {
		return this.posX;
	}
 	/**
	 * Returns position Y of the shot.
	 * @return posY position Y of the shot. 
	 */
	public int getShootPositionY() {
		return this.posY;
	}
	/**
	 * Sets the model of the shot.
	 * @param size pixel size of the shot-aim model.
	 * @return shootArea model of the sights. 
	 */
 public ImageView setShootModel(int size) {
	 Image shootpicture = new Image("images/shootpicture.png");
	 ImageView shootArea = new ImageView(shootpicture);
	 
	 shootArea.setFitHeight(size);
	 shootArea.setFitWidth(size);
	 shootArea.setX(this.posX);
	 shootArea.setY(this.posY);
	 shootArea.toFront();
	 
	 return shootArea;
 }
 
	public static void main(String[] args) {
		

	}

 }
