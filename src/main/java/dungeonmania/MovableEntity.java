package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.Position;

public abstract class MovableEntity extends Entity {

    public MovableEntity(Position position, String type) {
        super(position, type);
    }
    
	@Override
	public boolean collide(Entity entity) {
		// If empty space
		if (entity == null) {
			return true;
		}
		
		if (entity instanceof Boulder) {
			return false;
		} else if (entity instanceof Wall) {
			return false;
		} else if (entity instanceof ZombieToastSpawner) {
			return false;
		} else if (entity instanceof Door) {
			return false;
		} else if (entity instanceof Exit) {
			return false;
		}
		
		return true;
	}
}

