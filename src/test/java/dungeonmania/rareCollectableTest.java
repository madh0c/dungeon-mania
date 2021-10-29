package dungeonmania;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class RareCollectableTest {
    @Test
    public void testRareAppearing() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("rarecollectableMap", "peaceful");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		controller.tick("", Direction.RIGHT);
		
    }
}
