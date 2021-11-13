package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BattleTest {
	
	@Test
	public void testBattleOnce() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testBattleOnce", "Standard"));

		Dungeon dungeon = controller.getDungeon(0);
		controller.tick(null, Direction.RIGHT);
		assertEquals(95, dungeon.getPlayer().getHealth());
	}

	@Test
	public void testBasicBattle() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testBasicBattle", "Standard"));

		controller.tick(null, Direction.LEFT);

		Dungeon dungeon = controller.getDungeon(0);
		Position pos = dungeon.getPlayerPosition();
		assertEquals(95, dungeon.getPlayer().getHealth());
		assertFalse(dungeon.entityExists("spider", pos));

		// Next tick
		controller.tick(null, Direction.LEFT);

		dungeon = controller.getDungeon(0);
		pos = dungeon.getPlayerPosition();
		assertEquals(90, dungeon.getPlayer().getHealth());
		assertFalse(dungeon.entityExists("spider", pos));

		// Next tick
		controller.tick(null, Direction.LEFT);

		dungeon = controller.getDungeon(0);
		pos = dungeon.getPlayerPosition();
		assertEquals(85, dungeon.getPlayer().getHealth());
		assertFalse(dungeon.entityExists("spider", pos));
	}

	@Test
	public void testItemsLoseDurability() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testItemsLoseDurability", "Standard"));
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		controller.tick(null, Direction.DOWN);
		dungeonInfo = controller.getDungeonInfo(0);
		assertEquals(Arrays.asList(new ItemResponse("1", "anduril"), new ItemResponse("2", "sword"), new ItemResponse("3", "shield"), new ItemResponse("4", "midnight_armour"),  new ItemResponse("5", "armour")), dungeonInfo.getInventory());
		for (int i = 0; i < 50; i++) {
			controller.tick(null, Direction.NONE);
		}
		controller.tick(null, Direction.DOWN);
		for (int i = 0; i < 50; i++) {
			controller.tick(null, Direction.NONE);
		}
		controller.tick(null, Direction.DOWN);
		for (int i = 0; i < 50; i++) {
			controller.tick(null, Direction.NONE);
		}
		for (int i = 0; i < 50; i++) {
			controller.tick(null, Direction.NONE);
		}
		controller.tick(null, Direction.DOWN);
		for (int i = 0; i < 50; i++) {
			controller.tick(null, Direction.NONE);
		}
		controller.tick(null, Direction.DOWN);
		for (int i = 0; i < 50; i++) {
			controller.tick(null, Direction.NONE);
		}
		controller.tick(null, Direction.DOWN);
		for (int i = 0; i < 50; i++) {
			controller.tick(null, Direction.NONE);
		}
		dungeonInfo = controller.getDungeonInfo(0);
		assertFalse(dungeonInfo.getInventory().contains(new ItemResponse("1", "anduril")));
		assertFalse(dungeonInfo.getInventory().contains(new ItemResponse("2", "sword")));
		assertFalse(dungeonInfo.getInventory().contains(new ItemResponse("3", "shield")));
		assertFalse(dungeonInfo.getInventory().contains(new ItemResponse("4", "midnight_armour")));
		assertFalse(dungeonInfo.getInventory().contains(new ItemResponse("5", "armour")));
	}

	// @Test
	// public void testBattleDie() {
	// 	DungeonManiaController controller = new DungeonManiaController();
	// 	assertDoesNotThrow(() -> controller.newGame("testPlayerDies", "Standard"));

	// 	// For 20 ticks, wait for zombie to spawn
	// 	for (int i = 0; i < 21; i++) {
	// 		controller.tick(null, Direction.NONE);
	// 	}

	// 	// On this tick, zombie spawns on player, and battles him
	// 	// Mercenary has already battled him twice too
	// 	controller.tick(null, Direction.NONE);


	// 	assertEquals(50, controller.getDungeon(0).getPlayer().getHealth());

	// 	// Wait 19 ticks, til 40th tick
	// 	// Mercenary has battled him twice again, 30 health
	// 	for (int i = 0; i < 18; i++) {
	// 		controller.tick(null, Direction.NONE);
	// 	}

	// 	assertEquals(30, controller.getDungeon(0).getPlayer().getHealth());

	// }
	
}
