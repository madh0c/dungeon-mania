package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.allEntities.Mercenary;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.AnimationQueue;
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
	public void testSaveGameGoals() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testAndGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:enemies AND :treasure)");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), ":treasure");
		DungeonResponse dREnd = controller.getDungeonInfo(0);
		String gameName = "testAndGoal-1636361348027";
		assertEquals(dREnd, controller.saveGame(gameName));
		assertDoesNotThrow(() -> controller.loadGame(gameName));
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

        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startKeyInfo = new EntityResponse("1", "key", new Position(1,0), false);
        EntityResponse startSpawnerInfo = new EntityResponse("2", "zombie_toast_spawner", new Position(2,0), true);

        startList.add(startPlayerInfo);
        startList.add(startKeyInfo);
        startList.add(startSpawnerInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());
 

        // Make the player claim the key
		controller.tick(null, Direction.RIGHT);

        DungeonResponse dRMid = controller.getDungeonInfo(0);

        // Assert the sword is off the map
        List<EntityResponse> midList = new ArrayList<EntityResponse>();

        EntityResponse midPlayerInfo = new EntityResponse("0", "player", new Position(1,0), true);
        EntityResponse midSpawnerInfo = new EntityResponse("2", "zombie_toast_spawner", new Position(2,0), true);

        midList.add(midPlayerInfo);
        midList.add(midSpawnerInfo);

        assertEquals(midList, dRMid.getEntities());

        // Assert the sword is in the inventory
        List<ItemResponse> expectedInventory1 = new ArrayList<ItemResponse>();
        ItemResponse keyInfo = new ItemResponse("1", "key");
        expectedInventory1.add(keyInfo);

        assertEquals(expectedInventory1, dRMid.getInventory());

        // Make player interact with spawner
        controller.tick(null, Direction.RIGHT);

        assertThrows(InvalidActionException.class, () -> controller.interact("2"));

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
     * Saving a game.
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

        String gameName = "testMercenaryBribe-1636079593059";

        // Assert that the Save Game Method Works
        assertEquals(dREnd, controller.saveGame(gameName));

        assertTrue(controller.allGames().contains(gameName));

    }

    @Test
    public void testLoad() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.loadGame("testMercenaryBribe-1636079593059"));
        assertDoesNotThrow(() -> controller.saveGame("testMercenaryBribe-1636079593059"));
	}

    @Test
    public void testLoadInventory() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testLoadInventory", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startE1 = new EntityResponse("1", "treasure", new Position(1,0), false);
        EntityResponse startE2 = new EntityResponse("2", "key", new Position(2,0), false);
        EntityResponse startE3 = new EntityResponse("3", "health_potion", new Position(3,0), false);
        EntityResponse startE4 = new EntityResponse("4", "invincibility_potion", new Position(4,0), false);
        EntityResponse startE5 = new EntityResponse("5", "invisibility_potion", new Position(5,0), false);
        EntityResponse startE6 = new EntityResponse("6", "wood", new Position(6,0), false);
        EntityResponse startE7 = new EntityResponse("7", "arrow", new Position(7,0), false);
        EntityResponse startE8 = new EntityResponse("8", "bomb", new Position(8,0), false);
        EntityResponse startE9 = new EntityResponse("9", "sword", new Position(9,0), false);
        EntityResponse startE10 = new EntityResponse("10", "armour", new Position(10,0), false);
        EntityResponse startE11 = new EntityResponse("11", "one_ring", new Position(11,0), false);
        EntityResponse startE12 = new EntityResponse("12", "bow", new Position(12,0), false);
        EntityResponse startE13 = new EntityResponse("13", "shield", new Position(13,0), false);
        EntityResponse startE14 = new EntityResponse("14", "wall", new Position(19,20), false);
        EntityResponse startE15 = new EntityResponse("15", "wall", new Position(20,19), false);
        EntityResponse startE16 = new EntityResponse("16", "mercenary", new Position(20,20), true);
        EntityResponse startE17 = new EntityResponse("17", "wall", new Position(20,21), false);
        EntityResponse startE18 = new EntityResponse("18", "wall", new Position(21,20), false);

        startList.add(startPlayerInfo);
        startList.add(startE1);
        startList.add(startE2);
        startList.add(startE3);
        startList.add(startE4);
        startList.add(startE5);
        startList.add(startE6);
        startList.add(startE7);
        startList.add(startE8);
        startList.add(startE9);
        startList.add(startE10);
        startList.add(startE11);
        startList.add(startE12);
        startList.add(startE13);
        startList.add(startE14);
        startList.add(startE15);
        startList.add(startE16);
        startList.add(startE17);
        startList.add(startE18);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

		// Move player away from the mercenary.
        for (int i = 0; i < 13; i++) {
            controller.tick(null, Direction.RIGHT);
        }

		// Assert the mercenary has not moved from spawn
        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse endPlayerInfo = new EntityResponse("0", "player", new Position(13,0), true);
        EntityResponse endE1 = new EntityResponse("14", "wall", new Position(19,20), false);
        EntityResponse endE2 = new EntityResponse("15", "wall", new Position(20,19), false);
        EntityResponse endE3 = new EntityResponse("16", "mercenary", new Position(20,20), true);
        EntityResponse endE4 = new EntityResponse("17", "wall", new Position(20,21), false);
        EntityResponse endE5 = new EntityResponse("18", "wall", new Position(21,20), false);
        EntityResponse endE6 = new EntityResponse("19", "mercenary", new Position(3,0), true);

        endList.add(endPlayerInfo);
        endList.add(endE1);
        endList.add(endE2);
        endList.add(endE3);
        endList.add(endE4);
        endList.add(endE5);
        endList.add(endE6);


        List<ItemResponse> expInvList = new ArrayList<ItemResponse>();

        ItemResponse i1 = new ItemResponse("1", "treasure");
        ItemResponse i2 = new ItemResponse("2", "key");
        ItemResponse i3 = new ItemResponse("3", "health_potion");
        ItemResponse i4 = new ItemResponse("4", "invincibility_potion");
        ItemResponse i5 = new ItemResponse("5", "invisibility_potion");
        ItemResponse i6 = new ItemResponse("6", "wood");
        ItemResponse i7 = new ItemResponse("7", "arrow");
        ItemResponse i8 = new ItemResponse("8", "bomb");
        ItemResponse i9 = new ItemResponse("9", "sword");
        ItemResponse i10 = new ItemResponse("10", "armour");
        ItemResponse i11 = new ItemResponse("11", "one_ring");
        ItemResponse i12 = new ItemResponse("12", "bow");
        ItemResponse i13 = new ItemResponse("13", "shield");

        expInvList.add(i1);
        expInvList.add(i2);
        expInvList.add(i3);
        expInvList.add(i4);
        expInvList.add(i5);
        expInvList.add(i6);
        expInvList.add(i7);
        expInvList.add(i8);
        expInvList.add(i9);
        expInvList.add(i10);
        expInvList.add(i11);
        expInvList.add(i12);
        expInvList.add(i13);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(endList, dREnd.getEntities());
        assertEquals(expInvList, dREnd.getInventory());

        assertDoesNotThrow(() -> controller.saveGame("testLoadInventory-1636120871272"));
        assertDoesNotThrow(() -> controller.loadGame("testLoadInventory-1636120871272"));

        DungeonResponse dRLoad = controller.getDungeonInfo(0);

        assertEquals(endList, dRLoad.getEntities());
        assertEquals(expInvList, dRLoad.getInventory());

        assertEquals(new ArrayList<AnimationQueue>(), dRLoad.getAnimations());
        assertEquals("testLoadInventory.json", dRLoad.getDungeonName());
        assertEquals(new ArrayList<String>(), dRLoad.getBuildables());
        assertEquals("", dRLoad.getGoals());
        assertEquals("0", dRLoad.getDungeonId());
    }

    @Test
    public void testLoadReloadx2() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.loadGame("testMercenaryBribe-1636079593059"));

        // Move player away from the mercenary.
        for (int i = 0; i < 10; i++) {
            controller.tick(null, Direction.UP);
            controller.tick(null, Direction.DOWN);
        }

        assertDoesNotThrow(() -> controller.saveGame("testMercenaryBribe-1636079593059"));


        assertDoesNotThrow(() -> controller.loadGame("testMercenaryBribe-1636079593059"));

        // Move player away from the mercenary.
        for (int i = 0; i < 10; i++) {
            controller.tick(null, Direction.LEFT);
            controller.tick(null, Direction.RIGHT);
        }

        assertDoesNotThrow(() -> controller.saveGame("testMercenaryBribe-1636079593059"));
	}
}


