package dungeonmania.allEntities;

import java.util.Map;

import javax.xml.stream.events.EndElement;

import dungeonmania.CollectibleEntity;
import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class Player extends Entity {
    private int health;
	private int attack;
    private boolean visible;
	private Direction currentDir;
	private boolean haveKey;
	private int invincibleTickDuration;

    public Player(Position position) {
        super(position, "player");
		this.health = 100;
		this.visible = true;
		invincibleTickDuration = 0;
    }

    public void setHealth(int newHealth) {
        health = newHealth;
    }

	public int getAttack() {
		return attack;
	}

	public void setAttack(int newAttack) {
        attack = newAttack;
    }

    public int getHealth() {
        return health;
    }

    public boolean isVisible() {
        return visible;
    }

	public void setVisibility(boolean canBeSeen) {
		visible = canBeSeen;
	} 

	public int getInvincibleTickDuration() {
		return invincibleTickDuration; 
	}

	public void setInvincibleTickDuration(int durationInTicks) {
		invincibleTickDuration = (durationInTicks >= 0) ? durationInTicks : 0;
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
		} else if (entity instanceof BombStatic) {
			return false;
		} else if (entity instanceof ZombieToastSpawner) {
			return false;
		} else if (entity instanceof Door) {
			return haveKey;
		} else if (entity instanceof Boulder) {
			Boulder boulder = (Boulder) entity;

			Position newPos = boulder.getPosition().translateBy(currentDir);
			return boulder.collide(dungeon.getEntity(newPos));		
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

		if (entity instanceof CollectibleEntity) {
			// can't have 2 keys in inv
			if (entity instanceof Key) {
				for (CollectibleEntity item : dungeon.getInventory()) {
					if (item.getType().equals("key")) {
						return true;
					}
				}				
			} else if (entity instanceof OneRing) {
				for (CollectibleEntity item : dungeon.getInventory()) {
					if (item.getType().equals("one_ring")) {
						return true;
					}
				}	
			}
			// Remove entity
			dungeon.removeEntity(entity);

			// Add to player inv
			dungeon.addItemToInventory((CollectibleEntity)entity);

			return true;
		}

		// Have to add MovableEntity
		
		return true;
	}


}
