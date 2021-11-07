package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.*;

public abstract class EntityFactory {
	/**
	 * @param type
	 * @param position
	 * @return a new instance of the entity that should be created
	 */
	public abstract Entity createEntity(String id, String type, Position position);

	public abstract Player createPlayer(String id, Position position);

	public Portal createPortal(String id, Position position, String colour) {
		Portal portal = new Portal(id, position, colour);
		return portal;
	}

}
