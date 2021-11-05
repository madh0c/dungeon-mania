package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.allEntities.Mercenary;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;

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
    public void testInvalidLoadGame() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.loadGame("null"));
    }

    // TODO: Fix Load Game
    @Test
    public void testValidLoadGame() {
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

        String gameName = "testWallBlocksMercenaryMovement-1636040715294";

        // Assert that the Save Game Method Works
        assertEquals(dREnd, controller.saveGame(gameName));
        System.out.println(controller.allGames());

        assertDoesNotThrow(() -> controller.loadGame(gameName));
        controller.saveGame(gameName);
    }

	@Test
    public void tickTestInvalidItemUsed() {
        DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testKeyOpensDoor", "Standard"));

        // Assert that a player is spawned with a treasure and an exit
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startKeyInfo = new EntityResponse("1", "key", new Position(1,0), false);
        EntityResponse startDoorInfo = new EntityResponse("2", "door", new Position(2,0), false);

        startList.add(startPlayerInfo);
        startList.add(startKeyInfo);
        startList.add(startDoorInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        // Make the player claim the key
		controller.tick(null, Direction.RIGHT);

        // Assert the key is off the map
        List<EntityResponse> midList = new ArrayList<EntityResponse>();

        EntityResponse midPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse midDoorInfo = new EntityResponse("2", "door", new Position(2,0), false);

        midList.add(midPlayerInfo);
        midList.add(midDoorInfo);

        DungeonResponse dRMid = controller.getDungeonInfo(0);
        assertEquals(midList, dRMid.getEntities());

        // Assert the key is in the inventory
        List<ItemResponse> expectedInventory1 = new ArrayList<ItemResponse>();
        ItemResponse keyInfo = new ItemResponse("1", "key");
        expectedInventory1.add(keyInfo);
        
        assertEquals(expectedInventory1, dRMid.getInventory());

        // Try to use a key, which is not a usable item.
        assertThrows(IllegalArgumentException.class, () -> controller.tick("1", Direction.RIGHT));
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
    public void ListAllGames() {
        DungeonManiaController controller = new DungeonManiaController();
        List<String> allGames = controller.allGames();
        List<String> expectedGames = new ArrayList<String>();

        expectedGames.add("testMercenaryBribe-1636079593059");
        expectedGames.add("testWallBlocksMercenaryMovement-1636040715294");

        assertTrue(allGames.containsAll(expectedGames));

    }

    
    /**
     * Running Multiple Games from a Single Controller.
     */
	@Test
    public void testSingleControllerMultipleGames() {
        DungeonManiaController controller = new DungeonManiaController();

        // Creating First New Game
		assertDoesNotThrow(() -> controller.newGame("testWallBlocksMercenaryMovement", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList1 = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(2,0), true);
        EntityResponse startWallInfo = new EntityResponse("1", "wall", new Position(1,0), false);
        EntityResponse startMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,0), true);

        startList1.add(startPlayerInfo);
        startList1.add(startWallInfo);
        startList1.add(startMercenaryInfo);

        DungeonResponse dRStart1 = controller.getDungeonInfo(0);
        assertEquals(startList1, dRStart1.getEntities());

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

        DungeonResponse dREnd1 = controller.getDungeonInfo(0);
        assertEquals(expectedList, dREnd1.getEntities());


        // Creating Second New Game
        assertDoesNotThrow(() -> controller.newGame("testMercenaryBribe", "Standard"));

        // Pick up gold to right of player
		controller.tick(null, Direction.RIGHT);

		// interact with mercenary
		assertDoesNotThrow(() -> controller.interact("1"));

		// cast into merc, check if ally
		Mercenary merc = (Mercenary) controller.getDungeon(1).getEntity("1");
		assertTrue(merc.getIsAlly());
;
		// wait for merc to move into player
		controller.tick(null, Direction.NONE);
		controller.tick(null, Direction.NONE);

		// Now merc is on player, check he moves around with player
		controller.tick(null, Direction.DOWN);
		Position player = controller.getDungeon(1).getEntity("0").getPosition();
		assertTrue(controller.getDungeon(1).entityExists("mercenary", player));
		// assertEquals(-1, controller.getDungeon(0).getEntity("1").getPosition().getX());
		// assertTrue(controller.getDungeon(0).entityExists("mercenary", player.translateBy(Direction.UP)));

		controller.tick(null, Direction.RIGHT);
		player = controller.getDungeon(1).getEntity("0").getPosition();
		assertTrue(controller.getDungeon(1).entityExists("mercenary", player));

        DungeonResponse dREnd2 = controller.getDungeonInfo(1);

        String gameName = "testMercenaryBribe-1636079593059";
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

    @Test
    public void Load() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.loadGame("testMercenaryBribe-1636079593059"));
        assertDoesNotThrow(() -> controller.saveGame("testMercenaryBribe-1636079593059"));
	}

	//TODO: Write tests for loading, saving games, calling allgames.

	


}


