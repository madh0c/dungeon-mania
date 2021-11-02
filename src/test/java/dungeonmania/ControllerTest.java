package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class ControllerTest {
	@Test
    public void newGameTestNonExistentDungeon() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.newGame("null", "Standard"));
    }

	@Test
    public void newGameTestIllegalGameMode() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.newGame("portals", "illegal"));
    }

	@Test
    public void newGameTestIllegalArguments() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.newGame("illegal", "arguments"));
    }

	@Test
    public void testValidLoadGame() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.loadGame("null"));
    }

	@Test
    public void tickTestInvalidItemUsed() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.tick("invalid", Direction.RIGHT));
    }

	@Test
    public void tickTestItemNotInInventory() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("portals", "Standard"));
	
        assertThrows(InvalidActionException.class, () -> controller.tick("bomb", Direction.LEFT));
    }

	@Test
    public void interactEntityDoesNotExist() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("portals", "Standard"));
	
        assertThrows(IllegalArgumentException.class, () -> controller.interact("4"));
    }

	@Test
    public void interactMercenaryOutOfRange() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testInteractMercenaryOutOfRange", "Standard"));
	
        assertThrows(InvalidActionException.class, () -> controller.interact("1"));
    }

	@Test
    public void interactMercenaryNoGold() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testInteractMercenaryNoGold", "Standard"));
	
        assertThrows(InvalidActionException.class, () -> controller.interact("1"));
    }

	@Test
    public void interactZombieSpawnerOutOfRange() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testInteractZombieSpawnerOutOfRange", "Standard"));
	
        assertThrows(InvalidActionException.class, () -> controller.interact("1"));
    }

	@Test
    public void interactZombieSpawnerWithoutWeapon() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testInteractZombieSpawnerWithoutWeapon", "Standard"));
	
        assertThrows(InvalidActionException.class, () -> controller.interact("1"));
    }

	@Test
    public void interactSpawnerDungeons() {
		int i = 0;
        for (String dungeon : DungeonManiaController.dungeons()) {
			System.out.println(dungeon);
			i++;
		} System.out.println(i);

    }

    
    /**
     * Running Multiple Games from a Single Controller.
     */
	@Test
    public void testSingleControllerMultipleGames() {
        DungeonManiaController controller = new DungeonManiaController();

        // Creating First New Game
		assertDoesNotThrow(() -> controller.newGame("testWallBlocksPlayerMovement", "Standard"));

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        List<EntityResponse> startList = dRStart.getEntities();

		// Move player into the wall
		controller.tick(null, Direction.RIGHT);

        // Assert the player doesnt move 
        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(startList, dREnd.getEntities());


        // Creating Second New Game
		assertDoesNotThrow(() -> controller.newGame("testWallBlocksMercenaryMovement", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList2 = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(2,0), true);
        EntityResponse startWallInfo = new EntityResponse("1", "wall", new Position(1,0), false);
        EntityResponse startMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,0), true);

        startList2.add(startPlayerInfo);
        startList2.add(startWallInfo);
        startList2.add(startMercenaryInfo);

        DungeonResponse dRStart2 = controller.getDungeonInfo(1);
        assertEquals(startList2, dRStart2.getEntities());

		// Move player away from the mercenary.
		controller.tick(null, Direction.RIGHT);

		// Assert the mercenary has not moved from spawn
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(3,0), true);
        EntityResponse expectedWallInfo = new EntityResponse("1", "wall", new Position(1,0), false);
        EntityResponse expectedMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,0), true);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedWallInfo);
        expectedList.add(expectedMercenaryInfo);

        DungeonResponse dREnd2 = controller.getDungeonInfo(1);
        assertEquals(expectedList, dREnd2.getEntities());

        String gameName = "name";
        // Asserting that the Save Game Method Works
        assertEquals(dREnd2, controller.saveGame(gameName));

        assertTrue(controller.allGames().contains(gameName));

    }

    /**
     * Running Multiple Games from a Single Controller.
     */
	@Test
    public void testSaveGame() {
        DungeonManiaController controller = new DungeonManiaController();

		assertDoesNotThrow(() -> controller.newGame("testWallBlocksMercenaryMovement", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(2,0), true);
        EntityResponse startWallInfo = new EntityResponse("1", "wall", new Position(1,0), false);
        EntityResponse startMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,0), true);

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
        EntityResponse expectedMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,0), true);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedWallInfo);
        expectedList.add(expectedMercenaryInfo);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(expectedList, dREnd.getEntities());

        String gameName = "name";

        // Assert that the Save Game Method Works
        assertEquals(dREnd, controller.saveGame(gameName));

        assertTrue(controller.allGames().contains(gameName));

    }


	//TODO: Write tests for loading, saving games, calling allgames.

	


}


