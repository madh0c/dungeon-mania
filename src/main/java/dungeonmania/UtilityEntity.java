package dungeonmania;

import dungeonmania.allEntities.Player;
import dungeonmania.util.Position;

public abstract class UtilityEntity extends CollectableEntity {

	public UtilityEntity(String id, Position position, String type) {
		super(id, position, type);
	}

/**
	 * Use an item irrespective of subclass.
	 * @param dungeon	Dungeon of item
	 */
	public abstract void use(Player player);	
}
