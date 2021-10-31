package dungeonmania;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SpiderMove implements Move {

	@Override
	public void move(Entity entity, Dungeon dungeon, Direction direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(Entity entity, Dungeon dungeon) {
<<<<<<< HEAD
		Spider spider = (Spider) entity;

		// Find predetermined position
		if (spider.getClockwise()) {
			// If position 0, try move up
			if (spider.getCurrTile() == 0) {
				// Get pos1
				Position pos1 = spider.getRange().get(1);
				// check collide in pos1
				if (spider.collide(dungeon.getEntity(pos1))) {
					// Move to pos1
					spider.setPosition(pos1);
					spider.setCurrTile(1);
				} else {
					// Reverse clockwise
					spider.setClockwise(false);
				}
			}
			// If other positions
			else {
				// Get nextPos
				Position nextPos;
				// int tile = spider.getCurrTile();
				int nextTile = 0;
				if (spider.getCurrTile() == 8) {
					nextTile = 1;
					nextPos = spider.getRange().get(nextTile);
				} else {
					nextTile = spider.getCurrTile() + 1;
					nextPos = spider.getRange().get(nextTile);
				}

				// Check collide
				if (spider.collide(dungeon.getEntity(nextPos))) {
					spider.setPosition(nextPos);
					spider.setCurrTile(nextTile);
				} else {
					// If not, reverse and do nothing
					spider.setClockwise(false);
				}
			}
		} else {
			// If position 0, try to move down
			if (spider.getCurrTile() == 0) {
				// get pos5
				Position pos5 = spider.getRange().get(5);
				// Check collide in pos5
				if (spider.collide(dungeon.getEntity(pos5))) {
					// Move to pos5
					spider.setPosition(pos5);
					spider.setCurrTile(1);
				} else {
					spider.setClockwise(true);
				}
			}
			// Other positions
			else {
				// Get nextPos
				Position nextPos;
				// int tile = spider.getCurrTile();
				int nextTile = 0;
				if (spider.getCurrTile() == 1) {
					nextTile = 8;
					nextPos = spider.getRange().get(nextTile);
				} else {
					nextTile = spider.getCurrTile() - 1;
					nextPos = spider.getRange().get(nextTile);
				}

				// Check collide
				if (spider.collide(dungeon.getEntity(nextPos))) {
					spider.setPosition(nextPos);
					spider.setCurrTile(nextTile);
				} else {
					// If not, reverse and do nothing
					spider.setClockwise(false);
				}
			}
		}
=======
		// TODO Auto-generated method stub
		
>>>>>>> master
	}
	
}
