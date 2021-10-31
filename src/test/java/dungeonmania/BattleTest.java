package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.allEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BattleTest {

	@Test
	public void testBattleOnce() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testBattleOnce.json", "Standard"));

		Dungeon dungeon = controller.getDungeon(0);
		controller.tick(null, Direction.RIGHT);
		assertEquals(95, dungeon.getPlayer().getHealth());
	}

	@Test
	public void testBasicBattle() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testBasicBattle.json", "Standard"));

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
	public void testBattleDie() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testPlayerDies.json", "Standard"));

		// For 20 ticks, wait for zombie to spawn
		for (int i = 0; i < 20; i++) {
			controller.tick(null, Direction.NONE);
		}

		// On this tick, zombie spawns on player, and battles him
		// Mercenary has already battled him twice too
		controller.tick(null, Direction.NONE);
		assertEquals(50, controller.getDungeon(0).getPlayer().getHealth());

		// Wait 19 ticks, til 41st tick
		for (int i = 0; i < 19; i++) {
			controller.tick(null, Direction.NONE);
		}

		controller.tick(null, Direction.NONE);
		assertEquals(15, controller.getDungeon(0).getPlayer().getHealth());

	}
	
}
