package dungeonmania.allEntities;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class Boulder extends Entity {

    public Boulder(String id, Position position) {
        super(id, position, "boulder");
    }

	// @Override
	public boolean collide(Entity entity) {
		// If empty space
		if (entity == null) {
			return true;
		} else if (entity instanceof Switch) {
			return true;
		}
		
		return false;
	}

	public void move(Dungeon dungeon) {
		// Check if a player is on the same tile as boulder
		if (!getPosition().equals(dungeon.getPlayerPosition())) {
			return;
		}

		// Get position of possible switch on old tile
		Position prevPos = getPosition();
		Position prevPosSwitch = new Position(prevPos.getX(), prevPos.getY(), -1);

		// Move boulder
		setPosition(prevPos.translateBy(dungeon.getPlayer().getCurrentDir()));

		Position currPos = getPosition();
		Position currPosSwitch = new Position(currPos.getX(), currPos.getY(), -1);

		// Check if switch is being activated
		if (dungeon.entityExists("switch", currPosSwitch)) {
			Switch sw = (Switch) dungeon.getEntity("switch", currPosSwitch);
			sw.setStatus(true);
		}

		// Check if switch is being deactivated
		if (dungeon.entityExists("switch", prevPosSwitch)) {
			Switch sw = (Switch) dungeon.getEntity("switch", prevPosSwitch);
			sw.setStatus(false);
		}
	}
}
