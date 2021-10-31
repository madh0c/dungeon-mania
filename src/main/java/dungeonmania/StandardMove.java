package dungeonmania;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class StandardMove implements Move {

	@Override
	public void move(Entity entity, Dungeon dungeon) {
		return;
	}

	@Override
	public void move(Entity entity, Dungeon dungeon, Direction direction) {
		Position newPos = entity.getPosition().translateBy(direction);
		Entity newEnt = dungeon.getEntity(newPos);
		// MovableEntity moving = (MovableEntity) entity;
		if (entity instanceof MovableEntity) {
			MovableEntity moving = (MovableEntity) entity;
			if (!moving.collide(newEnt, dungeon)) {
				return;
			}
		}

		if (!entity.collide(newEnt)) {
			return;
		}

		// Move code
		entity.setPosition(newPos);
		
	}
	
}
// Void Move(){
// 	Position pos
// 	If collide(entity(pos)) {
// 	return
	
// 	… move code
	