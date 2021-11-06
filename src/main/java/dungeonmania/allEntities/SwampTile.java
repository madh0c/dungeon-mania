package dungeonmania.allEntities;

import dungeonmania.*;
import dungeonmania.util.Position;


public class SwampTile extends Entity {

	int bogFactor;

	/**
	 * This bomb is a Collectible, as can be picked up
	 * @param position	where the bomb is located
	 */
    public SwampTile(String id, Position position, int bogFactor) {
        super(id, position, "swamp_tile");
		this.bogFactor = bogFactor;
	}

	public int getBogFactor() {
		return bogFactor;
	}

}
