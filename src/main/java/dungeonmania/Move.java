package dungeonmania;

import dungeonmania.util.Direction;

public interface Move {
	/**
	 * Moves entity to TBD position
	 * @param entity
	 * @param dungeon
	 */
	public void move(Entity entity, Dungeon dungeon, Direction direction);

	public void move(Entity entity, Dungeon dungeon);
}
