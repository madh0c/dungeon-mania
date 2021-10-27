package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;

public class CollectibleEntitiesTest {
    @Test
    public void testCanPickUp() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right
        controller.newGame("testCollectibles", "peaceful");
        controller.tick("", Direction.RIGHT);

        assertEquals(new ItemResponse("1", "treasure"), controller.getItemInfo("1"));

        // move player right again
        controller.tick("", Direction.RIGHT);

        // both objects should be in the inventory
        assertEquals(new ItemResponse("1", "treasure"), controller.getItemInfo("1"));
        assertEquals(new ItemResponse("2", "wood"), controller.getItemInfo("2"));
        
    }
}
