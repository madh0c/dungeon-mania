package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.allEntities.Player;
import dungeonmania.util.Direction;

public class RareCollectableTest {

	@Test
	public void testRevive() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testRareCollectableMap", "Hard");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		controller.tick(null, Direction.DOWN);
		dungeonInfo = controller.getDungeonInfo(0);
		assertEquals(Arrays.asList(new ItemResponse("1", "one_ring")), dungeonInfo.getInventory());
		controller.tick(null, Direction.DOWN);
		Player player = controller.getDungeon(0).getPlayer();
		//Loses Health
		assertEquals(42, player.getHealth());
		controller.tick(null, Direction.DOWN);
		assertEquals(18, player.getHealth());
		controller.tick(null, Direction.DOWN);
		//Player gets revived
		assertEquals(49, player.getHealth());
		dungeonInfo = controller.getDungeonInfo(0);
		//Inventory should be empty after one ring is consumed
		assertTrue(dungeonInfo.getInventory().isEmpty());
	}

	@Test
	public void testMoreThanOne() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testMoreRing", "Standard");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		controller.tick(null, Direction.RIGHT);
		dungeonInfo = controller.getDungeonInfo(0);
		assertEquals(Arrays.asList(new ItemResponse("1", "one_ring")), dungeonInfo.getInventory());
		controller.tick(null, Direction.RIGHT);
		dungeonInfo = controller.getDungeonInfo(0);
		//Should not pickup second ring
		assertEquals(Arrays.asList(new ItemResponse("1", "one_ring")), dungeonInfo.getInventory());
		controller.tick(null, Direction.RIGHT);
		Player player = controller.getDungeon(0).getPlayer();
		assertEquals(85, player.getHealth());
		dungeonInfo = controller.getDungeonInfo(0);
		assertEquals(Arrays.asList(new ItemResponse("1", "one_ring")), dungeonInfo.getInventory());
	}

}

