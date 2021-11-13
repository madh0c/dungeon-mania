package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity {
	private int health;
	private int baseAttack;
	private boolean enemyAttack;
	private int ticksFrozen;

    public MovingEntity(String id, Position position, String type, boolean enemyAttack) {
        super(id, position, type);
		this.enemyAttack = enemyAttack;
		this.ticksFrozen = 0;
    }

	public int getTicksFrozen() {
		return ticksFrozen;
	}

	public void setTicksFrozen(int ticksFrozen) {
		this.ticksFrozen = ticksFrozen;
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

	public boolean enemyAttack() {
		return enemyAttack;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setEnemyAttack(boolean enemyAttack) {
		this.enemyAttack = enemyAttack;
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
			if (enemyAttack()) {
				Battle.battle(this, dungeon);
			}
			return true;
		}
		if (entity instanceof CollectableEntity) {
			return true;
		}

		if (entity instanceof SwampTile) {
			SwampTile swampTile = (SwampTile) entity;
			setTicksFrozen(swampTile.getMoveFactor() - 1);
		}
		
		return true;
	}

	public abstract void move(Dungeon dungeon);

	public abstract void moveScared(Dungeon dungeon); 

}

