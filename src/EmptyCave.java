import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EmptyCave {
	int posX;
	int posY;
		/**
		 * Constructor of the Empty cave.
		 * @param posX position X of the cave.
		 * @param posY position Y of the cave.
		 */
		public EmptyCave(int posX, int posY){
			this.posX = posX;
			this.posY = posY;
			
		}
		
		/**
		 * Sets the model of an empty cave.
		 * @param size pixel size of the cave.
		 * @return caveRoom model of the cave.
		 */
		public ImageView setCave(int size) {
			Image cave = new Image("images/cave.jpg");
			ImageView caveRoom = new ImageView(cave);
			
			caveRoom.setFitHeight(size);
			caveRoom.setFitWidth(size);
			caveRoom.setX(this.posX);
			caveRoom.setY(this.posY);
			
			return caveRoom;
		}
	public static void main(String[] args) {
		

	}

}
