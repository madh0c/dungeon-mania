package dungeonmania;

import org.junit.jupiter.api.Test;
import dungeonmania.allEntities.*;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        List<EntityResponse> startList = dRStart.getEntities();

		// Move player into the wall
		controller.tick(null, Direction.RIGHT);

        // Assert the player doesnt move 
        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(startList, dREnd.getEntities());
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

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

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

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(expectedList, dREnd.getEntities());
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

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

		// Tick player back and forth 22 times
		for (int i = 0; i < 11; i++) {
			controller.tick(null, Direction.RIGHT);
			controller.tick(null, Direction.LEFT);
		}

        // There should be no change from the start after 22 ticks as the player should be at its starting position and 
        // a zombie toast should not spawn.
        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(startList, dREnd.getEntities());
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
        
        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());
         
        // Make player try to push the boulder into the wall.
		controller.tick(null, Direction.RIGHT);

		// Assert boulder cannot be pushed through wall
        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(startList, dREnd.getEntities());
    }

    /**
	 * A spider will move onto a wall.
	 */
	@Test
    public void testSpiderCanMoveThroughWall() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testSpiderCanMoveThroughWall.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startWallInfo = new EntityResponse("1", "wall", new Position(5,1), false);
        EntityResponse startSpiderInfo = new EntityResponse("2", "spider", new Position(5,0), false);

        startList.add(startPlayerInfo);
        startList.add(startWallInfo);
        startList.add(startSpiderInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert the spider coincides with wall
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse endWallInfo = new EntityResponse("1", "wall", new Position(5,1), false);
        EntityResponse endSpiderInfo = new EntityResponse("2", "spider", new Position(5,1), false);

        endList.add(endPlayerInfo);
        endList.add(endWallInfo);
        endList.add(endSpiderInfo);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());
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
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startExitInfo = new EntityResponse("1", "exit", new Position(1,0), false);

        startList.add(startPlayerInfo);
        startList.add(startExitInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

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
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startExitInfo = new EntityResponse("1", "exit", new Position(1,0), false);
        EntityResponse startMercenaryInfo = new EntityResponse("2", "mercenary", new Position(8,0), false);

        startList.add(startPlayerInfo);
        startList.add(startExitInfo);
        startList.add(startMercenaryInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

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
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testPlayerOnExitSubgoalNotSatisfiedII.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startExitInfo = new EntityResponse("1", "exit", new Position(1,0), false);
        EntityResponse startTreasureInfo = new EntityResponse("2", "treasure", new Position(8,0), true);

        startList.add(startPlayerInfo);
        startList.add(startExitInfo);
        startList.add(startTreasureInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Move player onto the exit
		controller.tick(null, Direction.RIGHT);

        // Assert the goals are not satisfied and the game is not over
        String currentGoals = controller.getDungeon(0).getGoals();
        assertEquals(false, currentGoals.isEmpty());
        // assertEquals(false, controller.getDungeon(0).isComplete);
    }

    /**
	 * The player will spawn on a map with itself, a mercenary and the exit. The player steps onto the exit after killing 
     * the mercenary and the game should end.
	 */
	@Test
    public void testPlayerOnExitSubgoalSatisfiedI() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testPlayerOnExitSubgoalSatisfiedI.json", "Standard"));

        // Assert that a player is spawned with a mercenary and an exit
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startMercenaryInfo = new EntityResponse("1", "mercenary", new Position(1,0), false);
        EntityResponse startExitInfo = new EntityResponse("2", "exit", new Position(3,0), false);

        startList.add(startPlayerInfo);
        startList.add(startMercenaryInfo);
        startList.add(startExitInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Make the player kill the mercenary
		controller.tick(null, Direction.RIGHT);

        // Assert the player has killed the mercenary
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse endExitInfo = new EntityResponse("2", "exit", new Position(3,0), false);

        endList.add(endPlayerInfo);
        endList.add(endExitInfo);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());

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
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startTreasureInfo = new EntityResponse("1", "treasure", new Position(1,0), true);
        EntityResponse startExitInfo = new EntityResponse("2", "exit", new Position(3,0), false);

        startList.add(startPlayerInfo);
        startList.add(startTreasureInfo);
        startList.add(startExitInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Make the player claim the treasure
		controller.tick(null, Direction.RIGHT);
        
        // Assert the treasure is off the map
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse endExitInfo = new EntityResponse("2", "exit", new Position(3,0), false);

        endList.add(endPlayerInfo);
        endList.add(endExitInfo);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());

        // Assert the treasure is in the inventory
        List<ItemResponse> expectedInventory = new ArrayList<ItemResponse>();
        ItemResponse treasureInfo = new ItemResponse("1", "treasure");
        expectedInventory.add(treasureInfo);

        assertEquals(expectedInventory, dREnd.getInventory());

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
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse expectedBoulderInfo = new EntityResponse("1", "boulder", new Position(2,0), true);

        startList.add(expectedPlayerInfo);
        startList.add(expectedBoulderInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());
    }

    /**
	 * The player will spawn on a map with itself, and two adjacent boulders. The player pushes the closest boulder into the 
     * other and there should be no change in position for all entities.
	 */
	@Test
    public void testCantMoveTwoBoulders() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testCantMoveTwoBoulders.json", "Standard"));

        // Assert the spawn positions of player and boulders
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startBoulder1Info = new EntityResponse("1", "boulder", new Position(1,0), true);
        EntityResponse startBoulder2Info = new EntityResponse("2", "boulder", new Position(2,0), true);

        startList.add(startPlayerInfo);
        startList.add(startBoulder1Info);
        startList.add(startBoulder2Info);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Move player into the first boulder
		controller.tick(null, Direction.RIGHT);

        // Assert entities havent moved from spawn.
        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(startList, dREnd.getEntities());
    }

    /**
	 * A spider will reverse direction if it tries to move onto a boulder.
	 */
	@Test
    public void testSpiderCantMoveThroughBoulder() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testSpiderCantMoveThroughBoulder.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startBoulderInfo = new EntityResponse("1", "boulder", new Position(5,2), true);
        EntityResponse startSpiderInfo = new EntityResponse("2", "spider", new Position(5,1), false);

        startList.add(startPlayerInfo);
        startList.add(startBoulderInfo);
        startList.add(startSpiderInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert the spider doesnt coincides with the voulder
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse expectedBoulderInfo = new EntityResponse("1", "boulder", new Position(5,2), true);
        EntityResponse expectedSpiderInfo = new EntityResponse("2", "spider", new Position(5,0), false);

        endList.add(expectedPlayerInfo);
        endList.add(expectedBoulderInfo);
        endList.add(expectedSpiderInfo);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());
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
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startBoulderInfo = new EntityResponse("1", "boulder", new Position(1,0), true);

        startList.add(startPlayerInfo);
        startList.add(startBoulderInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert nothing changes
        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(startList, dREnd.getEntities());
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
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startBoulderInfo = new EntityResponse("1", "boulder", new Position(1,0), true);
        EntityResponse startSwitchInfo = new EntityResponse("2", "switch", new Position(2,0, -1), true);

        startList.add(startPlayerInfo);
        startList.add(startBoulderInfo);
        startList.add(startSwitchInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        Map<String, Entity> startEntities = controller.getDungeon(0).getEntities();

        Switch startSwitch = (Switch)startEntities.get("2");
        
        boolean startSwitchStatus = startSwitch.getStatus();

        assertEquals(false, startSwitchStatus);

        // Make the player move the boulder onto the switch
		controller.tick(null, Direction.RIGHT);

		// Assert the player and boulders moved.
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse expectedBoulder1Info = new EntityResponse("1", "boulder", new Position(2,0), true);
        EntityResponse expectedBoulder2Info = new EntityResponse("2", "switch", new Position(2,0, -1), true);

        endList.add(expectedPlayerInfo);
        endList.add(expectedBoulder1Info);
        endList.add(expectedBoulder2Info);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());

        // Assert the switch is activated
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
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startBoulderInfo = new EntityResponse("1", "boulder", new Position(1,0), true);
        EntityResponse startSwitchInfo = new EntityResponse("2", "switch", new Position(2,0, -1), true);

        startList.add(startPlayerInfo);
        startList.add(startBoulderInfo);
        startList.add(startSwitchInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        Map<String, Entity> startEntities = controller.getDungeon(0).getEntities();

        Switch startSwitch = (Switch)startEntities.get("2");
        
        boolean startSwitchStatus = startSwitch.getStatus();

        assertEquals(false, startSwitchStatus);

        // Make the player move the boulder onto the switch
		controller.tick(null, Direction.RIGHT);

		// Assert the player and boulders moved.
        List<EntityResponse> midList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse expectedBoulderInfo = new EntityResponse("1", "boulder", new Position(2,0), true);
        EntityResponse expectedSwitchInfo = new EntityResponse("2", "switch", new Position(2,0, -1), true);

        midList.add(expectedPlayerInfo);
        midList.add(expectedBoulderInfo);
        midList.add(expectedSwitchInfo);

        DungeonResponse dRMid = controller.getDungeonInfo(0);
        assertEquals(midList, dRMid.getEntities());

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

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());

        // Assert the switch reverts to being non-active
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
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startSwitchInfo = new EntityResponse("1", "switch", new Position(5,1, -1), true);
        EntityResponse startSpiderInfo = new EntityResponse("2", "spider", new Position(5,0), false);

        startList.add(startPlayerInfo);
        startList.add(startSwitchInfo);
        startList.add(startSpiderInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert the spider coincides with switch
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse expectedSwitchInfo = new EntityResponse("1", "switch", new Position(5,1, -1), true);
        EntityResponse expectedSpiderInfo = new EntityResponse("2", "spider", new Position(5,1), false);

        endList.add(expectedPlayerInfo);
        endList.add(expectedSwitchInfo);
        endList.add(expectedSpiderInfo);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());
    }




	// DOOR TESTS
    /**
	 * The player will get stopped by a door if it doesnt possess a key. Also checks that doors are created locked.
	 */
	@Test
    public void testPlayerCantMoveThroughDoor() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testPlayerCantMoveThroughDoor.json", "Standard"));

        // Assert the door is spawned closed
        Map<String, Entity> startEntities = controller.getDungeon(0).getEntities();
        Door door = (Door)startEntities.get("1");
        assertEquals(false, door.isOpen());

        // Obtain correct spawn positions
        DungeonResponse dRStart = controller.getDungeonInfo(0);
        List<EntityResponse> startList = dRStart.getEntities();

        // Move player into the door
		controller.tick(null, Direction.RIGHT);

		// Assert nothing has changed from spawn
        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(startList, dREnd.getEntities());
    }

    /**
	 * A mercenary will fail to move through a door.
	 */
	@Test
    public void testMercenaryCantMoveThroughDoor() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testMercenaryCantMoveThroughDoor.json", "Standard"));

        // Assert the door is spawned closed
        Map<String, Entity> startEntities = controller.getDungeon(0).getEntities();
        Door door = (Door)startEntities.get("1");
        assertEquals(false, door.isOpen());

        // Move player away from the mercenary.
		controller.tick(null, Direction.RIGHT);

		// Assert the mercenary has not moved from spawn
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(3,0), true);
        EntityResponse expectedDoorInfo = new EntityResponse("1", "door", new Position(1,0), true);
        EntityResponse expectedMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,0), false);

        endList.add(expectedPlayerInfo);
        endList.add(expectedDoorInfo);
        endList.add(expectedMercenaryInfo);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());
    }

    /**
	 * A spider will move onto a door.
	 */
	@Test
    public void testSpiderCanMoveThroughDoor() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testSpiderCanMoveThroughDoor.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startDoorInfo = new EntityResponse("1", "door", new Position(5,1), true);
        EntityResponse startSpiderInfo = new EntityResponse("2", "spider", new Position(5,0), false);

        startList.add(startPlayerInfo);
        startList.add(startDoorInfo);
        startList.add(startSpiderInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert the spider coincides with door
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse expectedDoorInfo = new EntityResponse("1", "door", new Position(5,1), true);
        EntityResponse expectedSpiderInfo = new EntityResponse("2", "spider", new Position(5,1), false);

        endList.add(expectedPlayerInfo);
        endList.add(expectedDoorInfo);
        endList.add(expectedSpiderInfo);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());
    }

    /**
	 * The player will pick up a key and open a door.
	 */
	@Test
    public void testKeyOpensDoor() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testKeyOpensDoor.json", "Standard"));

        // Assert that a player is spawned with a treasure and an exit
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startKeyInfo = new EntityResponse("1", "key", new Position(1,0), true);
        EntityResponse startDoorInfo = new EntityResponse("2", "door", new Position(2,0), true);

        startList.add(startPlayerInfo);
        startList.add(startKeyInfo);
        startList.add(startDoorInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Make the player claim the key
		controller.tick(null, Direction.RIGHT);

        // Assert the key is off the map
        List<EntityResponse> midList = new ArrayList<EntityResponse>();

        EntityResponse midPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse midDoorInfo = new EntityResponse("2", "door", new Position(2,0), true);

        midList.add(midPlayerInfo);
        midList.add(midDoorInfo);

        DungeonResponse dRMid = controller.getDungeonInfo(0);
        assertEquals(midList, dRMid.getEntities());

        // Assert the key is in the inventory
        List<ItemResponse> expectedInventory1 = new ArrayList<ItemResponse>();
        ItemResponse keyInfo = new ItemResponse("1", "key");
        expectedInventory1.add(keyInfo);
        
        assertEquals(expectedInventory1, dRMid.getInventory());

        // Make player step on the door
        controller.tick(null, Direction.RIGHT);

        DungeonResponse dREnd = controller.getDungeonInfo(0);

        // Assert the key is out of the inventory
        List<ItemResponse> expectedInventory2 = new ArrayList<ItemResponse>();
        assertEquals(expectedInventory2, dREnd.getInventory());


        // Assert the player is on the door
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(2,0), true);
        EntityResponse endDoorInfo = new EntityResponse("2", "door", new Position(2,0), true);

        endList.add(endPlayerInfo);
        endList.add(endDoorInfo);

        assertEquals(endList, dREnd.getEntities());


        // Assert the door is also open
        Map<String, Entity> endEntities = controller.getDungeon(0).getEntities();
        Door door = (Door)endEntities.get("2");
        assertEquals(true, door.isOpen());
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

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert the spider coincides with door
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(5,0), true);
        EntityResponse expectedPortal1Info = new EntityResponse("1", "portal", new Position(1,0), false);
        EntityResponse expectedPortal2Info = new EntityResponse("2", "portal", new Position(4,0), false);

        endList.add(expectedPlayerInfo);
        endList.add(expectedPortal1Info);
        endList.add(expectedPortal2Info);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());
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

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

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
        
        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());
    }

    
    /**
	 * The player will fail to teleport into the boundary.
	 */
	@Test
    public void testCantTeleportPlayerIntoBoundary() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testCantTeleportPlayerIntoBoundary.json", "Standard"));

		// Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startPortal1Info = new EntityResponse("1", "portal", new Position(1,0), false);
        EntityResponse startPortal2Info = new EntityResponse("2", "portal", new Position(8,0), false);

        startList.add(startPlayerInfo);
        startList.add(startPortal1Info);
        startList.add(startPortal2Info);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

		// Try to move player into the portal
		controller.tick(null, Direction.RIGHT);

        // Assert the player doesnt move
        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(startList, dREnd.getEntities());
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

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

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
        
        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());
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

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

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
        
        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());
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

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

		// Move player away from the mercenary.
		controller.tick(null, Direction.LEFT);

		// Assert the mercenary has not moved from spawn
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(4,0), true);
        EntityResponse expectedPortal1Info = new EntityResponse("1", "wall", new Position(1,0), false); 
        EntityResponse expectedPortal2Info = new EntityResponse("2", "portal", new Position(8,0), false);
        EntityResponse expectedMercenaryInfo = new EntityResponse("3", "mercenary", new Position(0,0), false);

        endList.add(expectedPlayerInfo);
        endList.add(expectedPortal1Info);
        endList.add(expectedPortal2Info);
        endList.add(expectedMercenaryInfo);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());
    }

    /**
	 * A spider will be unaffected by a portal.
	 */    
	@Test
    public void testSpiderDoesntTeleport() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testSpiderDoesntTeleport.json", "Standard"));

        // Assert the spawn positions of all entities.
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startPortal1Info = new EntityResponse("1", "portal", new Position(5,1), false);
        EntityResponse startPortal2Info = new EntityResponse("2", "portal", new Position(9,1), false);
        EntityResponse startSpiderInfo = new EntityResponse("3", "spider", new Position(5,0), false);

        startList.add(startPlayerInfo);
        startList.add(startPortal1Info);
        startList.add(startPortal2Info);
        startList.add(startSpiderInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Tick player.
		controller.tick(null, Direction.RIGHT);

		// Assert the spider coincides with portal
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse expectedPortal1Info = new EntityResponse("1", "portal", new Position(5,1), false);
        EntityResponse expectedPortal2Info = new EntityResponse("2", "portal", new Position(9,1), false);
        EntityResponse expectedSpiderInfo = new EntityResponse("3", "spider", new Position(5,1), false);
        
        endList.add(expectedPlayerInfo);
        endList.add(expectedPortal1Info);
        endList.add(expectedPortal2Info);
        endList.add(expectedSpiderInfo);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());
    }

    /**
	 * A zombie toast spawner can be destroyed by a player.
	 */
	@Test
    public void testDestroyZombieToastSpawner() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("/StaticDungeons/testDestroyZombieToastSpawner.json", "Standard"));

        // Assert that a player is spawned with a treasure and an exit
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startSwordInfo = new EntityResponse("1", "sword", new Position(1,0), true);
        EntityResponse startSpawnerInfo = new EntityResponse("2", "zombie_toast_spawner", new Position(3,0), false);

        startList.add(startPlayerInfo);
        startList.add(startSwordInfo);
        startList.add(startSpawnerInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());
 

        // Make the player claim the key
		controller.tick(null, Direction.RIGHT);

        DungeonResponse dRMid = controller.getDungeonInfo(0);

        // Assert the sword is off the map
        List<EntityResponse> midList = new ArrayList<EntityResponse>();

        EntityResponse midPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse midSpawnerInfo = new EntityResponse("2", "zombie_toast_spawner", new Position(3,0), false);

        midList.add(midPlayerInfo);
        midList.add(midSpawnerInfo);

        assertEquals(midList, dRMid.getEntities());

        // Assert the sword is in the inventory
        List<ItemResponse> expectedInventory1 = new ArrayList<ItemResponse>();
        ItemResponse swordInfo = new ItemResponse("1", "sword");
        expectedInventory1.add(swordInfo);

        assertEquals(expectedInventory1, dRMid.getInventory());

        // Make player step on the spawner
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.interact("2");

        // Assert the sword is still in the inventory
        DungeonResponse dREnd = controller.getDungeonInfo(0);

        List<ItemResponse> expectedInventory2 = new ArrayList<ItemResponse>();
        ItemResponse swordInfo2 = new ItemResponse("1", "sword");
        expectedInventory2.add(swordInfo2);

        assertEquals(expectedInventory2, dREnd.getInventory());

        // Assert the player destroys the spawner
        List<EntityResponse> endList = new ArrayList<EntityResponse>();
        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(3,0), true);
        endList.add(endPlayerInfo);

        assertEquals(endList, dREnd.getEntities());
    }
}
