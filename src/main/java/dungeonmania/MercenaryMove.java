package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryMove implements Move {

	@Override
	public void move(Entity entity, Dungeon dungeon, Direction direction) {
		
	}

	@Override
	public void move(Entity entity, Dungeon dungeon) {
		Mercenary merc = (Mercenary) entity;
		// Find player
		Player player = dungeon.getPlayer();
		if (player == null) {
			return;
		}
		
		// Prioritise up down movement
		// If not on same y axis
		if (player.getPosition().getY() != merc.getPosition().getY()) {
			// If player is to the up of merc
			if (player.getPosition().getY() < merc.getPosition().getY()) {
				if(mercMove(merc, Direction.UP, dungeon)) {
					return;
				}
				
			} 
			// If on down side
			else {
				if (mercMove(merc, Direction.DOWN, dungeon)) {
					return;
				}
			}
		}

		// left right movement
		if (player.getPosition().getX() != merc.getPosition().getX()) {
			// If player is to the left of merc
			if (player.getPosition().getX() < merc.getPosition().getX()) {
				if (mercMove(merc, Direction.LEFT, dungeon)) {
					return;
				}
			} 
			// If on right side
			else {
				if (mercMove(merc, Direction.RIGHT, dungeon)) {
					return;
				}
			}
		}
	}

	/**
	 * Move the mercenery iff it can move to a cardinally adjacent cell that is closer to the player than
	 * the current cell.
	 * @param merc
	 * @param direction
	 * @param dungeon
	 * @return boolean of whether the mercenary got moved
	 */
	public boolean mercMove(Mercenary merc, Direction direction, Dungeon dungeon) {
		// Check if collidable with next entity
		Position pos = merc.getPosition();
		Entity ent = dungeon.getEntity(pos.translateBy(direction));
		Direction prevDir = merc.getCurrentDir();
		merc.setCurrentDir(direction);
		if (ent != null && !merc.collide(ent, dungeon)) {
			merc.setCurrentDir(prevDir);
			return false;
		}


		merc.setPosition(merc.getPosition().translateBy(direction));

		portalMove(merc, dungeon);
		return true;
	}

	/**
	 * Find the square that the mercenary is about to move on (through the portal) then collides the mercenary with the square.
	 * If collideable then mercenary will move through the portal onto that square.
	 * @param player
	 * @param dungeon
	 */
	public void portalMove(Mercenary merc, Dungeon dungeon) {
		Position pos = merc.getPosition();
		Portal portal1 = (Portal) dungeon.getEntity("portal", pos);
		Position posPortal2 = new Position(0, 0);
		if (portal1 != null) {
			// Find other portal
			for (Entity currEnt : dungeon.getEntities()) {
				if (currEnt instanceof Portal) {
					Portal portal2 = (Portal) currEnt;
					if (portal2.getColour().equals(portal1.getColour()) && !portal2.equals(portal1)) {
						posPortal2 = portal2.getPosition();
						break;
					}
				}
			}
			merc.setPosition(posPortal2.translateBy(merc.getCurrentDir()));
		}
	}
	
}
