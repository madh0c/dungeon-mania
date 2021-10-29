package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Entity;

import java.util.Arrays;
import java.util.List;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CollectibleEntitiesTest {
    @Test
    public void testCanPickUp() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right to pickup treasure
        assertDoesNotThrow(() -> controller.newGame("testCollectibles1", "Peaceful"));        
        assertDoesNotThrow(() ->controller.tick("", Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo();
        assertEquals(Arrays.asList(new ItemResponse("1", "treasure")), dungeonInfo.getInventory());

        // move player right again to pickup wood
        assertDoesNotThrow(() ->controller.tick("", Direction.RIGHT));

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo();

        // both objects should be in the inventory
        assertEquals(Arrays.asList(new ItemResponse("1", "treasure"), new ItemResponse("2", "wood")), dungeonInfo.getInventory());

        // move player right again to pickup arrow
        assertDoesNotThrow(() ->controller.tick("", Direction.RIGHT));

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo();

        // both objects should be in the inventory
        assertEquals(Arrays.asList(new ItemResponse("1", "treasure"), new ItemResponse("2", "wood"), new ItemResponse("3", "arrow")), dungeonInfo.getInventory());
        
    }

    @Test
    public void testKey() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesKey", "Peaceful"));        
        assertDoesNotThrow(() ->controller.tick("", Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo();
        assertEquals(Arrays.asList(new ItemResponse("1", "key")), dungeonInfo.getInventory());

        // move player right again, should encounter another key
        assertDoesNotThrow(() ->controller.tick("", Direction.RIGHT));

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo();

        // only first key should be in inventory
        assertEquals(Arrays.asList(new ItemResponse("1", "key")), dungeonInfo.getInventory());

        // move player right again, should encounter door
        assertDoesNotThrow(() ->controller.tick("", Direction.RIGHT));

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo();

        // get actual dungeon and check the player can move onto the door
        assertEquals(controller.getDungeon().getPlayerPostion(), new Position(3, 0));       

    }

    @Test
    public void testPotions() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesPotions", "Standard"));        
        assertDoesNotThrow(() ->controller.tick("", Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo();
        assertEquals(Arrays.asList(new ItemResponse("1", "health_potion")), dungeonInfo.getInventory());

        controller.getDungeon().getPlayer().setHealth(1);
        assertEquals(1, controller.getDungeon().getPlayer().getHealth());

        // move player right again and use health potion, should pickup invis pot
        assertDoesNotThrow(() ->controller.tick("health_potion", Dir)ection.RIGHT);

        Player player = controller.getDungeon().getPlayer();
        // health should be full
        assertEquals(100, player.getHealth());

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo();

        // only invis pot should be in inventory, as health pot got used
        assertEquals(Arrays.asList(new ItemResponse("2", "invisibility_potion")), dungeonInfo.getInventory());

        // move player right again, should pickup invicible pot
        assertDoesNotThrow(() ->controller.tick("invisibility_potion)", Direction.RIGHT);
        assertTrue(!player.isVisible());

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo();

        assertEquals(Arrays.asList(new ItemResponse("3", "invincibility_potion")), dungeonInfo.getInventory());

        //merc should be adjacent to character right now

        // use invicible pot and move right
        // health should be full as mercenary runs away / player is invincible
        assertEquals(100, player.getHealth());
    }

    @Test
    public void testBomb() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesBomb", "Peaceful"));        
        assertDoesNotThrow(() ->controller.tick("", Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo();
        assertEquals(Arrays.asList(new ItemResponse("1", "bomb")), dungeonInfo.getInventory());

        // move to right (pushing the boulder onto the switch)
        assertDoesNotThrow(() ->controller.tick("", Direction.RIGHT));

        // use the bomb
        assertDoesNotThrow(() ->controller.tick("bomb", Direction.NONE));

        // update info
        dungeonInfo = controller.getDungeonInfo();

        // player should be the only entity left
        assertEquals(1, dungeonInfo.getEntities().size());
    }

    @Test
    public void testShieldAndSword() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right to pickup sword
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesSword", "Standard"));        
        assertDoesNotThrow(() ->controller.tick("", Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo();
        assertEquals(Arrays.asList(new ItemResponse("1", "sword")), dungeonInfo.getInventory());

        // move to right and pickup shield
        assertDoesNotThrow(() ->controller.tick("", Direction.RIGHT));

        // grab the info of dungeon
        dungeonInfo = controller.getDungeonInfo();
        assertEquals(Arrays.asList(new ItemResponse("1", "sword"), new ItemResponse("2", "shield")), dungeonInfo.getInventory());

        // move to right and fight the mercenary
        assertDoesNotThrow(() ->controller.tick("", Direction.RIGHT));

        // TODO: find what the health should be (50 IS WRONG)
        Player player = controller.getDungeon().getPlayer();
        assertEquals(50, player.getHealth());
    }


}
