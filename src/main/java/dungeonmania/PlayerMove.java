package dungeonmania;

import dungeonmania.allEntities.Player;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlayerMove implements Move {
    @Override
	public void move(Entity entity, Dungeon dungeon) {
		return;
	}

	@Override
	public void move(Entity entity, Dungeon dungeon, Direction direction) {
        Player player = (Player) entity;
		Position newPos = entity.getPosition().translateBy(direction);
		Entity newEnt = dungeon.getEntity(newPos);
		if (!player.collide(newEnt, dungeon)) {
			return;
		}

		// Move code
		player.setPosition(newPos);
		
	}


}
