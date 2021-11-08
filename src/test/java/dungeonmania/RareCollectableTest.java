package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		controller.newGame("testRareCollectableMap", "Standard");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		controller.tick(null, Direction.RIGHT);
		dungeonInfo = controller.getDungeonInfo(0);
		assertEquals(Arrays.asList(new ItemResponse("7", "one_ring")), dungeonInfo.getInventory());
		for (int i = 0; i < 81; i++) {
			controller.tick(null, Direction.LEFT);
		}
		Player player = controller.getDungeon(0).getPlayer();
		assertEquals(40, player.getHealth());
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

