package dungeonmania.allEntities;

import dungeonmania.CollectibleEntity;
import dungeonmania.Entity;
import dungeonmania.util.Position;


public class BombItem extends CollectibleEntity {

	/**
	 * This bomb is a Collectible, as can be picked up
	 * @param position	where the bomb is located
	 */
    public BombItem(Position position) {
        super(position, "bomb");
    }

}
