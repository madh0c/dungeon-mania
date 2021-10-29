package dungeonmania;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import dungeonmania.*;
import dungeonmania.allEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.Entity;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class StaticEntityTest {
    
	// WALL TESTS
    /**
     * A player will try to move into a wall but will be stopped.
     */
	@Test
    public void testWallBlocksPlayerMovement() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testWallBlocksPlayerMovement.json", "Standard"));

        List<EntityResponse> startList = controller.getDungeon(0).generateListEntityResponse();

		// Move player into the wall
		controller.tick(null, Direction.RIGHT);

        // Assert the player doesnt move
        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());
    }

	/**
	 * A player and a mercenary are spawned with a wall in between them in a collinear fashion. The player will walk 
	 * away from the wall in a straight light and the mercenary should fail to move as it should not be able to walk when 
	 * a wall is blocking its path.
	 */
	@Test
    public void testWallBlocksMercenaryMovement() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testWallBlocksMercenaryMovement.json", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(2,0), true);
        EntityResponse startWallInfo = new EntityResponse("1", "wall", new Position(1,0), false);
        EntityResponse startMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,0), false);

        startList.add(startPlayerInfo);
        startList.add(startWallInfo);
        startList.add(startMercenaryInfo);

        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());

		// Move player away from the mercenary.
		controller.tick(null, Direction.RIGHT);

		// Assert the mercenary has not moved from spawn
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(3,0), true);
        EntityResponse expectedWallInfo = new EntityResponse("1", "wall", new Position(1,0), false);
        EntityResponse expectedMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,0), false);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedWallInfo);
        expectedList.add(expectedMercenaryInfo);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());
    }

	/**
	 * A zombie toast spawner is created with four walls cardinally adjacent to it. The player will tick 22 times and 
	 * there should be no zombie toast being spawned as here are walls in the cardinally positions adjacent to the spawner
	 */
	@Test
    public void testWallBlocksZombieToastSpawn() {
        
		// Spawn 1 Zombie Toast Spawner in the middle of the map.
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testWallBlocksZombieToastSpawn.json", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(6,0), true);
        EntityResponse startSpawnerInfo = new EntityResponse("1", "zombie_toast_spawner", new Position(1,1), false);
        EntityResponse startWall1Info = new EntityResponse("2", "wall", new Position(0,1), false);
        EntityResponse startWall2Info = new EntityResponse("3", "wall", new Position(1,0), false);
        EntityResponse startWall3Info = new EntityResponse("4", "wall", new Position(1,2), false);
        EntityResponse startWall4Info = new EntityResponse("5", "wall", new Position(2,1), false);

        startList.add(startPlayerInfo);
        startList.add(startSpawnerInfo);
        startList.add(startWall1Info);
        startList.add(startWall2Info);
        startList.add(startWall3Info);
        startList.add(startWall4Info);

        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());

		// Tick player back and forth 22 times
		for (int i = 0; i < 11; i++) {
			controller.tick(null, Direction.RIGHT);
			controller.tick(null, Direction.LEFT);
		}

        // There should be no change from the start after 22 ticks as the player should be at its starting position and 
        // a zombie toast should not spawn.
        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());
    }

	/**
	 * A wall, boulder and the player are spawned adjacent to each other in a collinear manner. The character will 
	 * attempt to push the boulder through the wall and the positions of all the entities should not change.
	 */
	@Test
    public void testWallBlocksBoulderMovement() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testWallBlocksBoulderMovement.json", "standard"));
        
        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startBoulderInfo = new EntityResponse("1", "boulder", new Position(1,0), true);
        EntityResponse startWallInfo = new EntityResponse("2", "wall", new Position(2,0), false);
 
        startList.add(startPlayerInfo);
        startList.add(startBoulderInfo);
        startList.add(startWallInfo);
 
        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());
         
        // Make player try to push the boulder into the wall.
		controller.tick(null, Direction.RIGHT);

		// Assert boulder cannot be pushed through wall
        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * A spider will move onto a wall.
	 */
	@Test
    public void testSpiderCanMoveThroughWall() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testSpiderCanMoveThroughWall.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startWallInfo = new EntityResponse("1", "wall", new Position(5,1), false);
        EntityResponse startSpiderInfo = new EntityResponse("2", "spider", new Position(5,0), false);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startWallInfo);
        expectedStartList.add(startSpiderInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert the spider coincides with wall
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse expectedWallInfo = new EntityResponse("1", "wall", new Position(5,1), false);
        EntityResponse expectedSpiderInfo = new EntityResponse("2", "spider", new Position(5,1), false);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedWallInfo);
        expectedList.add(expectedSpiderInfo);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());
    }




    // EXIT TESTS
	/**
	 * The player will spawn on a map with just itself and the exit. Once the player steps onto the exit, the game should be over
	 */
	@Test
    public void testPlayerOnExitCompletesGame() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testPlayerOnExitCompletesGame.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startExitInfo = new EntityResponse("1", "exit", new Position(1,0), false);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startExitInfo);
        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        // Move player onto the exit
		controller.tick(null, Direction.RIGHT);

		// Assert the goals are satisfied and the game is over
        String currentGoals = controller.getDungeon(0).getGoals();
        assertEquals(true, currentGoals.isEmpty());
        // assertEquals(true, controller.getDungeon(0).isComplete())
    }

    /**
	 * The player will spawn on a map with itself, a mercenary and the exit. The player steps onto the exit without killing 
     * the mercenary and the game should not end.
	 */
	@Test
    public void testPlayerOnExitSubgoalNotSatisfiedI() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testPlayerOnExitSubgoalNotSatisfiedI.json", "Standard"));
        
        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startExitInfo = new EntityResponse("1", "exit", new Position(1,0), false);
        EntityResponse startMercenaryInfo = new EntityResponse("2", "mercenary", new Position(8,0), false);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startExitInfo);
        expectedStartList.add(startMercenaryInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        // Move player onto the exit
		controller.tick(null, Direction.RIGHT);

        // Assert the goals are not satisfied and the game is not over
        String currentGoals = controller.getDungeon(0).getGoals();
        assertEquals(false, currentGoals.isEmpty());
        // assertEquals(false, controller.getDungeon(0).isComplete());
    }

    /**
	 * The player will spawn on a map with itself, a treasure and the exit. The player steps onto the exit without claiming 
     * the treasure and the game should not end.
	 */
	@Test
    public void testPlayerOnExitSubgoalNotSatisfiedII() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testPlayerOnExitSubgoalNotSatisfiedII.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startExitInfo = new EntityResponse("1", "exit", new Position(1,0), false);
        EntityResponse startTreasureInfo = new EntityResponse("2", "treasure", new Position(8,0), true);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startExitInfo);
        expectedStartList.add(startTreasureInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        // Move player onto the exit
		controller.tick(null, Direction.RIGHT);

        // Assert the goals are not satisfied and the game is not over
        String currentGoals = controller.getDungeon(0).getGoals();
        assertEquals(false, currentGoals.isEmpty());
        // assertEquals(false, controller.getDungeon("0").isComplete);
    }

    /**
	 * The player will spawn on a map with itself, a mercenary and the exit. The player steps onto the exit after killing 
     * the mercenary and the game should end.
	 */
	@Test
    public void testPlayerOnExitSubgoalSatisfiedI() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testPlayerOnExitSubgoalSatisfiedI.json", "Standard"));


        // Assert that a player is spawned with a mercenary and an exit
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startMercenaryInfo = new EntityResponse("1", "mercenary", new Position(1,0), false);
        EntityResponse startExitInfo = new EntityResponse("2", "exit", new Position(3,0), false);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startMercenaryInfo);
        expectedStartList.add(startExitInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        // Make the player kill the mercenary
		controller.tick(null, Direction.RIGHT);

        // Assert the player has killed the mercenary
        List<EntityResponse> expectedEndList = new ArrayList<EntityResponse>();

        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse endExitInfo = new EntityResponse("2", "exit", new Position(3,0), false);

        expectedEndList.add(endPlayerInfo);
        expectedEndList.add(endExitInfo);

        assertEquals(expectedEndList, controller.getDungeon(0).generateListEntityResponse());

        // Make player step on the exit
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);

        // Assert the goals are satisfied and the game is over
        String currentGoals = controller.getDungeon(0).getGoals();
        assertEquals(true, currentGoals.isEmpty());
        // assertEquals(true, controller.getDungeon(0).isComplete());
    }

    /**
	 * The player will spawn on a map with itself, a treasure and the exit. The player steps onto the exit after claiming 
     * the treasure and the game should end.
	 */
	@Test
    public void testPlayerOnExitSubgoalSatisfiedII() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testPlayerOnExitSubgoalSatisfiedII.json", "Standard"));


        // Assert that a player is spawned with a treasure and an exit
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startTreasureInfo = new EntityResponse("1", "treasure", new Position(1,0), true);
        EntityResponse startExitInfo = new EntityResponse("2", "exit", new Position(3,0), false);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startTreasureInfo);
        expectedStartList.add(startExitInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());


        // Make the player claim the treasure
		controller.tick(null, Direction.RIGHT);

        
        // Assert the treasure is off the map
        List<EntityResponse> expectedEndList = new ArrayList<EntityResponse>();

        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse endExitInfo = new EntityResponse("2", "exit", new Position(3,0), false);

        expectedEndList.add(startPlayerInfo);
        expectedEndList.add(startExitInfo);

        assertEquals(expectedEndList, controller.getDungeon(0).generateListEntityResponse());

        // Assert the treasure is in the inventory
        List<ItemResponse> expectedInventory = new ArrayList<ItemResponse>();
        ItemResponse treasureInfo = new ItemResponse("1", "treasure");
        expectedInventory.add(treasureInfo);

        assertEquals(expectedInventory, controller.getDungeon(0).generateListInventoryResponse());


        // Make player step on the exit
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);

        // Assert the goals are satisfied and the game is over
        String currentGoals = controller.getDungeon(0).getGoals();
        assertEquals(true, currentGoals.isEmpty());
        // assertEquals(true, controller.getDungeon(0).isComplete())
    }




	// BOULDER TESTS
    /**
	 * The player will spawn on a map with itself, and a boulder. The player pushes the boulder and the positions of both 
     * the player and the boulder should change.
	 */
	@Test
    public void testBoulderMovement() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testBoulderMovement.json", "Standard"));

        // Move player into the boulder
		controller.tick(null, Direction.RIGHT);

		// Assert the boulder and players have moved from spawn
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse expectedBoulderInfo = new EntityResponse("1", "boulder", new Position(2,0), true);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedBoulderInfo);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * The player will spawn on a map with itself, and two adjacent boulders. The player pushes the closest boulder into the 
     * other and there should be no change in position for all entities.
	 */
	@Test
    public void testCantMoveTwoBoulders() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testCantMoveTwoBoulders.json", "Standard"));

        List<EntityResponse> startList = controller.getDungeon(0).generateListEntityResponse();

        // Assert the spawn positions of player and boulders
        List<EntityResponse> expectedstartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startBoulder1Info = new EntityResponse("1", "boulder", new Position(1,0), true);
        EntityResponse startBoulder2Info = new EntityResponse("2", "boulder", new Position(2,0), true);

        expectedstartList.add(startPlayerInfo);
        expectedstartList.add(startBoulder1Info);
        expectedstartList.add(startBoulder2Info);

        assertEquals(expectedstartList, controller.getDungeon(0).generateListEntityResponse());

        // Move player into the first boulder
		controller.tick(null, Direction.RIGHT);

        // Asser players havent moved from spawn.
        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * A spider cant move onto a boulder.
	 */
	@Test
    public void testSpiderCantMoveThroughBoulder() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testSpiderCantMoveThroughBoulder.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startBoulderInfo = new EntityResponse("1", "boulder", new Position(5,1), true);
        EntityResponse startSpiderInfo = new EntityResponse("2", "spider", new Position(5,0), false);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startBoulderInfo);
        expectedStartList.add(startSpiderInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert the spider doesnt coincides with the voulder
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse expectedBoulderInfo = new EntityResponse("1", "door", new Position(5,1), true);
        EntityResponse expectedSpiderInfo = new EntityResponse("2", "spider", new Position(5,0), false);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedBoulderInfo);
        expectedList.add(expectedSpiderInfo);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * The player will spawn on a map with itself, and a boulder between itself and a boundary. The player pushes the 
     * boulder into the boundary and there should be no change in position for all entities.
	 */
    @Test
    public void testCantMoveBoulderBoundary() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testCantMoveBoulderBoundary.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startBoulderInfo = new EntityResponse("1", "boulder", new Position(1,0), true);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startBoulderInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert nothing changes
        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

    }  




	// SWITCH TESTS
    /**
	 * The player will push a boulder onto a switch and activate it.
	 */
	@Test
    public void testSwitchTriggeredByBoulder() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testSwitchTriggerUntrigger.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startBoulderInfo = new EntityResponse("1", "boulder", new Position(1,0), true);
        EntityResponse startSwitchInfo = new EntityResponse("2", "switch", new Position(2,0, -1), true);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startBoulderInfo);
        expectedStartList.add(startSwitchInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        Map<String, Entity> startEntities = controller.getDungeon(0).getEntities();

        Switch startSwitch = (Switch)startEntities.get("2");
        
        boolean startSwitchStatus = startSwitch.getStatus();

        assertEquals(false, startSwitchStatus);

        // Make the player move the boulder onto the switch
		controller.tick(null, Direction.RIGHT);

		// Assert the player and boulders moved.
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse expectedBoulder1Info = new EntityResponse("1", "boulder", new Position(2,0), true);
        EntityResponse expectedBoulder2Info = new EntityResponse("2", "switch", new Position(2,0, -1), true);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedBoulder1Info);
        expectedList.add(expectedBoulder2Info);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());

        Map<String, Entity> endEntities = controller.getDungeon(0).getEntities();

        Switch endSwitch = (Switch)endEntities.get("2");
        
        boolean endSwitchStatus = endSwitch.getStatus();

        assertEquals(true, endSwitchStatus);
    }

    /**
	 * The player will push a boulder onto a switch activate it, then push it off and deactivate it.
	 */
	@Test
    public void testSwitchUntriggered() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testSwitchTriggerUntrigger.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startBoulderInfo = new EntityResponse("1", "boulder", new Position(1,0), true);
        EntityResponse startSwitchInfo = new EntityResponse("2", "switch", new Position(2,0, -1), true);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startBoulderInfo);
        expectedStartList.add(startSwitchInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        Map<String, Entity> startEntities = controller.getDungeon(0).getEntities();

        Switch startSwitch = (Switch)startEntities.get("2");
        
        boolean startSwitchStatus = startSwitch.getStatus();

        assertEquals(false, startSwitchStatus);

        // Make the player move the boulder onto the switch
		controller.tick(null, Direction.RIGHT);

		// Assert the player and boulders moved.
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse expectedBoulderInfo = new EntityResponse("1", "boulder", new Position(2,0), true);
        EntityResponse expectedSwitchInfo = new EntityResponse("2", "switch", new Position(2,0, -1), true);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedBoulderInfo);
        expectedList.add(expectedSwitchInfo);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());

        Map<String, Entity> midEntities = controller.getDungeon(0).getEntities();

        Switch midSwitch = (Switch)midEntities.get("2");
        
        boolean midSwitchStatus = midSwitch.getStatus();

        assertEquals(true, midSwitchStatus);

        // Make the player move the boulder away from the switch
		controller.tick(null, Direction.RIGHT);

		// Assert the player and boulders moved.
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(2,0), true);
        EntityResponse endBoulderInfo = new EntityResponse("1", "boulder", new Position(3,0), true);
        EntityResponse endSwitchInfo = new EntityResponse("2", "switch", new Position(2,0, -1), true);

        endList.add(endPlayerInfo);
        endList.add(endBoulderInfo);
        endList.add(endSwitchInfo);

        assertEquals(endList, controller.getDungeon(0).generateListEntityResponse());

        Map<String, Entity> endEntities = controller.getDungeon(0).getEntities();

        Switch endSwitch = (Switch)endEntities.get("2");
        
        boolean endSwitchStatus = endSwitch.getStatus();

        assertEquals(false, endSwitchStatus);
    }

    /**
	 * A spider will move onto a switch.
	 */
	@Test
    public void testSpiderCanMoveThroughSwitch() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testSpiderCanMoveThroughSwitch.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startSwitchInfo = new EntityResponse("1", "switch", new Position(5,1, -1), true);
        EntityResponse startSpiderInfo = new EntityResponse("2", "spider", new Position(5,0), false);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startSwitchInfo);
        expectedStartList.add(startSpiderInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        // Tick player.
		controller.tick(null, Direction.RIGHT);


		// Assert the spider coincides with door
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse expectedSwitchInfo = new EntityResponse("1", "door", new Position(5,1, -1), true);
        EntityResponse expectedSpiderInfo = new EntityResponse("2", "spider", new Position(5,1), false);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedSwitchInfo);
        expectedList.add(expectedSpiderInfo);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());
    }




	// DOOR TESTS
    /**
	 * The player will get stopped by a door if it doesnt possess a key.
	 */
	@Test
    public void testPlayerCantMoveThroughDoor() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testPlayerCantMoveThroughDoor.json", "Standard"));

        // Move player into the door
		controller.tick(null, Direction.RIGHT);

		// Assert the player has not moved from spawn
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse expectedDoorInfo = new EntityResponse("1", "door", new Position(1,0), true);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedDoorInfo);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * A mercenary will fail to move through a door.
	 */
	@Test
    public void testMercenaryCantMoveThroughDoor() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testMercenaryCantMoveThroughDoor.json", "Standard"));

        // Move player away from the mercenary.
		controller.tick(null, Direction.RIGHT);

		// Assert the mercenary has not moved from spawn
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(2,0), true);
        EntityResponse expectedDoorInfo = new EntityResponse("1", "door", new Position(1,0), true);
        EntityResponse expectedMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,0), false);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedDoorInfo);
        expectedList.add(expectedMercenaryInfo);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * A spider will move onto a door.
	 */
	@Test
    public void testSpiderCanMoveThroughDoor() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testSpiderCanMoveThroughDoor.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startDoorInfo = new EntityResponse("1", "door", new Position(5,1), true);
        EntityResponse startSpiderInfo = new EntityResponse("2", "spider", new Position(5,0), false);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startDoorInfo);
        expectedStartList.add(startSpiderInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert the spider coincides with door
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse expectedDoorInfo = new EntityResponse("1", "door", new Position(5,1), true);
        EntityResponse expectedSpiderInfo = new EntityResponse("2", "spider", new Position(5,1), false);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedDoorInfo);
        expectedList.add(expectedSpiderInfo);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * The player will pick up a key and open a door.
	 */
	@Test
    public void testKeyOpensDoor() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testKeyOpensDoor.json", "Standard"));

        // Assert that a player is spawned with a treasure and an exit
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startKeyInfo = new EntityResponse("1", "key", new Position(1,0), true);
        EntityResponse startDoorInfo = new EntityResponse("2", "door", new Position(2,0), true);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startKeyInfo);
        expectedStartList.add(startDoorInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        // Make the player claim the key
		controller.tick(null, Direction.RIGHT);

        // Assert the treasure is off the map
        List<EntityResponse> midList = new ArrayList<EntityResponse>();

        EntityResponse midPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse midDoorInfo = new EntityResponse("2", "door", new Position(2,0), true);

        midList.add(midPlayerInfo);
        midList.add(midDoorInfo);

        assertEquals(midList, controller.getDungeon(0).generateListEntityResponse());

        // Assert the key is in the inventory
        List<ItemResponse> expectedInventory1 = new ArrayList<ItemResponse>();
        ItemResponse keyInfo = new ItemResponse("1", "key");
        expectedInventory1.add(keyInfo);
        

        assertEquals(expectedInventory1, controller.getDungeon(0).generateListInventoryResponse());

        // Make player step on the door
        controller.tick(null, Direction.RIGHT);

        // Assert the key is out of the inventory
        List<ItemResponse> expectedInventory2 = new ArrayList<ItemResponse>();
        assertEquals(expectedInventory2, controller.getDungeon(0).generateListInventoryResponse());

        // Assert the player is on the door
        List<EntityResponse> expectedEndList = new ArrayList<EntityResponse>();

        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(2,0), true);
        EntityResponse endDoorInfo = new EntityResponse("2", "door", new Position(2,0), true);

        expectedEndList.add(startPlayerInfo);
        expectedEndList.add(endDoorInfo);

        assertEquals(expectedEndList, controller.getDungeon(0).generateListEntityResponse());
    }




	// TELEPORT TESTS
    /**
	 * The player will successfully teleport and maintain momentum.
	 */
	@Test
    public void testTeleportPlayerStandard() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testTeleportPlayerStandard.json", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startPortal1Info = new EntityResponse("1", "portal", new Position(1,0), false);
        EntityResponse startPortal2Info = new EntityResponse("2", "portal", new Position(4,0), false);
 
        startList.add(startPlayerInfo);
        startList.add(startPortal1Info);
        startList.add(startPortal2Info);

        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert the spider coincides with door
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(5,0), true);
        EntityResponse expectedPortal1Info = new EntityResponse("1", "portal", new Position(1,0), false);
        EntityResponse expectedPortal2Info = new EntityResponse("2", "portal", new Position(4,0), false);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedPortal1Info);
        expectedList.add(expectedPortal2Info);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * The player will fail to teleport into a wall but the world will tick
	 */
	@Test
    public void testTeleportPlayerIntoWall() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testTeleportPlayerIntoWall.json", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startPortal1Info = new EntityResponse("1", "portal", new Position(1,0), false);
        EntityResponse startPortal2Info = new EntityResponse("2", "portal", new Position(4,0), false);
        EntityResponse startWallInfo = new EntityResponse("3", "wall", new Position(5,0), false);
        EntityResponse startSpiderInfo = new EntityResponse("4", "spider", new Position(2,0), false);

        startList.add(startPlayerInfo);
        startList.add(startPortal1Info);
        startList.add(startPortal2Info);
        startList.add(startWallInfo);
        startList.add(startSpiderInfo);

        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());

		// Try to move player into the portal
		controller.tick(null, Direction.RIGHT);

        // Assert player doesnt move but spider ticks
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse endPortal1Info = new EntityResponse("1", "portal", new Position(1,0), false);
        EntityResponse endPortal2Info = new EntityResponse("2", "portal", new Position(4,0), false);
        EntityResponse endWallInfo = new EntityResponse("3", "wall", new Position(5,0), false);
        EntityResponse endSpiderInfo = new EntityResponse("4", "spider", new Position(2,1), false);

        endList.add(endPlayerInfo);
        endList.add(endPortal1Info);
        endList.add(endPortal2Info);
        endList.add(endWallInfo);
        endList.add(endSpiderInfo);
        
       assertEquals(endList, controller.getDungeon(0).generateListEntityResponse());
    }

    
    /**
	 * The player will fail to teleport into the boundary.
	 */
	@Test
    public void testTeleportPlayerIntoBoundary() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testTeleportPlayerIntoBoundary.json", "Standard"));

		// Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startPortal1Info = new EntityResponse("1", "portal", new Position(1,0), false);
        EntityResponse startPortal2Info = new EntityResponse("2", "portal", new Position(8,0), false);

        startList.add(startPlayerInfo);
        startList.add(startPortal1Info);
        startList.add(startPortal2Info);

        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());

		// Try to move player into the portal
		controller.tick(null, Direction.RIGHT);

        // Assert the player doesnt move
        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * The player will push a boulder through a portal.
	 */
	@Test
    public void testPushBoulderIntoPortal() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testPushBoulderIntoPortal.json", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startPortal1Info = new EntityResponse("1", "portal", new Position(2,0), false);
        EntityResponse startPortal2Info = new EntityResponse("2", "portal", new Position(5,0), false);
        EntityResponse startBoulderInfo = new EntityResponse("3", "boulder", new Position(1,0), true);
 
        startList.add(startPlayerInfo);
        startList.add(startPortal1Info);
        startList.add(startPortal2Info);
        startList.add(startBoulderInfo);
 
        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());
 
        // Try to move player into the portal
        controller.tick(null, Direction.RIGHT);
 
        // Assert boulder teleports
        List<EntityResponse> endList = new ArrayList<EntityResponse>();
 
        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse endPortal1Info = new EntityResponse("1", "portal", new Position(2,0), false);
        EntityResponse endPortal2Info = new EntityResponse("2", "portal", new Position(5,0), false);
        EntityResponse endBoulderInfo = new EntityResponse("3", "boulder", new Position(6,0), true);
 
        endList.add(startPlayerInfo);
        endList.add(startPortal1Info);
        endList.add(startPortal2Info);
        endList.add(endBoulderInfo);
         
        assertEquals(endList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * The player will fail to push a boulder through a portal and into the boundary.
	 */
	@Test
    public void testCantTeleportBoulderIntoBoundary() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testCantTeleportBoulderIntoBoundary.json", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startPortal1Info = new EntityResponse("1", "portal", new Position(2,0), false);
        EntityResponse startPortal2Info = new EntityResponse("2", "portal", new Position(5,0), false);
        EntityResponse startBoulderInfo = new EntityResponse("3", "boulder", new Position(1,0), true);
 
        startList.add(startPlayerInfo);
        startList.add(startPortal1Info);
        startList.add(startPortal2Info);
        startList.add(startBoulderInfo);
 
        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());
 
        // Try to move player into the portal
        controller.tick(null, Direction.RIGHT);
 
        // Assert boulder doesnt move as it would teleport into the boundary
        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * A mercenary will teleport through a portal and maintain momentum.
	 */
	@Test
    public void testTeleportMercenaryStandard() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testTeleportMercenaryStandard.json", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(10,0), true);
        EntityResponse startPortal1Info = new EntityResponse("1", "portal", new Position(2,0), false);
        EntityResponse startPortal2Info = new EntityResponse("2", "portal", new Position(5,0), false);
        EntityResponse startMercenaryInfo = new EntityResponse("3", "mercenary", new Position(1,0), false);

        startList.add(startPlayerInfo);
        startList.add(startPortal1Info);
        startList.add(startPortal2Info);
        startList.add(startMercenaryInfo);

        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());

        // Make mercenary follow player into a portal
        controller.tick(null, Direction.LEFT);

        // Assert the mercenary maintains momentum through the portal
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(9,0), true);
        EntityResponse endPortal1Info = new EntityResponse("1", "portal", new Position(2,0), false);
        EntityResponse endPortal2Info = new EntityResponse("2", "portal", new Position(5,0), false);
        EntityResponse endMercenaryInfo = new EntityResponse("3", "mercenary", new Position(6,0), false);

        endList.add(endPlayerInfo);
        endList.add(endPortal1Info);
        endList.add(endPortal2Info);
        endList.add(endMercenaryInfo);
        
        assertEquals(endList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * A mercenary will fail to teleport into a wall.
	 */
	@Test
    public void testTeleportMercenaryIntoWall() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testTeleportMercenaryIntoWall.json", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(10,0), true);
        EntityResponse startPortal1Info = new EntityResponse("1", "portal", new Position(2,0), false);
        EntityResponse startPortal2Info = new EntityResponse("2", "portal", new Position(5,0), false);
        EntityResponse startMercenaryInfo = new EntityResponse("3", "mercenary", new Position(1,0), false);
        EntityResponse startWallInfo = new EntityResponse("4", "wall", new Position(6,0), false);

        startList.add(startPlayerInfo);
        startList.add(startPortal1Info);
        startList.add(startPortal2Info);
        startList.add(startMercenaryInfo);
        startList.add(startWallInfo);

        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());

        // Make mercenary follow player into a portal
        controller.tick(null, Direction.LEFT);

        // Assert the mercenary does not go through the portal
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(9,0), true);
        EntityResponse endPortal1Info = new EntityResponse("1", "portal", new Position(2,0), false);
        EntityResponse endPortal2Info = new EntityResponse("2", "portal", new Position(5,0), false);
        EntityResponse endMercenaryInfo = new EntityResponse("3", "mercenary", new Position(1,0), false);
        EntityResponse endWallInfo = new EntityResponse("4", "wall", new Position(6,0), false);

        endList.add(endPlayerInfo);
        endList.add(endPortal1Info);
        endList.add(endPortal2Info);
        endList.add(endMercenaryInfo);
        endList.add(endWallInfo);
        
        assertEquals(endList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * A mercenary will fail to teleport into the boundary.
	 */
	@Test
    public void testTeleportMercenaryIntoBoundary() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testTeleportMercenaryIntoBoundary.json", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(5,0), true);
        EntityResponse startPortal1Info = new EntityResponse("1", "portal", new Position(1,0), false);
        EntityResponse startPortal2Info = new EntityResponse("2", "portal", new Position(8,0), false);
        EntityResponse startMercenaryInfo = new EntityResponse("3", "mercenary", new Position(0,0), false);

        startList.add(startPlayerInfo);
        startList.add(startPortal1Info);
        startList.add(startPortal2Info);
        startList.add(startMercenaryInfo);

        assertEquals(startList, controller.getDungeon(0).generateListEntityResponse());

		// Move player away from the mercenary.
		controller.tick(null, Direction.LEFT);

		// Assert the mercenary has not moved from spawn
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(4,0), true);
        EntityResponse expectedPortal1Info = new EntityResponse("1", "wall", new Position(1,0), false); 
        EntityResponse expectedPortal2Info = new EntityResponse("2", "portal", new Position(8,0), false);
        EntityResponse expectedMercenaryInfo = new EntityResponse("3", "mercenary", new Position(0,0), false);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedPortal1Info);
        expectedList.add(expectedPortal2Info);
        expectedList.add(expectedMercenaryInfo);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * A spider will be unaffected by a portal.
	 */    
	@Test
    public void testSpiderDoesntTeleport() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testSpiderDoesntTeleport.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startPortal1Info = new EntityResponse("1", "portal", new Position(5,1), false);
        EntityResponse startPortal2Info = new EntityResponse("2", "portal", new Position(9,1), false);
        EntityResponse startSpiderInfo = new EntityResponse("3", "spider", new Position(5,0), false);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startPortal1Info);
        expectedStartList.add(startPortal2Info);
        expectedStartList.add(startSpiderInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert the spider coincides with door
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse expectedPortal1Info = new EntityResponse("1", "portal", new Position(5,1), false);
        EntityResponse expectedPortal2Info = new EntityResponse("2", "portal", new Position(9,1), false);
        EntityResponse expectedSpiderInfo = new EntityResponse("3", "spider", new Position(5,1), false);
        
        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedPortal1Info);
        expectedList.add(expectedPortal2Info);
        expectedList.add(expectedSpiderInfo);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * A zombie toast will teleport and maintain momentum.
	 */
	@Test
    public void testTeleportZombieToastStandard() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testTeleportZombieToastStandard.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startWall1Info = new EntityResponse("1", "wall", new Position(1,1), false);
        EntityResponse startWall2Info = new EntityResponse("2", "wall", new Position(2,0), false);
        EntityResponse startWall3Info = new EntityResponse("3", "wall", new Position(3,1), false);
        EntityResponse startPortal1Info = new EntityResponse("4", "portal", new Position(2,2), false);
        EntityResponse startPortal2Info = new EntityResponse("5", "portal", new Position(2,4), false);
        EntityResponse startSpawnInfo = new EntityResponse("6", "zombie_toast_spawner", new Position(2,1), false);
 
        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startWall1Info);
        expectedStartList.add(startWall2Info);
        expectedStartList.add(startWall3Info);
        expectedStartList.add(startPortal1Info);
        expectedStartList.add(startPortal2Info);
        expectedStartList.add(startSpawnInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());
 
        // Tick player 20 times.
        for (int i = 0; i < 10; i++) {
            controller.tick(null, Direction.UP);
            controller.tick(null, Direction.DOWN);
        }
 
        // Assert the zombie toast teleports correctly
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();
 
        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse endWall1Info = new EntityResponse("1", "wall", new Position(1,1), false);
        EntityResponse endWall2Info = new EntityResponse("2", "wall", new Position(2,0), false);
        EntityResponse endWall3Info = new EntityResponse("3", "wall", new Position(3,1), false);
        EntityResponse endPortal1Info = new EntityResponse("4", "portal", new Position(2,2), false);
        EntityResponse endPortal2Info = new EntityResponse("5", "portal", new Position(2,4), false);
        EntityResponse endSpawnInfo = new EntityResponse("6", "zombie_toast_spawner", new Position(2,1), false);
        EntityResponse endZombInfo = new EntityResponse("7", "zombie_toast", new Position(2,5), false);

        expectedList.add(endPlayerInfo);
        expectedList.add(endWall1Info);
        expectedList.add(endWall2Info);
        expectedList.add(endWall3Info);
        expectedList.add(endPortal1Info);
        expectedList.add(endPortal2Info);
        expectedList.add(endSpawnInfo);
        expectedList.add(endZombInfo);

        assertEquals(expectedList, controller.getDungeon(0).generateListEntityResponse());
	}

    /**
	 * A zombie toast will fail to teleport into a wall.
	 */
	@Test
    public void testTeleportZombieToastIntoWall() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testTeleportZombieToastIntoWall.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startWall1Info = new EntityResponse("1", "wall", new Position(1,1), false);
        EntityResponse startWall2Info = new EntityResponse("2", "wall", new Position(2,0), false);
        EntityResponse startWall3Info = new EntityResponse("3", "wall", new Position(3,1), false);
        EntityResponse startPortal1Info = new EntityResponse("4", "portal", new Position(2,2), false);
        EntityResponse startPortal2Info = new EntityResponse("5", "portal", new Position(2,4), false);
        EntityResponse startSpawnInfo = new EntityResponse("6", "zombie_toast_spawner", new Position(2,1), false);
        EntityResponse startWall4Info = new EntityResponse("7", "wall", new Position(2,5), false);
 
        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startWall1Info);
        expectedStartList.add(startWall2Info);
        expectedStartList.add(startWall3Info);
        expectedStartList.add(startPortal1Info);
        expectedStartList.add(startPortal2Info);
        expectedStartList.add(startSpawnInfo);
        expectedStartList.add(startWall4Info);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());
 
        // Tick player 20 times.
        for (int i = 0; i < 10; i++) {
            controller.tick(null, Direction.UP);
            controller.tick(null, Direction.DOWN);
        }
 
        // Assert the zombie toast doesnt spawn as it will teleport into the wall. Furthermore, nothing should change 
        // from the initial entityList as nothing is created and the player returns to its starting position. 
        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());
	}

    /**
	 * A zombie toast will fail to teleport into the boundary.
	 */
	@Test
    public void testTeleportZombieToastToBoundary() {
        // Task 2
        // Example from the specification
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testTeleportZombieToastToBoundary.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startWall1Info = new EntityResponse("1", "wall", new Position(1,1), false);
        EntityResponse startWall2Info = new EntityResponse("2", "wall", new Position(2,0), false);
        EntityResponse startWall3Info = new EntityResponse("3", "wall", new Position(3,1), false);
        EntityResponse startPortal1Info = new EntityResponse("4", "portal", new Position(2,2), false);
        EntityResponse startPortal2Info = new EntityResponse("5", "portal", new Position(2,4), false);
        EntityResponse startSpawnInfo = new EntityResponse("6", "zombie_toast_spawner", new Position(2,1), false);
 
        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startWall1Info);
        expectedStartList.add(startWall2Info);
        expectedStartList.add(startWall3Info);
        expectedStartList.add(startPortal1Info);
        expectedStartList.add(startPortal2Info);
        expectedStartList.add(startSpawnInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());
 
        // Tick player 20 times.
        for (int i = 0; i < 10; i++) {
            controller.tick(null, Direction.UP);
            controller.tick(null, Direction.DOWN);
        }
 
        // Assert the zombie toast never spawns as it will automatically teleport into the boundary
        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());
    }

    /**
	 * A zombie toast spawner can be destroyed by a player.
	 */
	@Test
    public void testDestroyZombieToastSpawner() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testDestroyZombieToastSpawner.json", "Standard"));


        // Assert that a player is spawned with a treasure and an exit
        List<EntityResponse> expectedStartList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startSwordInfo = new EntityResponse("1", "sword", new Position(1,0), true);
        EntityResponse startSpawnerInfo = new EntityResponse("2", "zombie_toast_spawner", new Position(3,0), false);

        expectedStartList.add(startPlayerInfo);
        expectedStartList.add(startSwordInfo);
        expectedStartList.add(startSpawnerInfo);

        assertEquals(expectedStartList, controller.getDungeon(0).generateListEntityResponse());

        // Make the player claim the key
		controller.tick(null, Direction.RIGHT);

        // Assert the sword is off the map
        List<EntityResponse> midList = new ArrayList<EntityResponse>();

        EntityResponse midPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse midSpawnerInfo = new EntityResponse("2", "zombie_toast_spawner", new Position(3,0), false);

        midList.add(midPlayerInfo);
        midList.add(midSpawnerInfo);

        assertEquals(midList, controller.getDungeon(0).generateListEntityResponse());

        // Assert the sword is in the inventory
        List<ItemResponse> expectedInventory1 = new ArrayList<ItemResponse>();
        ItemResponse swordInfo = new ItemResponse("1", "sword");
        expectedInventory1.add(swordInfo);

        assertEquals(expectedInventory1, controller.getDungeon(0).generateListInventoryResponse());

        // Make player step on the spawner
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);

        // Assert the sword is still in the inventory
        List<ItemResponse> expectedInventory2 = new ArrayList<ItemResponse>();
        ItemResponse swordInfo2 = new ItemResponse("1", "sword");
        expectedInventory2.add(swordInfo2);

        assertEquals(expectedInventory2, controller.getDungeon(0).generateListInventoryResponse());

        // Assert the player destroys the spawner
        List<EntityResponse> expectedEndList = new ArrayList<EntityResponse>();
        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(3,0), true);
        expectedEndList.add(endPlayerInfo);

        assertEquals(expectedEndList, controller.getDungeon(0).generateListEntityResponse());
    }
}
