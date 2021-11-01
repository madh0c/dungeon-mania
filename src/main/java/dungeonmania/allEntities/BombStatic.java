package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class BombStatic extends Entity {

	/**
	 * This bomb has been placed and will be exploded, thus cannot be moved or moved into
	 * @param position	where the bomb is located
	 */
    public BombStatic(Position position) {
        super(position, "bomb");
    }

}
