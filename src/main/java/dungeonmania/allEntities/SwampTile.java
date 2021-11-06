package dungeonmania.allEntities;

import dungeonmania.*;
import dungeonmania.util.Position;


public class SwampTile extends Entity {

	int moveFactor;

	/**
	 * This bomb is a Collectible, as can be picked up
	 * @param position	where the bomb is located
	 */
    public SwampTile(String id, Position position, int moveFactor) {
        super(id, position, "swamp_tile");
		this.moveFactor = moveFactor;
	}
	public int getMoveFactor() {
		return moveFactor;
	}

	public void setMoveFactor(int moveFactor) {
		this.moveFactor = moveFactor;
	}

}
