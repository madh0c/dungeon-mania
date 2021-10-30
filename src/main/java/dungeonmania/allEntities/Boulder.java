package dungeonmania.allEntities;

import dungeonmania.CollectibleEntity;
import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Boulder extends Entity {

    public Boulder(Position position) {
        super(position, "boulder");
    }

	@Override
	public boolean collide(Entity entity) {
		// If empty space
		if (entity == null) {
			return true;
		} else if (entity instanceof Switch) {
			return true;
		}
		
		return false;
	}
}
