package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MovingEntityTest {
    // Test spawning works properly for spiders
	@Test
	public void testSpiderSpawn() {
		DungeonManiaController controller = new DungeonManiaController();

		assertDoesNotThrow(() -> controller.newGame("testSpiderSpawn", "standard"));

		// Check if spider 1 exists
		Position spiderPos1 = controller.getEntity("1").getPosition();
		// Should be here
		Position spiderStart1 = new Position(1,1);
		// Assert spider spawned in correctly
		assertTrue(spiderPos1.equals(spiderStart1));

		// Check if spider 2 exists
		Position spiderPos2 = controller.getEntity("2").getPosition();
		// Should be here
		Position spiderStart2 = new Position(1,4);
		// Assert spider spawned in correctly
		assertTrue(spiderPos2.equals(spiderStart2));	
	}

	// Test a maximum of 4 spiders can spawn int
	@Test
	public void testMaxSpiders() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testSpiderFour", "standard"));
		// Too many spiders (5)
		assertThrows(InvalidActionException.class, () -> controller.newGame("testSpiderMax", "standard"));
	}
	
	// Test movement of spider is correct
	@Test
    public void testSpiderMovement() {
        // assertTrue(DungeonManiaController.dungeons().size() > 0);
        // assertTrue(DungeonManiaController.dungeons().contains("maze"));
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("spiderMovement", "standard");

		// Original position of spider
		Position prevPos = controller.getEntity("1").getPosition();
		Position currPos = controller.getEntity("1").getPosition();
		assertTrue(prevPos.equals(currPos));
		controller.tick("", Direction.NONE);

		// Spider moves up 1
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.UP)));
		prevPos = prevPos.translateBy(Direction.UP);
		controller.tick("", Direction.NONE);

		// Spider moves right from here
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.RIGHT)));
		prevPos = prevPos.translateBy(Direction.RIGHT);
		controller.tick("", Direction.NONE);

		// Down
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.DOWN)));
		prevPos = prevPos.translateBy(Direction.DOWN);
		controller.tick("", Direction.NONE);

		// Down
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.DOWN)));
		prevPos = prevPos.translateBy(Direction.DOWN);
		controller.tick("", Direction.NONE);

		// Left
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.LEFT)));
		prevPos = prevPos.translateBy(Direction.LEFT);
		controller.tick("", Direction.NONE);

		// Left
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.LEFT)));
		prevPos = prevPos.translateBy(Direction.LEFT);
		controller.tick("", Direction.NONE);

		// Up
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.UP)));
		prevPos = prevPos.translateBy(Direction.UP);
		controller.tick("", Direction.NONE);

		// Up
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.UP)));
		prevPos = prevPos.translateBy(Direction.UP);
		controller.tick("", Direction.NONE);

		// Right
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.RIGHT)));
		prevPos = prevPos.translateBy(Direction.RIGHT);
		controller.tick("", Direction.NONE);

		// Right
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.RIGHT)));
		prevPos = prevPos.translateBy(Direction.RIGHT);
		controller.tick("", Direction.NONE);

    }

	// Test that the door, wall, portal, exit and switch do not affect spider movement
	@Test
	public void testSpiderObstructions() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("spiderObstruction", "standard");

		// Original position of spider
		Position prevPos = controller.getEntity("1").getPosition();
		Position currPos = controller.getEntity("1").getPosition();
		assertTrue(prevPos.equals(currPos));
		controller.tick("", Direction.NONE);

		// Spider moves up 1
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.UP)));
		prevPos = prevPos.translateBy(Direction.UP);
		controller.tick("", Direction.NONE);

		// Spider moves right from here
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.RIGHT)));
		prevPos = prevPos.translateBy(Direction.RIGHT);
		controller.tick("", Direction.NONE);

		// Down
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.DOWN)));
		prevPos = prevPos.translateBy(Direction.DOWN);
		controller.tick("", Direction.NONE);

		// Down
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.DOWN)));
		prevPos = prevPos.translateBy(Direction.DOWN);
		controller.tick("", Direction.NONE);

		// Left
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.LEFT)));
		prevPos = prevPos.translateBy(Direction.LEFT);
		controller.tick("", Direction.NONE);

		// Left
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.LEFT)));
		prevPos = prevPos.translateBy(Direction.LEFT);
		controller.tick("", Direction.NONE);

		// Up
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.UP)));
		prevPos = prevPos.translateBy(Direction.UP);
		controller.tick("", Direction.NONE);

		// Up
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.UP)));
		prevPos = prevPos.translateBy(Direction.UP);
		controller.tick("", Direction.NONE);

		// Right
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.RIGHT)));
		prevPos = prevPos.translateBy(Direction.RIGHT);
		controller.tick("", Direction.NONE);

		// Right
		currPos = controller.getEntity("1").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.RIGHT)));
		prevPos = prevPos.translateBy(Direction.RIGHT);
		controller.tick("", Direction.NONE);
	}

	// Test that boulders change the spiders movement in the correct way
	@Test
	public void testSpiderBoulders() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("spiderObstruction", "standard");

		// Original position of spider
		Position prevPos1 = controller.getEntity("1").getPosition();
		Position currPos1 = controller.getEntity("1").getPosition();
		assertTrue(prevPos1.equals(currPos1));
		controller.tick("", Direction.NONE);

		// 1st tick
		// Move spider1 down first
		currPos1 = controller.getEntity("1").getPosition();
		assertTrue(currPos1.equals(prevPos1.translateBy(Direction.DOWN)));
		prevPos1 = prevPos1.translateBy(Direction.DOWN);
		controller.tick("", Direction.NONE);


		// 2nd tick
		// Move spider1 left

		// 3rd tick
		// Move spider1 up

		// 4th tick
		// Move spider1 up

		// 5th tick
		// Don't move spider1

		// 6th tick
		// Move spider1 down

		// 7th tick
		// Move spider1 down

		// 8th tick
		// Move spider1 right

		// 9th tick
		// Move spider1 right


	}
}