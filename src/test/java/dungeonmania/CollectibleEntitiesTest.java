package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import dungeonmania.exceptions.*;

import dungeonmania.allEntities.Armour;
import dungeonmania.allEntities.Player;
import dungeonmania.allEntities.Sword;
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
        assertDoesNotThrow(() -> controller.newGame("testCollectibles1.json", "Peaceful"));        
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "treasure")), dungeonInfo.getInventory());

        // move player right again to pickup wood
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo(0);

        // both objects should be in the inventory
        assertEquals(Arrays.asList(new ItemResponse("1", "treasure"), new ItemResponse("2", "wood")), dungeonInfo.getInventory());

        // move player right again to pickup arrow
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo(0);

        // both objects should be in the inventory
        assertEquals(Arrays.asList(new ItemResponse("1", "treasure"), new ItemResponse("2", "wood"), new ItemResponse("3", "arrow")), dungeonInfo.getInventory());
        
    }

    @Test
    public void testKey() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesKey.json", "Peaceful"));        
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "key")), dungeonInfo.getInventory());

        // move player right again, should encounter another key
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo(0);

        // only first key should be in inventory
        assertEquals(Arrays.asList(new ItemResponse("1", "key")), dungeonInfo.getInventory());

        // move player right again, should encounter door
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo(0);

        // get actual dungeon and check the player can move onto the door
        assertEquals(controller.getDungeon(0).getPlayerPosition(), new Position(3, 0));       

    }

    @Test
    public void testPotions() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesPotions.json", "Standard"));        
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "health_potion")), dungeonInfo.getInventory());

        controller.getDungeon(0).getPlayer().setHealth(1);
        assertEquals(1, controller.getDungeon(0).getPlayer().getHealth());

        // move player right again and use health potion, should pickup invis pot
        assertDoesNotThrow(() ->controller.tick("health_potion", Direction.RIGHT));

        Player player = controller.getDungeon(0).getPlayer();
        // health should be full
        assertEquals(100, player.getHealth());

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo(0);

        // only invis pot should be in inventory, as health pot got used
        assertEquals(Arrays.asList(new ItemResponse("2", "invisibility_potion")), dungeonInfo.getInventory());

        // move player right again and use invis pot, should also pickup invicible pot
        assertDoesNotThrow(() ->controller.tick("invisibility_potion", Direction.RIGHT));
        assertTrue(!player.isVisible());

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("3", "invincibility_potion")), dungeonInfo.getInventory());

        // merc should be adjacent to character right now
        // use invicible pot and move right
        assertDoesNotThrow(() ->controller.tick("invincibility_potion", Direction.RIGHT));

        // health should be full as mercenary runs away / player is invincible
        assertEquals(100, player.getHealth());
    }

    @Test
    public void testBomb() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesBomb.json", "Peaceful"));        
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "bomb")), dungeonInfo.getInventory());

        // move to right (pushing the boulder onto the switch)
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // use the bomb
        assertDoesNotThrow(() ->controller.tick("bomb", Direction.NONE));

        // update info
        dungeonInfo = controller.getDungeonInfo(0);

        // player should be the only entity left
        assertEquals(1, dungeonInfo.getEntities().size());
    }

    @Test
    public void testArmourAndSword() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right to pickup sword
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesSword.json", "Standard"));        
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "sword")), dungeonInfo.getInventory());

        // move to right and pickup shield
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // grab the info of dungeon
        dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "sword"), new ItemResponse("2", "armour")), dungeonInfo.getInventory());

        // move to right and fight the mercenary
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // // check that durabilities went down
        // Sword sword = (Sword) controller.getDungeon(0).getInventory().get(0);
        // assertNotEquals(10, sword.getDurability()); 

        // Armour armour = (Armour) controller.getDungeon(0).getInventory().get(1);
        // assertNotEquals(10, armour.getDurability()); 

        // // TODO: find what the health should be (100 IS WRONG)
        // Player player = controller.getDungeon(0).getPlayer();
        // assertEquals(100, player.getHealth());
    }

    @Test
    public void testExceptions() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesPotions.json", "Standard"));
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        //try to use a non-usable item
        assertThrows(IllegalArgumentException.class, () ->controller.tick("treasure", Direction.NONE));

        // try to use an item thats not in inventory
        assertThrows(InvalidActionException.class, () ->controller.tick("invisibility_potion", Direction.NONE));
        
    }

    @Test
    public void testPickupTwoItemsConcurrently() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right to pickup treasure AND wood
        assertDoesNotThrow(() ->controller.newGame("testCollectiblesStack.json", "Peaceful"));        
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // update dungeon response
        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);

        // both objects should be in the inventory
        assertEquals(Arrays.asList(new ItemResponse("1", "treasure"), new ItemResponse("2", "wood")), dungeonInfo.getInventory());



    }
}
