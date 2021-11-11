package dungeonmania.allEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Battle;
import dungeonmania.CollectableEntity;
import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.Dijkstra;



public class Mercenary extends MovingEntity {

	private boolean isAlly;
	private Direction currentDir;
	private int sceptreTickDuration;

    public Mercenary(String id, Position position, boolean enemyAttack) {
        super(id, position, "mercenary", enemyAttack);
		this.isAlly = false;
		super.setHealth(30);
		super.setBaseAttack(5);
    }

	public boolean getIsAlly() {
		return this.isAlly;
	}

	public void setAlly(boolean isAlly) {
		this.isAlly = isAlly;
	}
	
	public Direction getCurrentDir() {
		return currentDir;
	}

	public void setCurrentDir(Direction currentDir) {
		this.currentDir = currentDir;
	}

	public int getSceptreTick() {
		return sceptreTickDuration;
	}

	public void setSceptreTickDuration(int durationTicks) {
		sceptreTickDuration = (durationTicks >= 0) ? durationTicks : -1; 
	}
	
	@Override
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
		if (entity instanceof CollectableEntity) {
			return true;
		}
			return false;
		}
		
		if (entity instanceof Player) {
			// If not ally, battle
			if (!isAlly) {
				if (enemyAttack()) {
					Battle.battle(this, dungeon);
				}
				return true;
			}
			// If ally
			return true;
		}

		// Have to add MovableEntity
		if (entity instanceof MovingEntity) {


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
		boolean sceptreStatus = false;
		CollectableEntity sceptre = null;
		this.isAlly = true;	
		for (CollectableEntity collect : dungeon.getInventory()) {
			if (collect instanceof Sceptre) {
				sceptreStatus = true;
				sceptre = collect;
			}
		}
		if (sceptreStatus) {
			dungeon.getInventory().remove(sceptre);
			dungeon.getPlayer().getControlled().add(getId());
			this.setSceptreTickDuration(10);
		} else {
			// Remove the first gold if player doesnt have sunstone
			if (!dungeon.getPlayer().getSunstoneStatus()) {
				Treasure gold = null;
				for (CollectableEntity ent : dungeon.getInventory()) {
					if (ent instanceof Treasure) {
						gold = (Treasure) ent;
						break;			
					}
				}
				dungeon.getInventory().remove(gold);
			}	
		}	
	}

	public void sceptreTick(Dungeon dungeon) {
		this.setSceptreTickDuration(this.getSceptreTick() - 1);
		List<String> controlledIds = dungeon.getPlayer().getControlled();
		List<String> releaseId = new ArrayList<>();
		if (this.getSceptreTick() == -1) {
			for (String mercId : controlledIds) {
				//Found merc that is controlled and now turn back to enemy
				if (this.getId().equals(mercId)) {
					releaseId.add(this.getId());
					this.setAlly(false);
				}
			}
			controlledIds.remove(this.getId());
		}
		
		// int sceptreTicksLeft = currentDungeon.getPlayer().getSceptreTick();
		// 	currentDungeon.getPlayer().setSceptreTickDuration(sceptreTicksLeft - 1);
		// 	List<String> controlledIds = currentDungeon.getPlayer().getControlled();
		// 	if (sceptreTicksLeft == 0 && !controlledIds.isEmpty()) {
		// 		String releaseId = controlledIds.get(0);
		// 		for (Entity ent : currentDungeon.getEntities()) {
		// 			if (releaseId.equals(ent.getId()) && ent instanceof Mercenary) {
		// 				Mercenary merc = (Mercenary) ent;
		// 				merc.setAlly(false);
		// 			}
		// 		}
		// 		controlledIds.remove(releaseId);
		// 	}
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

		// Position currPos = getPosition();
		// setPosition(Dijkstra.move(currPos, dungeon));
		// portalMove(dungeon);

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