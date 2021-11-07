package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity {
	private int health;
	private int baseAttack;

    public MovingEntity(String id, Position position, String type) {
        super(id, position, type);
    }

	public int getBaseAttack() {
		return baseAttack;
	}

	public void setBaseAttack(int baseAttack) {
		this.baseAttack = baseAttack;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
    
	public boolean collide(Entity entity, Dungeon dungeon) {
		// If empty space
		if (entity == null) {
			return true;
		}
		
		if (entity instanceof Boulder) {
			return false;
		} else if (entity instanceof BombStatic) {
			return false;
		} else if (entity instanceof Wall) {
			return false;
		} else if (entity instanceof ZombieToastSpawner) {
			return false;
		}  else if (entity instanceof Door) {
			Door door = (Door) entity;
			if (door.isOpen()) {
				return true;
			}
			return false;
		} else if (entity instanceof Exit) {
			return false;
		} else if (entity instanceof MovingEntity) {
			return false;
		} else if (entity instanceof Player) {
			if (dungeon.getMode().enemyAttack()) {
				Battle.battle(this, dungeon);
			}
			return true;
		}
		if (entity instanceof CollectableEntity) {
			return true;
		}
		
		return true;
	}

	public abstract void move(Dungeon dungeon);
}

