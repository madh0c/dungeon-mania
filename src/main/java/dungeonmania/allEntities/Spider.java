package dungeonmania.allEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.MovableEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class Spider extends MovableEntity {

	private List<Position> range;
	// Position of tile in relation to centre, dependent on currTile
	//	8 1 2
	//	7 0 3 
	//	6 5 4
	private int currTile;

	private boolean clockwise;

    public Spider(Position position) {
        super(position, "spider");
		range = new ArrayList<>();
		
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
	public boolean collide(Entity entity) {
		// If empty space
		if (entity == null) {
			return true;
		}
		
		if (entity instanceof Boulder) {
			return false;
		} else if (entity instanceof MovableEntity) {
			return false;
		}
		
		return true;
	}
}
