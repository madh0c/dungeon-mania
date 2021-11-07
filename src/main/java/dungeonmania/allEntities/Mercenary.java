package dungeonmania.allEntities;

import dungeonmania.Battle;
import dungeonmania.CollectibleEntity;
import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovableEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class Mercenary extends MovableEntity {

	private boolean isAlly;
	private Direction currentDir;

    public Mercenary(String id, Position position) {
        super(id, position, "mercenary");
		this.isAlly = false;
		super.setHealth(30);
		super.setBaseAttack(5);
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
		} else if (entity instanceof BombStatic) {
			return false;
		} else if (entity instanceof ZombieToastSpawner) {
			return false;
		} else if (entity instanceof Door) {
			Door door = (Door) entity;
			if (door.isOpen()) {
				return true;
			}
			return false;
		} else if (entity instanceof Boulder) {
			return false;		
		} else if (entity instanceof Portal) {
			Portal portal1 = (Portal) entity;
			for (Entity currEnt : dungeon.getEntities()) {
				if (!currEnt.getId().equals(portal1.getId())) {
					if (!(currEnt instanceof Portal)) {
						continue;
					}
					Portal portal2 = (Portal) currEnt;
					if (portal1.getColour().equals((portal2).getColour())) {
						// Find position of p2
						// Move in direciton of currDir
						Entity nextTo = dungeon.getEntity(portal2.getPosition().translateBy(currentDir));
						return collide(nextTo, dungeon);
					}
				}
			}
		if (entity instanceof CollectibleEntity) {
			return true;
		}
			return false;
		}
		
		if (entity instanceof Player) {
			// If not ally, battle
			if (!isAlly) {
				if (dungeon.getMode().enemyAttack()) {
					Battle.battle(this, dungeon);
				}
				return true;
			}
			// If ally
			return true;
		}

		// Have to add MovableEntity
		if (entity instanceof MovableEntity) {


			return true;
		}
		
		return true;
	}

	/**
	 * Bribe the Mercenary <p>
	 * The check for if the player has enough gold is already done before this
	 * @param dungeon	Current dungeon of mercenary
	 */
	public void bribe(Dungeon dungeon) {

		Treasure gold = null;
		// Remove the first gold
		// Integer goldPos = 0;
		for (CollectibleEntity ent : dungeon.getInventory()) {
			if (ent instanceof Treasure) {
				// goldPos = Integer.parseInt(ent.getId());
				// break;
				gold = (Treasure) ent;			
			}
		}
		// dungeon.getInventory().remove(goldPos);
		dungeon.getInventory().remove(gold);
		this.isAlly = true;
	}

	/**
	 * Find the square that the mercenary is about to move on (through the portal) then collides the mercenary with the square.
	 * If collideable then mercenary will move through the portal onto that square.
	 * @param dungeon	Current dungeon of mercenary
	 */
	public void portalMove(Dungeon dungeon) {
		Position pos = getPosition();
		Portal portal1 = (Portal) dungeon.getEntity("portal", pos);
		Position posPortal2 = new Position(0, 0);
		if (portal1 != null) {
			// Find other portal
			for (Entity currEnt : dungeon.getEntities()) {
				if (currEnt instanceof Portal) {
					Portal portal2 = (Portal) currEnt;
					if (portal2.getColour().equals(portal1.getColour()) && !portal2.equals(portal1)) {
						posPortal2 = portal2.getPosition();
						break;
					}
				}
			}
			setPosition(posPortal2.translateBy(getCurrentDir()));
		}
	}

	/**
	 * Move the mercenery iff it can move to a cardinally adjacent cell that is closer to the player than
	 * the current cell.
	 * @param direction	Desired direction of mercenary
	 * @param dungeon	Current dungeon of mercenary
	 * @return boolean of whether the mercenary got moved
	 */
	public boolean mercMove(Direction direction, Dungeon dungeon) {
		// Check if collidable with next entity
		Position pos = getPosition();
		Entity ent = dungeon.getEntity(pos.translateBy(direction));
		Direction prevDir = getCurrentDir();
		setCurrentDir(direction);
		if (ent != null && !collide(ent, dungeon)) {
			setCurrentDir(prevDir);
			return false;
		}

		setPosition(getPosition().translateBy(direction));

		portalMove(dungeon);
		return true;
	}

	/**
	 * Move the mercenary towards the player<p>
	 * Up / Down first priority<p>
	 * Left / Right second priority
	 * @param dungeon	Current dungeon of mercenary
	 */
	@Override
	public void move(Dungeon dungeon) {
		// Find player
		Player player = dungeon.getPlayer();
		if (player == null) {
			return;
		}
		
		// Prioritise up down movement
		// If not on same y axis
		if (player.getPosition().getY() != getPosition().getY()) {
			// If player is to the up of merc
			if (player.getPosition().getY() < getPosition().getY()) {
				if(mercMove(Direction.UP, dungeon)) {
					return;
				}
				
			} 
			// If on down side
			else {
				if (mercMove(Direction.DOWN, dungeon)) {
					return;
				}
			}
		}

		// left right movement
		if (player.getPosition().getX() != getPosition().getX()) {
			// If player is to the left of merc
			if (player.getPosition().getX() < getPosition().getX()) {
				if (mercMove(Direction.LEFT, dungeon)) {
					return;
				}
			} 
			// If on right side
			else {
				if (mercMove(Direction.RIGHT, dungeon)) {
					return;
				}
			}
		}
	}
	
}
