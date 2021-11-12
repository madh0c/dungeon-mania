package dungeonmania.allEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Battle;
import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class Spider extends MovingEntity {

	private List<Position> range;
	// Position of tile in relation to centre, dependent on currTile
	//	8 1 2
	//	7 0 3 ^	UP
	//	6 5 4 v DOWN
	private int currTile;

	private boolean clockwise;

    public Spider(String id, Position position, boolean enemyAttack) {
        super(id, position, "spider", enemyAttack);
		range = new ArrayList<>();
		super.setHealth(10);
		super.setBaseAttack(5);
		currTile = 0;
		range.add(position);
		range.add(position.translateBy(Direction.UP));
		range.add(position.translateBy(Direction.UP).translateBy(Direction.RIGHT));
		range.add(position.translateBy(Direction.RIGHT));
		range.add(position.translateBy(Direction.RIGHT).translateBy(Direction.DOWN));
		range.add(position.translateBy(Direction.DOWN));
		range.add(position.translateBy(Direction.DOWN).translateBy(Direction.LEFT));
		range.add(position.translateBy(Direction.LEFT));
		range.add(position.translateBy(Direction.LEFT).translateBy(Direction.UP));

		clockwise = true;

    }

	public int getCurrTile() {
		return currTile;
	}

	public boolean getClockwise() {
		return clockwise;
	}

	public List<Position> getRange() {
		return range;
	}

	public void setCurrTile(int currTile) {
		this.currTile = currTile;
	}

	public void setClockwise(boolean clockwise) {
		this.clockwise = clockwise;
	}

	public void setRange(List<Position> range) {
		this.range = range;
	}

	@Override
	public boolean collide(Entity entity, Dungeon dungeon) {
		// If empty space
		if (entity == null) {
			return true;
		}
		
		if (entity instanceof Boulder) {
			return false;
		} else if (entity instanceof Player) {
			if (enemyAttack()) {
				Battle.battle(this, dungeon);
			}
		}
		
		else if (entity instanceof MovingEntity) {
			return false;
		}
		
		return true;
	}

	@Override
	public void move(Dungeon dungeon) {
		// Find predetermined position
		if (clockwise) {
			// If position 0 move up
			if (currTile == 0) {
				// Get pos1
				Position pos1 = range.get(1);
				// check collide in pos1
				if (collide(dungeon.getEntity(pos1), dungeon)) {
					// Move to pos1
					setPosition(pos1);
					setCurrTile(1);
				} else {
					// Reverse clockwise
					setClockwise(false);
				}
			}// If other positions
			else {
				// Get nextPos
				Position nextPos;
				int nextTile = 0;
				// If looped back around, move back to tile 1
				if (currTile == 8) {
					nextTile = 1;
				} else {
					nextTile = currTile + 1;
				}
				nextPos = range.get(nextTile);
				// Check collide
				if (collide(dungeon.getEntity(nextPos), dungeon)) {
					setPosition(nextPos);
					setCurrTile(nextTile);
				} else {
					// If not, reverse and do nothing
					setClockwise(false);
				}
			}

		} else {
			// If position 0, try to move down
			if (currTile == 0) {
				Position pos5 = range.get(5);
				// Check collide in pos5
				if (collide(dungeon.getEntity(pos5), dungeon)) {
					// Move to pos5
					setPosition(pos5);
					setCurrTile(5);
				}
				setClockwise(true);
			}
			// Other positions
			else {
				// Get nextPos
				Position nextPos;
				int nextTile = 0;
				// Move anticlockwise, unless reached 1 then move to 8
				if (currTile == 1) {
					nextTile = 8;
				} else {
					nextTile = currTile - 1;
				}
				nextPos = range.get(nextTile);

				// Check collide
				if (collide(dungeon.getEntity(nextPos), dungeon)) {
					setPosition(nextPos);
					setCurrTile(nextTile);
				} else {
					// If not, reverse and do nothing
					setClockwise(false);
				}
			}
		}
	}

	@Override
	public void moveScared(Dungeon dungeon) {
		// direction
		Direction dir = Direction.NONE;
		Player player = dungeon.getPlayer();

		if (player.getPosition().getY() != getPosition().getY()) {
			// If player is to the up of merc
			if (player.getPosition().getY() < getPosition().getY()) {
				dir = Direction.DOWN;				
			} 
			// If on down side
			else {
				dir = Direction.UP;
			}
		}

		// left right movement
		if (player.getPosition().getX() != getPosition().getX()) {
			// If player is to the left of merc
			if (player.getPosition().getX() < getPosition().getX()) {
				dir = Direction.RIGHT;	
			} 
			// If on right side
			else {
				dir = Direction.LEFT;	
			}
		}

		// Check if collideable with the direciton
		Position newPos = getPosition().translateBy(dir);
		for (Entity entity : dungeon.getEntitiesOnCell(newPos)) {
			if (!collide(entity, dungeon)) {
				return;
			}
		}
		// If yes, then set pos
		setPosition(newPos);
	}
}
