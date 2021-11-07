package dungeonmania.allEntities;

import dungeonmania.CollectableEntity;
import dungeonmania.util.Position;


public class BombItem extends CollectableEntity {

	/**
	 * This bomb is a Collectible, as can be picked up
	 * @param position	where the bomb is located
	 */
    public BombItem(String id, Position position) {
        super(id, position, "bomb");
	}

}
