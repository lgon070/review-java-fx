import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EnemyMonster {

	int posX, posY;
	/**
	 * Constructor of the monster.
	 * @param posX position X of monster.
	 * @param posY position Y of monster.
	 */
	public EnemyMonster(int posX, int posY) {
		this.posX =posX;
		this.posY =posY;
		
	}
	/**
	 * Sets position of the monster.
	 * @param posX position X of monster.
	 * @param posY position Y of monster.
	 */
	public void setEnemyPosition(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	/**
	 * Returns position X of the monster.
	 * @return posX position X of the monster.
	 */
	public int getEnemyPositionX() {
		return this.posX;
	}
	/**
	 * Returns position Y of the monster.
	 * @return posY position Y of the Monster
	 */
	public int getEnemyPositionY() {
		return this.posY;
	}
	
	/**
	 * Sets the model of the monster.
	 * @param size pixel size of the monster model.
	 * @return enemyModel 
	 */
	public ImageView setEnemyModel(int size) {
		Image enemypicture = new Image("images/enemy.png");
		ImageView enemyModel = new ImageView(enemypicture);

		enemyModel.setFitWidth(size);
		enemyModel.setFitHeight(size);
		enemyModel.setX(this.posX);
		enemyModel.setY(this.posY);
		
		return enemyModel;	
	}
	
	
	
	public static void main(String[] args) {
		
	}
	
}
