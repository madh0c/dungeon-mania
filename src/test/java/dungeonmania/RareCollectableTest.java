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
import dungeonmania.util.Position;

public class RareCollectableTest {

	@Test
	public void testRevive() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testPlayerRevives", "Hard");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		controller.tick(null, Direction.DOWN);
		dungeonInfo = controller.getDungeonInfo(0);
		//Picks up one ring
		assertEquals(Arrays.asList(new ItemResponse("1", "one_ring")), dungeonInfo.getInventory());
		controller.tick(null, Direction.DOWN);
		Player player = controller.getDungeon(0).getPlayer();
		controller.tick(null, Direction.DOWN);
		//Player respawns with 60 health
		assertEquals(60, player.getHealth());
		dungeonInfo = controller.getDungeonInfo(0);
		//Inventory should not contain one ring after it is consumed
		assertFalse(dungeonInfo.getInventory().contains(new ItemResponse ("1", "one_ring")));
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
		assertTrue(dungeonInfo.getInventory().contains(new ItemResponse("1", "one_ring")));
	}

	@Test
	public void testPlayerDies() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testPlayerRevives", "Hard");
		controller.tick(null, Direction.RIGHT);
		//Picks up one ring
		controller.tick(null, Direction.DOWN);
		controller.tick(null, Direction.DOWN);
		//Player fights assassin
		Position playerPos = controller.getDungeon(0).getPlayer().getPosition();
		controller.tick(null, Direction.NONE);
		assertFalse(controller.getDungeon(0).entityExists("player", playerPos));
	}

}

