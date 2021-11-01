package dungeonmania;

import java.util.Map;

import dungeonmania.allEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlayerMove implements Move {
    @Override
	public void move(Entity entity, Dungeon dungeon) {
		return;
	}

	@Override
	/**
	 * Find the square that the player is about to move on to then collides the player with the square.
	 * If collideable then player will move onto that square.
	 * @param entity
	 * @param dungeon
	 * @param direction
	 * 
	 */
	public void move(Entity entity, Dungeon dungeon, Direction direction) {
        Player player = (Player) entity;
		Position newPos = entity.getPosition().translateBy(direction);

		boolean collideable = true;
		for (Entity ent : dungeon.getEntitiesOnCell(newPos)) {
			if (!player.collide(ent, dungeon)) {
				collideable = false;
			}
		}

		if (collideable) {
			// Move code
			player.setPosition(newPos);
			player.setCurrentDir(direction);
			portalMove(player, dungeon);
		}		
		
	}

	/**
	 * Find the square that the player is about to move on (through the portal) then collides the player with the square.
	 * If collideable then player will move through the portal onto that square.
	 * @param player
	 * @param dungeon
	 */
	public void portalMove(Player player, Dungeon dungeon) {
		Position pos = player.getPosition();
		Portal portal1 = (Portal) dungeon.getEntity("portal", pos);
		Position posPortal2 = new Position(0, 0);
		if (portal1 != null) {
			// Find other portal
			for (Map.Entry<String, Entity> entry : dungeon.getEntities().entrySet()) {
				Entity currEnt = entry.getValue();
				if (currEnt instanceof Portal) {
					Portal portal2 = (Portal) currEnt;
					if (portal2.getColour().equals(portal1.getColour()) && !portal2.equals(portal1)) {
						posPortal2 = portal2.getPosition();
						break;
					}
				}
			}
			player.setPosition(posPortal2.translateBy(player.getCurrentDir()));
		}
	}

}
