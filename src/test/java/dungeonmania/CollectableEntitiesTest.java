package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import dungeonmania.exceptions.*;

import dungeonmania.allEntities.Armour;
import dungeonmania.allEntities.Mercenary;
import dungeonmania.allEntities.Player;
import dungeonmania.allEntities.Sword;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CollectableEntitiesTest {
    @Test
    public void testCanPickUp() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right to pickup treasure
        assertDoesNotThrow(() -> controller.newGame("testCollectibles1", "Peaceful"));        
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
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesKey", "Peaceful"));        
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "key_1")), dungeonInfo.getInventory());

        // move player right again, should encounter another key
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo(0);

        // only first key should be in inventory
        assertEquals(Arrays.asList(new ItemResponse("1", "key_1")), dungeonInfo.getInventory());

        // move player right again, should encounter door
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo(0);

        // get actual dungeon and check the player can move onto the door
        assertEquals(controller.getDungeon(0).getPlayerPosition(), new Position(3, 0));       

    }

    @Test
    public void testSunStoneAsKey() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesSunKey", "Peaceful"));        
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "key_1")), dungeonInfo.getInventory());

        // move player right again, should encounter sun stone
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo(0);

        assertEquals(Arrays.asList(new ItemResponse("1", "key_1"), new ItemResponse("2", "sun_stone")), dungeonInfo.getInventory());

        // move player right again, should encounter door
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo(0);

        // get actual dungeon and check the player can move onto the door
        assertEquals(controller.getDungeon(0).getPlayerPosition(), new Position(3, 0));  
        
        // check that sun stone is used to open door ie both items are still in inv
        assertEquals(Arrays.asList(new ItemResponse("1", "key_1"), new ItemResponse("2", "sun_stone")), dungeonInfo.getInventory());

    }

    @Test
	public void testSunStoneAsGold() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testCollectiblesSunBribe", "Standard"));
	
		// Pick up gold to right of player
		controller.tick(null, Direction.RIGHT);

		// interact with mercenary
		assertDoesNotThrow(() -> controller.interact("1"));

		// cast into merc, check if ally
		Mercenary merc = (Mercenary) controller.getDungeon(0).getEntity("1");
		assertTrue(merc.getIsAlly());

        // player stil should have sunstone
        assertTrue(controller.getDungeon(0).getPlayer().getSunstoneStatus());
        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("2", "sun_stone")), dungeonInfo.getInventory());

		// wait for merc to move into player
		controller.tick(null, Direction.NONE);
		controller.tick(null, Direction.NONE);

		// Now merc is on player, check he moves around with player
		controller.tick(null, Direction.DOWN);
		Position player = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(controller.getDungeon(0).entityExists("mercenary", player));

		controller.tick(null, Direction.RIGHT);
		player = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(controller.getDungeon(0).entityExists("mercenary", player));
	}

    @Test
    public void testPotions() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesPotions", "Standard"));        
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "health_potion")), dungeonInfo.getInventory());

        controller.getDungeon(0).getPlayer().setHealth(1);
        assertEquals(1, controller.getDungeon(0).getPlayer().getHealth());

        // move player right again and use health potion, should pickup invis pot
        assertDoesNotThrow(() ->controller.tick("1", Direction.RIGHT));

        Player player = controller.getDungeon(0).getPlayer();
        // health should be full
        assertEquals(100, player.getHealth());

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo(0);

        // only invis pot should be in inventory, as health pot got used
        assertEquals(Arrays.asList(new ItemResponse("2", "invisibility_potion")), dungeonInfo.getInventory());

        // move player right again and use invis pot, should also pickup invicible pot
        assertDoesNotThrow(() ->controller.tick("2", Direction.RIGHT));
        assertTrue(!player.isVisible());

        // update dungeon response
        dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("3", "invincibility_potion")), dungeonInfo.getInventory());

        // merc should be adjacent to character right now
        // use invicible pot and move right
        assertDoesNotThrow(() ->controller.tick("3", Direction.RIGHT));

        // health should be full as mercenary runs away / player is invincible
        assertEquals(100, player.getHealth());
    }

    @Test
    public void testBomb() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesBomb", "Peaceful"));        
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // grab the info of dungeon
        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "bomb")), dungeonInfo.getInventory());

        // move to right (pushing the boulder onto the switch)
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // use the bomb
        assertDoesNotThrow(() ->controller.tick("1", Direction.NONE));

        // update info
        dungeonInfo = controller.getDungeonInfo(0);

        // player should be the only entity left
        assertEquals(1, dungeonInfo.getEntities().size());
    }

    @Test
    public void testArmourAndSword() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right to pickup sword
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesSword", "Standard"));        
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
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // check that durabilities went down
        Sword sword = (Sword) controller.getDungeon(0).getInventory().get(0);
        assertNotEquals(10, sword.getDurability()); 

        Armour armour = (Armour) controller.getDungeon(0).getInventory().get(1);
        assertNotEquals(10, armour.getDurability()); 

        Player player = controller.getDungeon(0).getPlayer();
        assertEquals(94, player.getHealth());
    }

    @Test
    public void testExceptions() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game
        assertDoesNotThrow(() -> controller.newGame("testCollectiblesException", "Standard"));
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        //try to use a non-usable item
        assertThrows(IllegalArgumentException.class, () ->controller.tick("1", Direction.NONE));

        // try to use an item thats not in inventory
        assertThrows(InvalidActionException.class, () ->controller.tick("2", Direction.NONE));
    }

    @Test
    public void testPickupTwoItemsConcurrently() {
        DungeonManiaController controller = new DungeonManiaController();

        // create a new game and move the player right to pickup treasure AND wood
        assertDoesNotThrow(() ->controller.newGame("testCollectiblesStack", "Peaceful"));        
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        // update dungeon response
        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);

        // both objects should be in the inventory
        assertEquals(Arrays.asList(new ItemResponse("1", "treasure"), new ItemResponse("2", "wood")), dungeonInfo.getInventory());
    }

	@Test
	public void testUseInvicinbleMercVertical() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() ->controller.newGame("testInvincibleMercVertical", "Standard"));        
		assertDoesNotThrow(() ->controller.tick(null, Direction.DOWN));
		Entity ent1 = controller.getDungeon(0).getEntity("2");
		Mercenary merc1 = (Mercenary) ent1;
		assertEquals(merc1.getPosition(), new Position(12, 11));
		Entity ent2 = controller.getDungeon(0).getEntity("3");
		Mercenary merc2 = (Mercenary) ent2;
		assertEquals(merc2.getPosition(), new Position(12, 15));
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		assertEquals(Arrays.asList(new ItemResponse("1", "invincibility_potion")), dungeonInfo.getInventory());
		// uses invincible potion and mercenary runs away from player
		assertDoesNotThrow(() ->controller.tick("1", Direction.NONE));
		assertEquals(merc1.getPosition(), new Position(12, 10));
		assertEquals(merc2.getPosition(), new Position(12, 16));
		for (int i = 0; i < 6; i++) {
			assertDoesNotThrow(() ->controller.tick(null, Direction.NONE));
		}
		// Invincible potion about to wear off
		assertEquals(merc1.getPosition(), new Position(12, 4));
		assertEquals(merc2.getPosition(), new Position(12, 22));
		controller.tick(null, Direction.NONE);
		// Invincible wears off, mercenary now wants to fight player
		assertEquals(merc1.getPosition(), new Position(12, 5));
		assertEquals(merc2.getPosition(), new Position(12, 21));
	}

	@Test
	public void testUseInvicinbleMercHorizon() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() ->controller.newGame("testInvincibleMercHorizon", "Standard"));        
		assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));
		Entity ent1 = controller.getDungeon(0).getEntity("2");
		Mercenary merc1 = (Mercenary) ent1;
		assertEquals(merc1.getPosition(), new Position(11, 12));
		Entity ent2 = controller.getDungeon(0).getEntity("3");
		Mercenary merc2 = (Mercenary) ent2;
		assertEquals(merc2.getPosition(), new Position(15, 12));
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		assertEquals(Arrays.asList(new ItemResponse("1", "invincibility_potion")), dungeonInfo.getInventory());
		// uses invincible potion and mercenary runs away from player
		assertDoesNotThrow(() ->controller.tick("1", Direction.NONE));
		assertEquals(merc1.getPosition(), new Position(10, 12));
		assertEquals(merc2.getPosition(), new Position(16, 12));
		for (int i = 0; i < 6; i++) {
			assertDoesNotThrow(() ->controller.tick(null, Direction.NONE));
		}
		// Invincible potion about to wear off
		assertEquals(merc1.getPosition(), new Position(4, 12));
		assertEquals(merc2.getPosition(), new Position(22, 12));
		controller.tick(null, Direction.NONE);
		// Invincible wears off, mercenary now wants to fight player
		assertEquals(merc1.getPosition(), new Position(5, 12));
		assertEquals(merc2.getPosition(), new Position(21, 12));
	}

}
