package dungeonmania.allEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Battle;
import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovableEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class Spider extends MovableEntity {

	private List<Position> range;
	// Position of tile in relation to centre, dependent on currTile
	//	8 1 2
	//	7 0 3 ^	UP
	//	6 5 4 v DOWN
	private int currTile;

	private boolean clockwise;

    public Spider(String id, Position position) {
        super(id, position, "spider");
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
			if (dungeon.getMode().enemyAttack()) {
				Battle.battle(this, dungeon);
			}
		}
		
		else if (entity instanceof MovableEntity) {
			return false;
		}
		
		return true;
	}

	// @Override
	// public boolean collide(Entity entity) {
	// 	// If empty space
	// 	if (entity == null) {
	// 		return true;
	// 	}
		
	// 	if (entity instanceof Boulder) {
	// 		return false;
	// 	} 
	// 	// else if (entity instanceof Player) {
	// 	// 	Battle.battle(this, dungeon);
	// 	// }
		
	// 	else if (entity instanceof MovableEntity) {
	// 		return false;
	// 	}
		
	// 	return true;
	// }
}
