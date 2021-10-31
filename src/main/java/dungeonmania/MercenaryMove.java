package dungeonmania;

import java.util.Map;

import dungeonmania.allEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryMove implements Move {

	@Override
	public void move(Entity entity, Dungeon dungeon, Direction direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(Entity entity, Dungeon dungeon) {
		Mercenary merc = (Mercenary) entity;
		// Find player
		Player player = dungeon.getPlayer();
		
		// Prioritise up down movement
		// If not on same y axis
		if (player.getPosition().getY() != merc.getPosition().getY()) {
			// If player is to the up of merc
			if (player.getPosition().getY() < merc.getPosition().getY()) {
				merc.setPosition(merc.getPosition().translateBy(Direction.UP));
				merc.setCurrentDir(Direction.UP);
				portalMove(merc, dungeon);
			} 
			// If on down side
			else {
				merc.setPosition(merc.getPosition().translateBy(Direction.DOWN));
				merc.setCurrentDir(Direction.DOWN);
				portalMove(merc, dungeon);
			}
			return;
		}

		// left right movement
		if (player.getPosition().getX() != merc.getPosition().getX()) {
			// If player is to the left of merc
			if (player.getPosition().getX() < merc.getPosition().getX()) {
				merc.setPosition(merc.getPosition().translateBy(Direction.LEFT));
				merc.setCurrentDir(Direction.LEFT);
				portalMove(merc, dungeon);
			} 
			// If on right side
			else {
				merc.setPosition(merc.getPosition().translateBy(Direction.RIGHT));
				merc.setCurrentDir(Direction.RIGHT);
				portalMove(merc, dungeon);
			}
			return;
		}
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
