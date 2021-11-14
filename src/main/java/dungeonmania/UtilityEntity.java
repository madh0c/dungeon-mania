package dungeonmania;

import dungeonmania.allEntities.Player;
import dungeonmania.util.Position;

public abstract class UtilityEntity extends CollectableEntity {

	public UtilityEntity(String id, Position position, String type) {
		super(id, position, type);
	}

/**
	 * Use an item <ul>
	 * <li> Adds attack damage to player for certain items
	 * <li> Reduces attack damage of enemy for certain items
	 * </ul>
	 * @param dungeon	Dungeon of item
	 */
	public abstract void use(Player player);	
}
