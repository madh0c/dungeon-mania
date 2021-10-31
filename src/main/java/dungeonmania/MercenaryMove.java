package dungeonmania;

import java.util.Map;

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
				// merc.setPosition(merc.getPosition().translateBy(Direction.UP));
				// merc.setCurrentDir(Direction.UP);
				if(mercMove(merc, Direction.UP, dungeon)) {
					return;
				}

				// mercMove(merc, Direction.UP, dungeon);

				// portalMove(merc, dungeon);
				
			} 
			// If on down side
			else {
				// merc.setPosition(merc.getPosition().translateBy(Direction.DOWN));
				// merc.setCurrentDir(Direction.DOWN);
				if (mercMove(merc, Direction.DOWN, dungeon)) {
					return;
				}
				// mercMove(merc, Direction.DOWN, dungeon);

				// portalMove(merc, dungeon);
			}
			// return;
		}

		// left right movement
		if (player.getPosition().getX() != merc.getPosition().getX()) {
			// If player is to the left of merc
			if (player.getPosition().getX() < merc.getPosition().getX()) {
				// merc.setPosition(merc.getPosition().translateBy(Direction.LEFT));
				// merc.setCurrentDir(Direction.LEFT);

				if (mercMove(merc, Direction.LEFT, dungeon)) {
					return;
				}
				// mercMove(merc, Direction.LEFT, dungeon);
				// portalMove(merc, dungeon);
			} 
			// If on right side
			else {
				// merc.setPosition(merc.getPosition().translateBy(Direction.RIGHT));
				// merc.setCurrentDir(Direction.RIGHT);
				if (mercMove(merc, Direction.RIGHT, dungeon)) {
					return;
				}
				// mercMove(merc, Direction.RIGHT, dungeon);

				// portalMove(merc, dungeon);
			}
			// return;
		}
	}

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

	public void portalMove(Mercenary merc, Dungeon dungeon) {
		Position pos = merc.getPosition();
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
			merc.setPosition(posPortal2.translateBy(merc.getCurrentDir()));
		}
	}
	
}
