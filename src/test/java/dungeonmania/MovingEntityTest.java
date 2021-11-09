package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import dungeonmania.allEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MovingEntityTest {

	// SPIDER TESTS

    // Test spawning works properly for spiders
	@Test
	public void testSpiderSpawn() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testSpiderSpawn", "Standard"));

		// Check if spider 1 exists
		Position spiderPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		// Should be here
		Position spiderStart1 = new Position(1,1);
		// Assert spider spawned in correctly
		assertTrue(spiderPos1.equals(spiderStart1));

		// Check if spider 2 exists
		Position spiderPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		// Should be here
		Position spiderStart2 = new Position(1,4);
		// Assert spider spawned in correctly
		assertTrue(spiderPos2.equals(spiderStart2));	
	}

	// // Test a maximum of 4 spiders can spawn int
	// @Test
	// public void testMaxSpiders() {
	// 	DungeonManiaController controller = new DungeonManiaController();
	// 	assertDoesNotThrow(() -> controller.newGame("testSpiderFour.json", "Standard"));
	// 	// Too many spiders (5)
	// 	assertThrows(IllegalArgumentException.class, () -> controller.newGame("testSpiderMax.json", "Standard"));
	// }
	
	// Test movement of spider is correct
	@Test
    public void testSpiderMovement() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testSpiderMovement", "Standard"));

		// Original position of spider
		Position prevPos = controller.getDungeon(0).getEntity("0").getPosition();
		Position currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(prevPos.equals(currPos));
		controller.tick(null, Direction.NONE);

		// Spider moves up 1
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.UP)));
		prevPos = prevPos.translateBy(Direction.UP);
		controller.tick(null, Direction.NONE);

		// Spider moves right from here
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.RIGHT)));
		prevPos = prevPos.translateBy(Direction.RIGHT);
		controller.tick(null, Direction.NONE);

		// DOWN
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.DOWN)));
		prevPos = prevPos.translateBy(Direction.DOWN);
		controller.tick(null, Direction.NONE);

		// DOWN
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.DOWN)));
		prevPos = prevPos.translateBy(Direction.DOWN);
		controller.tick(null, Direction.NONE);

		// Left
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.LEFT)));
		prevPos = prevPos.translateBy(Direction.LEFT);
		controller.tick(null, Direction.NONE);

		// Left
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.LEFT)));
		prevPos = prevPos.translateBy(Direction.LEFT);
		controller.tick(null, Direction.NONE);

		// UP
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.UP)));
		prevPos = prevPos.translateBy(Direction.UP);
		controller.tick(null, Direction.NONE);

		// UP
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.UP)));
		prevPos = prevPos.translateBy(Direction.UP);
		controller.tick(null, Direction.NONE);

		// Right
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.RIGHT)));
		prevPos = prevPos.translateBy(Direction.RIGHT);
		controller.tick(null, Direction.NONE);

		// Right
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.RIGHT)));
		prevPos = prevPos.translateBy(Direction.RIGHT);
		controller.tick(null, Direction.NONE);

    }

	// Test that the door, wall, portal, exit and switch do not affect spider movement
	@Test
	public void testSpiderObstructions() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testSpiderObstruction", "Standard"));

		// Original position of spider
		Position prevPos = controller.getDungeon(0).getEntity("0").getPosition();
		Position currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(prevPos.equals(currPos));
		controller.tick(null, Direction.NONE);

		// Spider moves UP 1
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.UP)));
		prevPos = prevPos.translateBy(Direction.UP);
		controller.tick(null, Direction.NONE);

		// Spider moves right from here
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.RIGHT)));
		prevPos = prevPos.translateBy(Direction.RIGHT);
		controller.tick(null, Direction.NONE);

		// DOWN
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.DOWN)));
		prevPos = prevPos.translateBy(Direction.DOWN);
		controller.tick(null, Direction.NONE);

		// DOWN
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.DOWN)));
		prevPos = prevPos.translateBy(Direction.DOWN);
		controller.tick(null, Direction.NONE);

		// Left
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.LEFT)));
		prevPos = prevPos.translateBy(Direction.LEFT);
		controller.tick(null, Direction.NONE);

		// Left
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.LEFT)));
		prevPos = prevPos.translateBy(Direction.LEFT);
		controller.tick(null, Direction.NONE);

		// UP
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.UP)));
		prevPos = prevPos.translateBy(Direction.UP);
		controller.tick(null, Direction.NONE);

		// UP
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.UP)));
		prevPos = prevPos.translateBy(Direction.UP);
		controller.tick(null, Direction.NONE);

		// Right
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.RIGHT)));
		prevPos = prevPos.translateBy(Direction.RIGHT);
		controller.tick(null, Direction.NONE);

		// Right
		currPos = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos.equals(prevPos.translateBy(Direction.RIGHT)));
		prevPos = prevPos.translateBy(Direction.RIGHT);
		controller.tick(null, Direction.NONE);
	}

	// Test that boulders change the spiders movement in the correct way
	@Test
	public void testSpiderBoulders() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testSpiderBoulder", "Standard"));

		// Original position of spider
		Position prevPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		Position currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(prevPos1.equals(currPos1));

		Position prevPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		Position currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		assertTrue(prevPos2.equals(currPos2));

		controller.tick(null, Direction.NONE);		

		// 1st tick
		// Move spider1 down first
		currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos1.equals(prevPos1.translateBy(Direction.NONE)));
		// assertEquals(currPos1.getY(), prevPos1.getY());
		prevPos1 = prevPos1.translateBy(Direction.NONE);

		// Move spider2 up first
		currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		assertTrue(currPos2.equals(prevPos2.translateBy(Direction.UP)));
		prevPos2 = prevPos2.translateBy(Direction.UP);

		controller.tick(null, Direction.NONE);

		// 2nd tick
		// Move spider1 left
		currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos1.equals(prevPos1.translateBy(Direction.DOWN)));
		prevPos1 = prevPos1.translateBy(Direction.DOWN);

		// Move spider2 right
		currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		assertTrue(currPos2.equals(prevPos2.translateBy(Direction.RIGHT)));
		prevPos2 = prevPos2.translateBy(Direction.RIGHT);


		controller.tick(null, Direction.NONE);


		// 3rd tick
		// Move spider1 up
		currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos1.equals(prevPos1.translateBy(Direction.LEFT)));
		prevPos1 = prevPos1.translateBy(Direction.LEFT);

		// Move spider2 down
		currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		assertTrue(currPos2.equals(prevPos2.translateBy(Direction.DOWN)));
		prevPos2 = prevPos2.translateBy(Direction.DOWN);

		controller.tick(null, Direction.NONE);

		// 4th tick
		// Move spider1 up
		currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos1.equals(prevPos1.translateBy(Direction.UP)));
		prevPos1 = prevPos1.translateBy(Direction.UP);

		// Move spider2 down
		currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		assertTrue(currPos2.equals(prevPos2.translateBy(Direction.DOWN)));
		prevPos2 = prevPos2.translateBy(Direction.DOWN);

		controller.tick(null, Direction.NONE);


		// 5th tick
		// Don't move spider1
		currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos1.equals(prevPos1.translateBy(Direction.UP)));
		prevPos1 = prevPos1.translateBy(Direction.UP);

		// Move spider2 left
		currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		assertTrue(currPos2.equals(prevPos2.translateBy(Direction.LEFT)));
		prevPos2 = prevPos2.translateBy(Direction.LEFT);

		controller.tick(null, Direction.NONE);

		// 6th tick
		// Move spider1 down
		currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos1.equals(prevPos1.translateBy(Direction.NONE)));
		prevPos1 = prevPos1.translateBy(Direction.NONE);

		// Don't move spider2
		currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		assertTrue(currPos2.equals(prevPos2.translateBy(Direction.NONE)));
		prevPos2 = prevPos2.translateBy(Direction.NONE);

		controller.tick(null, Direction.NONE);

		// 7th tick
		// Move spider1 down
		currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos1.equals(prevPos1.translateBy(Direction.DOWN)));
		prevPos1 = prevPos1.translateBy(Direction.DOWN);

		// Move spider2 right
		currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		assertTrue(currPos2.equals(prevPos2.translateBy(Direction.RIGHT)));
		prevPos2 = prevPos2.translateBy(Direction.RIGHT);

		controller.tick(null, Direction.NONE);

		// 8th tick
		// Move spider1 right
		currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos1.equals(prevPos1.translateBy(Direction.DOWN)));
		prevPos1 = prevPos1.translateBy(Direction.DOWN);

		// Move spider2 up
		currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		assertTrue(currPos2.equals(prevPos2.translateBy(Direction.UP)));
		prevPos2 = prevPos2.translateBy(Direction.UP);

		controller.tick(null, Direction.NONE);

		// 9th tick
		// Move spider1 right
		currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos1.equals(prevPos1.translateBy(Direction.RIGHT)));
		prevPos1 = prevPos1.translateBy(Direction.RIGHT);

		// Move spider up
		currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		assertTrue(currPos2.equals(prevPos2.translateBy(Direction.UP)));
		prevPos2 = prevPos2.translateBy(Direction.UP);

		controller.tick(null, Direction.NONE);

		// 10th tick
		// Move spider1 up
		currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos1.equals(prevPos1.translateBy(Direction.RIGHT)));
		prevPos1 = prevPos1.translateBy(Direction.RIGHT);

		// Move spider left
		currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		assertTrue(currPos2.equals(prevPos2.translateBy(Direction.LEFT)));
		prevPos2 = prevPos2.translateBy(Direction.LEFT);

		controller.tick(null, Direction.NONE);

		// 11th tick
		// Move spider1 up
		currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(currPos1.equals(prevPos1.translateBy(Direction.UP)));
		prevPos1 = prevPos1.translateBy(Direction.UP);

		// Don't move spider2
		currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
		assertTrue(currPos2.equals(prevPos2.translateBy(Direction.NONE)));
		prevPos2 = prevPos2.translateBy(Direction.NONE);

		controller.tick(null, Direction.NONE);
	}

	// ZOMBIE TESTS

	// Test that zombie spawns after 20 ticks
	@Test
	public void testZombieSpawn() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testZombieSpawn", "Standard"));
		
		Position zombiePos;
		Position spawnerPos = controller.getDungeon(0).getEntity("1").getPosition();

		// Check if zombie spawns in after 20 ticks
		for (int i = 0; i < 21; i++) {
			// Make sure nothing spawns during this time
			// assertThrows(InvalidActionException.class, () -> controller.getEntity("1"));
			assertFalse(controller.getDungeon(0).entityExists("zombie_toast"));
			controller.tick(null, Direction.RIGHT);
		}

		// Assert that zombie spawned cardinally adjacent
		zombiePos = controller.getDungeon(0).getEntity("3").getPosition();
		assertTrue(Position.isCardinallyAdjacent(zombiePos, spawnerPos));
	}

	// Test that zombie obstructions
	@Test
	public void testZombieObstruction() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testZombieObstruction", "Standard"));

		Position zombiePos1 = controller.getDungeon(0).getEntity("0").getPosition();
		Position zombiePos2 = controller.getDungeon(0).getEntity("1").getPosition();

		Position currPos1;
		Position currPos2;

		// Test for 5 tick that zombie won't get out of the spots
		for (int i = 0; i < 5; i++) {
			currPos1 = controller.getDungeon(0).getEntity("0").getPosition();
			currPos2 = controller.getDungeon(0).getEntity("1").getPosition();
			
			assertTrue(zombiePos1.equals(currPos1));
			assertTrue(zombiePos2.equals(currPos2));
			controller.tick(null, Direction.NONE);
		}
	}

	// Test that zombie movement works / is random
	// Not testing randomness, but testing the the zombie moves to a cardinally
	// adjacent tile
	@Test
	public void testZombieMovement() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testZombieMovement", "Standard"));
		ZombieToast zombie = (ZombieToast) controller.getDungeon(0).getEntity("0");
		int seed = zombie.getSeed();
		Random random = new Random(seed);
		
		// For all 10 ticks, all movements will be random
		for (int i = 0; i < 10; i++) {
			Position prevPos = zombie.getPosition();
			int num = random.nextInt(4);

			Direction dir = Direction.NONE;
			switch (num) {
				case 0:
					dir = Direction.UP;
					break;
				case 1:
					dir = Direction.DOWN;
					break;
				case 2:
					dir = Direction.LEFT;
					break;
				case 3:
					dir = Direction.RIGHT;
					break;
				default:
					break;
			}
			controller.tick(null, Direction.NONE);
			Position currPos = zombie.getPosition();
			assertTrue(currPos.equals(prevPos.translateBy(dir)));

		}
	}

	// MERCENARY TESTS

	// Check mercenary spawns in the entry location after 10 ticks
	@Test
	public void testMercenarySpawn() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testMercenarySpawn", "Hard"));

		// Get player out of the way so mercenary can spawn
		controller.tick(null, Direction.RIGHT);

		// For all 10 ticks, the mercenary will not be spawned in yet
		for (int i = 0; i < 10; i++) {
			// assertThrows(InvalidActionException.class, () -> controller.getEntity("1"));
			assertFalse(controller.getDungeon(0).entityExists("mercenary"));
			controller.tick(null, Direction.LEFT);
		}
		assertTrue(controller.getDungeon(0).entityExists("mercenary") || controller.getDungeon(0).entityExists("assassin"));

	}

	// Check mercenary moves through portal correctly
	@Test
	public void testMercenaryPortal() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testMercenaryPortal", "Standard"));		

		// Move player up through portal
		// Get position of second portal and check if player in correct position
		Position position = controller.getDungeon(0).getEntity("2").getPosition();
		position = position.translateBy(Direction.DOWN);

		controller.tick(null, Direction.NONE);
		// assertEquals(-1, controller.getDungeon(0).getEntity("0").getPosition().getX());
		assertTrue(controller.getDungeon(0).entityExists("mercenary", new Position(4,2)));
	}
	// Check mercenary gets obstructed by
	// Four walls
	@Test
	public void testMercenaryFourWalls() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testMercenaryFourWalls", "Standard"));

		// for five ticks, no movement
		Position position = controller.getDungeon(0).getEntity("0").getPosition();

		for (int i = 0; i < 5; i++) {
			assertTrue(controller.getDungeon(0).entityExists("mercenary", position));
			controller.tick(null, Direction.NONE);
		}
	}

	// Open space
	@Test
	public void testMercenaryMovement() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testMercenaryMovement", "Standard"));
		
		Position mercenary = controller.getDungeon(0).getEntity("1").getPosition();
		
		// one tick, move closer left
		controller.tick(null, Direction.NONE);

		assertTrue(controller.getDungeon(0).entityExists("mercenary", mercenary.translateBy(Direction.LEFT)));

		mercenary = mercenary.translateBy(Direction.LEFT);

		// move closer left
		controller.tick(null, Direction.NONE);

		assertTrue(controller.getDungeon(0).entityExists("mercenary", mercenary.translateBy(Direction.LEFT)));

		mercenary = mercenary.translateBy(Direction.LEFT);

		// move closer left
		controller.tick(null, Direction.NONE);

		assertTrue(controller.getDungeon(0).entityExists("mercenary", mercenary.translateBy(Direction.LEFT)));

		mercenary = mercenary.translateBy(Direction.LEFT);
	}

	// Open space, player moves around, merc follows
	@Test
	public void testMercenaryMovementMoving() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testMercenaryMovement", "Standard"));
		
		Position mercenary = controller.getDungeon(0).getEntity("1").getPosition();

		// Move player down one
		controller.tick(null, Direction.DOWN);

		// player shouldve moved down, same with merc
		assertTrue(controller.getDungeon(0).entityExists("mercenary", mercenary.translateBy(Direction.DOWN)));
		mercenary = mercenary.translateBy(Direction.DOWN);

		// Move player right, merc should've moved left
		controller.tick(null, Direction.RIGHT);

		assertTrue(controller.getDungeon(0).entityExists("mercenary", mercenary.translateBy(Direction.LEFT)));
		mercenary = mercenary.translateBy(Direction.LEFT);

		// Move player down
		controller.tick(null, Direction.DOWN);

		// merc moved down
		assertTrue(controller.getDungeon(0).entityExists("mercenary", mercenary.translateBy(Direction.DOWN)));

	}

	// Check mercenary moves straight line towards player before obstruction
	// After blocked, move player down, make sure merc follows
	@Test
	public void testMercenaryBlocked() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testMercenaryBlocked", "Standard"));

		Position mercenary = controller.getDungeon(0).getEntity("1").getPosition();

		// Moves closer to player
		controller.tick(null, Direction.NONE);

		assertTrue(controller.getDungeon(0).entityExists("mercenary", mercenary.translateBy(Direction.LEFT)));
		mercenary = mercenary.translateBy(Direction.LEFT);
		
		// Check if mercenary is blocked
		controller.tick(null, Direction.NONE);

		// assertEquals(controller.getDungeon(0).getEntity("1").getPosition().getX(), 0);
		assertTrue(controller.getDungeon(0).entityExists("mercenary", mercenary));

		// Check if player moves down, mercenary moves down 1 too
		controller.tick(null, Direction.DOWN);
		assertTrue(controller.getDungeon(0).entityExists("mercenary", mercenary.translateBy(Direction.DOWN)));
	}

	@Test
	public void testMercenaryBlockedFromStart() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testMercenaryBlocked", "Standard"));

		Position mercenary = controller.getDungeon(0).getEntity("1").getPosition();

		// move player down one
		controller.tick(null, Direction.DOWN);

		assertTrue(controller.getDungeon(0).entityExists("mercenary", mercenary.translateBy(Direction.LEFT)));
	}

	// Test bribe with 1 gold
	@Test
	public void testMercenaryBribe() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testMercenaryBribe", "Standard"));
	
		// Pick up gold to right of player
		controller.tick(null, Direction.RIGHT);

		// interact with mercenary
		assertDoesNotThrow(() -> controller.interact("1"));

		// cast into merc, check if ally
		Mercenary merc = (Mercenary) controller.getDungeon(0).getEntity("1");
		assertTrue(merc.getIsAlly());
