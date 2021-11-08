package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.*;

public abstract class EntityFactory {
	/**
	 * Create and return a new Entity, depending on the given type
	 * @param id		Id of returned Entity
	 * @param type		Type of returned Entity
	 * @param position	Position of returned Entity
	 * @return a new instance of the entity that should be created
	 */
	public abstract Entity createEntity(String id, String type, Position position);

	/**
	 * Create and return a new player
	 * @param id		Id of player
	 * @param position	Position of player
	 * @return
	 */
	public abstract Player createPlayer(String id, Position position);

	/**
	 * Create and return a new portal
	 * @param id		Id of portal
	 * @param position	Position of portal
	 * @param colour	Colour of portal
	 * @return
	 */
	public Portal createPortal(String id, Position position, String colour) {
		Portal portal = new Portal(id, position, colour);
		return portal;
	}

}
