package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity {
	private int health;
	private int baseAttack;
	private boolean enemyAttack;

    public MovingEntity(String id, Position position, String type, boolean enemyAttack) {
        super(id, position, type);
		this.enemyAttack = enemyAttack;
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
    
	/**
	 * Checks if the MovingEntity can coincide in the same tile as the given entity
	 * @param entity	Entity to be coincided with
	 * @param dungeon	Dungeon of everything
	 * @return	true if can coincide with entity
	 * 			<li> false if otherwise
	 */
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
		
		return true;
	}

	/**
	 * Move the MovingEntity depending on the type of entity being moved <ul>
	 * <li>Mercenary, Assassin: Move towards the player in the shortest path
	 * <li>Spider: Move around the 8 adjacent tiles of the spawning point of the spider
	 * <li>ZombieToast, Hydra: Move in random directions</ul>
	 * @param dungeon	Dungeon of MovingEntity
	 */
	public abstract void move(Dungeon dungeon);

	/**
	 * Move away from the player
	 * @param dungeon	Dungeon of MovingEntity
	 */
	public abstract void moveScared(Dungeon dungeon); 

	/**
	 * Reduce the health of the MovingEntity, depending on the player's health and
	 * attack damage<p>
	 * enemyHealth -= (playerHealth * playerAttack) / 5;
	 * @param dungeon	Dungeon of MovingEntity
	 */
	public void attack(Dungeon dungeon, int playerHp) {
		Player player = dungeon.getPlayer();
		setHealth(getHealth() - ((playerHp * player.getAttack()) / 5));
	};

}