;

		// wait for merc to move into player
		controller.tick(null, Direction.NONE);
		controller.tick(null, Direction.NONE);

		// Now merc is on player, check he moves around with player
		controller.tick(null, Direction.DOWN);
		Position player = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(controller.getDungeon(0).entityExists("mercenary", player));
		// assertEquals(-1, controller.getDungeon(0).getEntity("1").getPosition().getX());
		// assertTrue(controller.getDungeon(0).entityExists("mercenary", player.translateBy(Direction.UP)));

		controller.tick(null, Direction.RIGHT);
		player = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(controller.getDungeon(0).entityExists("mercenary", player));
	}


	@Test
	public void testAssassinBribe() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testAssassinBribe", "Standard"));
	
		// Pick up gold and one ring to right of player
		controller.tick(null, Direction.RIGHT);

		// interact with mercenary
		assertDoesNotThrow(() -> controller.interact("1"));

		// cast into merc, check if ally
		Assassin ass = (Assassin) controller.getDungeon(0).getEntity("1");
		assertTrue(ass.getIsAlly());

		assertTrue(controller.getDungeonInfo(0).getInventory().isEmpty());

		// wait for merc to move into player
		controller.tick(null, Direction.NONE);
		controller.tick(null, Direction.NONE);

		// Now merc is on player, check he moves around with player
		controller.tick(null, Direction.DOWN);
		Position player = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(controller.getDungeon(0).entityExists("assassin", player));

		controller.tick(null, Direction.RIGHT);
		player = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(controller.getDungeon(0).entityExists("assassin", player));
	}

	@Test
	public void testMercenaryBribeFar() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testMercenaryBribeFar", "Standard"));

		assertThrows(InvalidActionException.class, () -> controller.interact("0"));
	}


	// Test cannot bribe merc, not enough
	@Test
	public void testMercenaryCantBribe() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testMercenaryMovement", "Standard"));
	
		controller.tick(null, Direction.RIGHT);
		controller.tick(null, Direction.RIGHT);
		assertThrows(InvalidActionException.class, () -> controller.interact("1"));
	}
}