import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FriendlyCreature {
	int posX, posY;
	/**
	 * Constructor of the creature.
	 * @param posX position X of creature.
	 * @param posY position Y of creature.
	 */
	public FriendlyCreature(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		
		}
	/**
	 * Sets position of the creature.
	 * @param posX position X of creature.
	 * @param posY position Y of creature.
	 */
	public void setCreaturePosition(int posX, int posY) {
		this.posX=posX;
		this.posY=posY;
	}
	
	/**
	 * Returns position X of the creature.
	 * @return posX position X of the creature.
	 */
	public int getCreaturePositionX() {
		return this.posX;
	}
	/**
	 * Returns position Y of the creature.
	 * @return posY position Y of the creature
	 */
	public int getCreaturePositionY() {
		return this.posY;
	}
	/**
	 * Sets the model of the creature.
	 * @param size pixel size of the creature model.
	 * @return creatureModel model of the creature.
	 */
	public ImageView setCreatureModel(int size) {
		Image creaturepicture = new Image("images/creature.png");
		ImageView creatureModel = new ImageView(creaturepicture);
		
		creatureModel.setFitHeight(size);
		creatureModel.setFitWidth(size);
		creatureModel.setX(this.posX);
		creatureModel.setY(this.posY);
		
		return creatureModel;
	}
	
	public static void main(String[] args) {
		

	}

}
