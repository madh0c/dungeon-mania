package dungeonmania.allEntities;

import java.util.Map;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovableEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class Mercenary extends MovableEntity {

	private boolean isAlly;
	private Direction currentDir;

    public Mercenary(Position position) {
        super(position, "mercenary");
		this.isAlly = false;
    }

	public boolean getIsAlly() {
		return this.isAlly;
	}

	public Direction getCurrentDir() {
		return currentDir;
	}

	public void setCurrentDir(Direction currentDir) {
		this.currentDir = currentDir;
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
				if (!currentEntity.getId().equals(portal1.getId())) {
					if (!(currentEntity instanceof Portal)) {
						continue;
					}
					Portal portal2 = (Portal) currentEntity;
					if (portal1.getColour().equals((portal2).getColour())) {
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
