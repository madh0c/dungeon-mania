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
    public void testRareAppearing() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testDroppingRing.json", "peaceful");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		controller.tick("", Direction.RIGHT);
		//Player and mercenary now in the same square
		//TODO: change it so that there is a chance of this appearing
		
		assertEquals(Arrays.asList(new ItemResponse("1", "one_ring")), dungeonInfo.getInventory());

    }

	@Test
	public void testRevive() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testRarecollectableMap.json", "peaceful");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		controller.tick("", Direction.DOWN);
		assertEquals(Arrays.asList(new ItemResponse("1", "one_ring")), dungeonInfo.getInventory());
		for (int i = 0; i < 81; i++) {
			controller.tick("", Direction.LEFT);
		}
		Player player = controller.getDungeon(0).getPlayer();
		assertEquals(100, player.getHealth());

	}

	@Test
	public void testRandomRing() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testRandomRing.json", "peaceful");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		for (int i = 0; i < 21; i++) {
			controller.tick("", Direction.LEFT);
		}
		assertEquals(Arrays.asList(new ItemResponse("1", "one_ring")), dungeonInfo.getInventory());
		for (int i = 0; i < 20; i++) {
			controller.tick("", Direction.LEFT);
			//Ring should not spawn
			//TODO: check that ring does not spawn
		}
		for (int i = 0; i < 20; i++) {
			controller.tick("", Direction.LEFT);
			//TODO: check that ring does not spawn

		}
		for (int i = 0; i < 20; i++) {
			controller.tick("", Direction.LEFT);
			//TODO: check that ring does not spawn
		}
		for (int i = 0; i < 20; i++) {
			controller.tick("", Direction.LEFT);
		}
		assertEquals(Arrays.asList(new ItemResponse("1", "one_ring")), dungeonInfo.getInventory());
		
	}
	

}

