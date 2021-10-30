package dungeonmania.allEntities;

import java.util.Map;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovableEntity;
import dungeonmania.util.Position;


public class Mercenary extends MovableEntity {

	private boolean isAlly;
	private Position currentDir;

    public Mercenary(Position position) {
        super(position, "mercenary");
		this.isAlly = false;
    }

	public boolean getIsAlly() {
		return this.isAlly;
	}


	public boolean collide(Entity entity, Dungeon dungeon) {
		// If empty space
		if (entity == null) {
			return true;
		}
		
		if (entity instanceof Wall) {
			return false;
		} else if (entity instanceof ZombieToastSpawner) {
			return false;
		} else if (entity instanceof Door) {
			return false;
		} else if (entity instanceof Boulder) {
			return false;		
		} else if (entity instanceof Portal) {
			Portal portal1 = (Portal) entity;
			for (Map.Entry<String, Entity> entry : dungeon.getEntities().entrySet()) {
				Entity currentEntity = entry.getValue();
				// Check if same entity
				if (!currentEntity.getId().equals(portal1.getId())) {
					Portal portal2 = (Portal) currentEntity;
					if (portal1.getColour().equals(portal2.getColour())) {
						// Find position of p2
						// Move in direciton of currDir
						Entity nextTo = dungeon.getEntity(portal2.getPosition().translateBy(currentDir));
						return collide(nextTo, dungeon);
					}
				}
	
			}
			return false;
		}

		// Have to add MovableEntity
		if (entity instanceof MovableEntity) {
			// If ally
			if (!isAlly){				
				return false;
			} 

			// Battle if true
			return true;
		}
		
		return true;
	}
}
