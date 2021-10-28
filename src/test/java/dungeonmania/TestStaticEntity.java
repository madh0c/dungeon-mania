package dungeonmania;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import dungeonmania.*;
import dungeonmania.allEntities*
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.Entity;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;


@TestInstance(value = Lifecycle.PER_CLASS)
public class TestStaticEntity {
    
	// TODO WALL TESTS
	@Test
    public void testWallBlocksPlayerMovement() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testWallBlocksPlayerMovementDungeon", "standard"));

		// Extract the player and the wall.
		Entity player = controller.getEntity("0");
		Entity wall = controller.getEntity("1");


		// Confirm start position of Player
		Position playerStart = player.getPosition();
		
		// Move player toward the wall
		controller.tick(null, Direction.RIGHT);

		// Assert the player has not moved from spawn
		assertTrue(player.getPosition.equals(playerStart));
    }

	/**
	 * A player and a mercenary are spawned with a wall in between them in a collinear fashion. The player will walk 
	 * away from the wall in a straight light and the mercenary should fail to move as it should not be able to walk when 
	 * a wall is blocking its path.
	 */
	@Test
    public void testWallBlocksMercenaryMovement() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testWallBlocksMercenaryMovementDungeon", "Standard"));
		
		// Extract the player, wall, and mercenary.
		Entity player = controller.getEntity("0");
		Entity wall = controller.getEntity("1");
		Entity mercenary = controller.getEntity("2");

		// Confirm start position of Player
		Position playerStart = player.getPosition();

		// Confirm start position of Player
		Position mercenaryStart = mercenary.getPosition();
		
		// Move player toward the wall
		controller.tick(null, Direction.RIGHT);

		Position newPlayerPosition = new Position(2, 4);

		// Assert the player has moved but the mercenary hasn't.
		assertTrue(player.getPosition.equals(newPlayerPosition) && mercenary.getPosition.equals(mercenaryStart));
    }

	/**
	 * A zombie toast spawner is created with four walls cardinally adjacent to it. The player will tick 22 times and 
	 * there should be no zombie toast being spawned there are walls in the cardinally positions adjacent to the spawner
	 */
	@Test
    public void testWallBlocksZombieToastSpawn() {
        
		// Spawn: 1 Zombie Toast Spawner in the middle of the map.
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testWallBlocksZombieToastSpawnDungeon", "standard"));

		// Tick player back and forth 22 times
		for (int i = 0; i < 11; i++) {
			controller.tick(null, Direction.RIGHT);
			controller.tick(null, Direction.LEFT);
		}
		//
    }

	/**
	 * A wall, boulder and the player are spawned adjacent to each other in a collinear manner. The character will 
	 * attempt to push the boulder through the wall and the positions of all the entities should not change.
	 */
	@Test
    public void testWallBlocksBoulderMovement() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testWallBlocksBoulderMovementDungeon", "standard"));
    }



	// TODO EXIT TESTS
	/**
	 * The player will spawn on a map with just itself and the exit. Once the player steps onto the map
	 */
	@Test
    public void testPlayerOnExitCompletesGame() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testPlayerOnExitCompletesGameDungeon", "standard"));
    }

	@Test
    public void testPlayerOnExitSubgoalNotSatisfiedI() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testPlayerOnExitSubgoalNotSatisfiedIDungeon", "standard"));
    }

	@Test
    public void testPlayerOnExitSubgoalNotSatisfiedII() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testPlayerOnExitSubgoalNotSatisfiedIIDungeon", "standard"));
    }

	@Test
    public void testPlayerOnExitSubgoalSatisfiedI() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testPlayerOnExitSubgoalSatisfiedIDungeon", "standard"));
    }

	@Test
    public void testPlayerOnExitSubgoalSatisfiedII() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testPlayerOnExitSubgoalSatisfiedIIDungeon", "standard"));
    }



	// TODO BOULDER TESTS
	@Test
    public void testBoulderMovement() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testBoulderMovementDungeon", "standard"));
    }

	@Test
    public void testCantMoveTwoBoulders() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testCantMoveTwoBouldersDungeon", "standard"));
    }

	// TODO SWITCH TESTS
	@Test
    public void testSwitchTriggeredByBoulder() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testSwitchTriggeredByBoulderDungeon", "standard"));
    }

	@Test
    public void testSwitchUntriggered() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testSwitchUntriggeredDungeon", "standard"));
    }



	// TODO DOOR TESTS
	@Test
    public void testPlayerCantMoveThroughDoor() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testPlayerCantMoveThroughDoorDungeon", "standard"));
    }

	@Test
    public void testMercenaryCantMoveThroughDoor() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testMercenaryCantMoveThroughDoorDungeon", "standard"));
    }

	@Test
    public void testSpiderCanMoveThroughDoor() {
        // Task 2
        // Example from the specification
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testSpiderCanMoveThroughDoorDungeon", "standard"));
    }

	@Test
    public void testKeyOpensDoor() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testKeyOpensDoorDungeon", "standard"));
    }



	// TODO TELEPORT TESTS
	@Test
    public void testTeleportPlayerStandard() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testTeleportPlayerStandardDungeon", "standard"));
    }

	@Test
    public void testTeleportPlayerIntoWall() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testTeleportPlayerIntoWallDungeon", "standard"));
    }

	@Test
    public void testTeleportPlayerIntoBoundary() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testTeleportPlayerIntoBoundaryDungeon", "standard"));
    }

	@Test
    public void testPushBoulderIntoPortal() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testPushBoulderIntoPortalDungeon", "standard"));
    }

	@Test
    public void testTeleportMercenaryStandard() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testTeleportMercenaryStandardDungeon", "standard"));
    }

	@Test
    public void testTeleportMercenaryIntoWall() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testTeleportMercenaryIntoWallDungeon", "standard"));
    }

	@Test
    public void testTeleportMercenaryIntoBoundary() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testTeleportMercenaryIntoBoundaryDungeon", "standard"));
    }

	@Test
    public void testSpiderDoesntTeleport() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testSpiderDoesntTeleportDungeon", "standard"));
    }

	@Test
    public void testTeleportZombieToastStandard() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testTeleportZombieToastStandardDungeon", "standard"));
	}

	@Test
    public void testTeleportZombieToastIntoWall() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testTeleportZombieToastIntoWallDungeon", "standard"));
	}

	@Test
    public void testTeleportZombieToastToBoundary() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testTeleportZombieToastToBoundaryDungeon", "standard"));
    }



	@Test
    public void testDestroyZombieToastSpawner() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/testStaticDungeon/testDestroyZombieToastSpawnerDungeon", "standard"));
    }
}
