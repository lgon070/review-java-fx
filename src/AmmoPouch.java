import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AmmoPouch {
	int posX, posY;
	/**
	 * Constructor of the ammo the pouch.
	 * @param posX position X of the ammo pouch.
	 * @param posY position Y of the ammo pouch.
	 */
	public AmmoPouch(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	/**
	 * Sets position of the ammo pouch.
	 * @param posX position X of the ammo pouch.
	 * @param posY position Y of the ammo pouch.
	 */
	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	/**
	 * Returns position X of the ammo pouch.
	 * @return posX position X of the ammo pouch.
	 */
	public int getAmmoPositionX() {
		return this.posX;
	}
	/**
	 * Returns position Y of the ammo pouch.
	 * @return posY position Y of the ammo pouch
	 */
	public int getAmmoPositionY() {
		return this.posY;
	}
	/**
	 * Sets the model of the ammo pouch.
	 * @param size pixel size of the ammo pouch model.
	 * @return ammoModel model of the ammo pouch.
	 */
	public ImageView setAmmoModel(int size) {
		Image ammopicture = new Image("images/ammo.png");
		ImageView ammoModel = new ImageView(ammopicture);
		
		ammoModel.setFitWidth(size);
		ammoModel.setFitHeight(size);
		ammoModel.setX(this.posX);
		ammoModel.setY(this.posY);
		
		return ammoModel;
	}
	
	public static void main(String[] args) {
		
	}

}
