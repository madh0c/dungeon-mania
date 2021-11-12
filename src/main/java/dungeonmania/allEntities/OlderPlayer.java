package dungeonmania.allEntities;

import java.util.*;

import dungeonmania.Battle;
import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class OlderPlayer extends MovingEntity {
    List<Direction> trackingList = new ArrayList<>();
    int traceUntil = 0;
    
    public OlderPlayer (String id, Position position, boolean enemyAttack) {
        super(id, position, "older_player", enemyAttack);
    }

    /**
	 * Check if the older player is able to collide with entity <p>
	 * Collide means if they are able to be on the same square <p>
	 * If the colliding entity is a player, then a battle occurs
	 * 
	 * @param	entity	The entity in question
	 * @param	dungeon	The given dungeon where this collision is taking place
	 * @return	true - If player is able to collide<p>
	 * 			false - If player is not able to collide
	 */
	public boolean collide(Entity entity, Dungeon dungeon, Direction currentDir) {
		// If empty space
		if (entity == null) {
			return true;
		}
		
		if (entity instanceof Wall || entity instanceof BombStatic || entity instanceof ZombieToastSpawner) {
			return false;
		} else if (entity instanceof Door) {
			Door door = (Door) entity;
			if (door.isOpen()) {
				return true;
			} return false;
			
		} else if (entity instanceof Boulder) {
			Boulder boulder = (Boulder) entity;
			Position newPos = boulder.getPosition().translateBy(currentDir);
			return boulder.collide(dungeon.getEntity(newPos));		
		} else if (entity instanceof Portal) {
			Portal portal1 = (Portal) entity;

			for (Entity currEnt : dungeon.getEntities()) {
				if (!(currEnt instanceof Portal)) {
					continue;
				}
				// Check if same entity
				if (!currEnt.getId().equals(portal1.getId())) {
					Portal portal2 = (Portal) currEnt;
					if (portal1.getColour().equals(portal2.getColour())) {
						// Find position of p2
						// Move in direciton of currDir
						Entity nextTo = dungeon.getEntity(portal2.getPosition().translateBy(currentDir));
						return collide(nextTo, dungeon);
					}
				}
			} return false;
		} else if (entity instanceof Player) {
            Player player = (Player) entity;
            boolean willFight = (!player.getSunstoneStatus() && !dungeon.getMidnightStatus() && player.isVisible());
			if (enemyAttack() && willFight) {
				Battle.battle(entity, dungeon);
			}
		}

		return true;
	}

    /* Move the older player in the previous path of the player */
    @Override
    public void move(Dungeon dungeon) {


        boolean timePortalExists = false;
        Position portalPos = null;

        for (Entity ent : dungeon.getEntities()) {
            if (ent instanceof TimeTravellingPortal) {
                timePortalExists = true;
            }
        }

        System.out.println(dungeon.getTickNumber());
        System.out.println(traceUntil);


        if (dungeon.getTickNumber() - 1 < traceUntil) {
            Direction currentDir = trackingList.get(dungeon.getTickNumber() - 1);

            Position nextPos = getPosition().translateBy(currentDir);
            List<Entity> entOnCell = dungeon.getEntitiesOnCell(nextPos);
            boolean canCollide = true;

            for (Entity ent : entOnCell) {
                if (!collide(ent, dungeon, currentDir)) {
                    canCollide = false;
                    break;
                }
            }

            if (canCollide) {
                this.setPosition(getPosition().translateBy(currentDir));
            }

        } else if (timePortalExists) {
            findPortal(portalPos, dungeon);
        } 

        if (getPosition().equals(portalPos)) {
            dungeon.removeEntity(this);
        }
    }

    /**
	 * Move the older player iff it can move to a cardinally adjacent cell that is closer to the a time travelling portal than
	 * the current cell.
	 * @param direction	Desired direction of mercenary
	 * @param dungeon	Current dungeon of mercenary
	 * @return boolean of whether the mercenary got moved
	 */
	public boolean oPMove(Direction direction, Dungeon dungeon) {
		// Check if collidable with next entity
		Position pos = getPosition();
		Entity ent = dungeon.getEntity(pos.translateBy(direction));
		if (ent != null && !collide(ent, dungeon)) {
			return false;
		}

		setPosition(getPosition().translateBy(direction));
		return true;
	}

    public void findPortal(Position destination, Dungeon dungeon) {
        if (destination.getY() != getPosition().getY()) {
			// If player is to the up of merc
			if (destination.getY() < getPosition().getY()) {
                if (oPMove(Direction.UP, dungeon)) {
                    return;
                }
			} else {
				if (oPMove(Direction.DOWN, dungeon)) {
                    return;
                }
			}
		} else if (destination.getX() < getPosition().getX()) {
            if (oPMove(Direction.LEFT, dungeon)) {
                return;
            }
        } else {
            if (oPMove(Direction.RIGHT, dungeon)) {
                return;
            }
		}
    }

    public int getTraceUntil() {
        return traceUntil;
    }

    public void setTraceUntil(int traceUntil) {
        this.traceUntil = traceUntil;
    }

    public void setTrackingList(List<Direction> trackingList) {
        this.trackingList = trackingList;
    }

}
