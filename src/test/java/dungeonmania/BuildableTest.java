package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.allEntities.Assassin;
import dungeonmania.allEntities.Bow;
import dungeonmania.allEntities.Mercenary;
import dungeonmania.allEntities.MidnightArmour;
import dungeonmania.allEntities.Player;
import dungeonmania.allEntities.Shield;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BuildableTest {
    @Test 
	public void testInvalidBuildMethod () {
		DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testBuildableMap", "Peaceful");
		controller.tick(null, Direction.RIGHT);
		controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
		assertThrows(IllegalArgumentException.class, () -> controller.build("scythe"));
	}
	
	//Not enough materials to build bow.
    @Test
    public void testInvalidBuildBow () {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testBuildableMap", "Peaceful");
        //Has 0 arrows.
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        //collects one arrow.
        controller.tick(null, Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        //Has 3 arrows now.
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
    }
    //Not enough materials to build shield.
    @Test
    public void testInvalidBuildShield() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testBuildableMap", "Peaceful");
        //collects treasure
        controller.tick(null, Direction.DOWN);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        controller.tick(null, Direction.DOWN);
        //collects wood
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
		controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
    }
    //Build a bow and is stored in inventory.
    @Test
    public void testBuildBow() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testBuildableMap", "Peaceful");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        //collects one arrow
        controller.tick(null, Direction.RIGHT);
        //collects wood
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        //collects another arrow
        controller.tick(null, Direction.UP);
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        //Has 3 arrows now and bow.
        controller.tick(null, Direction.RIGHT);
		dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "arrow"), new ItemResponse("2", "wood"), new ItemResponse("3", "arrow"), (new ItemResponse("4", "arrow"))), dungeonInfo.getInventory());
        assertDoesNotThrow(() -> controller.build("bow"));
		dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("12", "bow")), dungeonInfo.getInventory());
    }
    //Build a shield with treasure.
    @Test
    public void testBuildShieldTreasure() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testBuildableMap", "Peaceful");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        assertDoesNotThrow(() -> controller.build("shield"));
		dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("10", "key_1"), new ItemResponse("12", "shield")), dungeonInfo.getInventory());
    }
    //Builds a shield with key.
    @Test
    public void testBuildShieldKey() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testBuildableMap", "Peaceful");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
		dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "arrow"), new ItemResponse("2", "wood"), new ItemResponse("8", "wood"), new ItemResponse("9", "wood"), new ItemResponse("10", "key_1")), dungeonInfo.getInventory());
        assertDoesNotThrow(() -> controller.build("shield"));
		dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "arrow"), new ItemResponse("9", "wood"), new ItemResponse("12", "shield")), dungeonInfo.getInventory());
    }

    //Test bow damage
    @Test
    public void testBowDamange() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testBuildableMap", "Standard");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        assertDoesNotThrow(() -> controller.build("bow"));
		dungeonInfo = controller.getDungeonInfo(0);
		assertEquals(Arrays.asList(new ItemResponse("12", "bow")), dungeonInfo.getInventory());        
		Player player = controller.getDungeon(0).getPlayer();
		Bow bow = (Bow) controller.getDungeon(0).getInventory().get(0);
		assertEquals(5, bow.getDurability()); 
		//Both mercenary and player should be on same square
		controller.tick(null, Direction.RIGHT);
		//Simulate a fight between player and mercenary 
		assertEquals(85, player.getHealth());	
		assertEquals(4, bow.getDurability()); 
    }
    
    //Test shield blocking
	@Test
    public void testShieldBlock() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testBuildableMap", "Standard");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        assertDoesNotThrow(() -> controller.build("shield"));
		dungeonInfo = controller.getDungeonInfo(0);
		assertEquals(Arrays.asList(new ItemResponse("12", "shield")), dungeonInfo.getInventory());        
        //Block smth
		controller.tick(null, Direction.DOWN);
		controller.tick(null, Direction.DOWN);
		Player player = controller.getDungeon(0).getPlayer();
		Shield shield = (Shield) controller.getDungeon(0).getInventory().get(0);
		assertEquals(5, shield.getDurability()); 
		// Simulate a fight between player and mercenary 
		controller.tick(null, Direction.DOWN);
		assertEquals(97, player.getHealth());
		assertEquals(4, shield.getDurability()); 

    }
	@Test
	public void testInvalidBuildSceptre() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testBuildSceptreMap", "Standard");
		controller.tick(null, Direction.RIGHT);
		//Picks up 1 arrow
        assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
		controller.tick(null, Direction.RIGHT);
		//Picks up 1 treasure
		assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
		controller.tick(null, Direction.RIGHT);
		//Picks up 1 sunstone
		assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
	}

	@Test
	public void testBuildSceptre() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testBuildSceptreMap", "Standard");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		controller.tick(null, Direction.DOWN);
		controller.tick(null, Direction.DOWN);
		//Picks up 1 arrow
        assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
		controller.tick(null, Direction.DOWN);
		//Picks up 1 key
		assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
		controller.tick(null, Direction.DOWN);
		//Picks up 1 arrow
		assertThrows(InvalidActionException.class, () -> controller.build("sceptre"));
		controller.tick(null, Direction.DOWN);
		//Picks up 1 sunstone 
		assertDoesNotThrow(() -> controller.build("sceptre"));
		dungeonInfo = controller.getDungeonInfo(0);
		assertEquals(Arrays.asList(new ItemResponse("9", "sceptre")), dungeonInfo.getInventory());        
	}

	@Test
	public void testSceptreControlMerc() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testSceptreControlMerc", "Standard");
		controller.tick(null, Direction.RIGHT);
		controller.tick(null, Direction.RIGHT);
		controller.tick(null, Direction.RIGHT);
		assertDoesNotThrow(() -> controller.build("sceptre"));
		assertEquals("sceptre", controller.getDungeon(0).getInventory().get(0).getType());
		assertDoesNotThrow(() -> controller.interact("4"));
		assertTrue(controller.getDungeon(0).getInventory().isEmpty());
		Mercenary merc = (Mercenary) controller.getDungeon(0).getEntity("4");
		assertTrue(merc.getIsAlly());
		//Merc moves into player
		//1 tick
		controller.tick(null, Direction.RIGHT);
		//Mercenary still exists
		controller.tick(null, Direction.DOWN);
		Position player = controller.getDungeon(0).getEntity("0").getPosition();
		assertTrue(controller.getDungeon(0).entityExists("mercenary", player));
	}

	@Test
	public void testSceptreControlDuration() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testSceptreControlMerc", "Standard");
		controller.tick(null, Direction.RIGHT);
		controller.tick(null, Direction.RIGHT);
		controller.tick(null, Direction.RIGHT);
		assertDoesNotThrow(() -> controller.build("sceptre"));
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		//Sceptre built is added to inventory
		assertEquals(Arrays.asList(new ItemResponse("5", "sceptre")), dungeonInfo.getInventory());        
		assertDoesNotThrow(() -> controller.interact("4"));
		dungeonInfo = controller.getDungeonInfo(0);
		//After interaction, sceptre is removed from inventory
		assertFalse(dungeonInfo.getInventory().contains(new ItemResponse("5", "sceptre")));        
		Mercenary merc = (Mercenary) controller.getDungeon(0).getEntity("4");
		assertTrue(merc.getIsAlly());
		for (int i = 0; i < 10; i++) {
			controller.tick(null, Direction.DOWN);
		}
		assertTrue(merc.getIsAlly());
		controller.tick(null, Direction.LEFT);
		//Mercenary isn't an ally any longer
		assertTrue(!merc.getIsAlly());
		//Can fight mercenary now
		controller.tick(null, Direction.UP);
		Player player = controller.getDungeon(0).getPlayer();
		assertEquals(85 ,player.getHealth());
	}

	@Test 
	public void testMultipleSceptreControlDuration() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testMultipleSceptreDuration", "Standard");
		controller.tick(null, Direction.RIGHT);
		controller.tick(null, Direction.RIGHT);
		controller.tick(null, Direction.RIGHT);
		//Makes the first sceptre
		assertDoesNotThrow(() -> controller.build("sceptre"));
		assertDoesNotThrow(() -> controller.interact("4"));
		Mercenary merc = (Mercenary) controller.getDungeon(0).getEntity("4");
		assertTrue(merc.getIsAlly());
		controller.tick(null, Direction.DOWN);
		controller.tick(null, Direction.DOWN);
		controller.tick(null, Direction.DOWN);
		//Makes the second sceptre
		assertDoesNotThrow(() -> controller.build("sceptre"));
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		//Sceptre built is added to the inventory
		assertEquals(Arrays.asList(new ItemResponse("10", "sceptre")), dungeonInfo.getInventory());        
		assertDoesNotThrow(() -> controller.interact("8"));
		dungeonInfo = controller.getDungeonInfo(0);
		//After interaciton with assassin, sceptre is removed from inventory
		assertFalse(dungeonInfo.getInventory().contains(new ItemResponse("10", "sceptre"))); 
		Assassin assassin = (Assassin) controller.getDungeon(0).getEntity("8");
		assertTrue(assassin.getIsAlly());
		for (int i = 0; i < 7; i++) {
			controller.tick(null, Direction.LEFT);
		}
		//Merc1 on last tick for sceptre
		assertTrue(merc.getIsAlly());
		controller.tick(null, Direction.LEFT);
		//Merc1 no longer an ally
		assertTrue(!merc.getIsAlly());
		//Kill the merc since no longer an ally, the friendly assassin is on player
		controller.tick(null, Direction.RIGHT);
		assertEquals(85, controller.getDungeon(0).getPlayer().getHealth());
		controller.tick(null, Direction.RIGHT);
		//Assassin on last tick for sceptre
		assertTrue(assassin.getIsAlly());
		controller.tick(null, Direction.RIGHT);
		//Assassin no longer an ally
		assertTrue(!assassin.getIsAlly());
		controller.tick(null, Direction.LEFT);
		//Fights Assassin
		Position player = controller.getDungeon(0).getEntity("0").getPosition();
		assertFalse(controller.getDungeon(0).entityExists("assassin", player));
	}

	@Test
	public void testInvalidBuildMidnight() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testBuildMidnightMap", "Hard");
		controller.tick(null, Direction.RIGHT);
		//Picks up armour
		assertThrows(InvalidActionException.class, () -> controller.build("midnight_armour"));
		//Zombie spawning
		for (int i = 0; i < 15; i++) {
			controller.tick(null, Direction.NONE);

		}
		controller.tick(null, Direction.RIGHT);
		//Picks up sun stone
		assertThrows(InvalidActionException.class, () -> controller.build("midnight_armour"));
	}

	@Test
	public void testBuildMidnight() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testBuildMidnightMap", "Standard");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		controller.tick(null, Direction.DOWN);
		controller.tick(null, Direction.UP);
		controller.tick(null, Direction.RIGHT);
		//Picks up armour
		assertThrows(InvalidActionException.class, () -> controller.build("midnight_armour"));
		controller.tick(null, Direction.RIGHT);
		//Picks up sun stone
		assertDoesNotThrow(() -> controller.build("midnight_armour"));
		dungeonInfo = controller.getDungeonInfo(0);
		assertEquals(Arrays.asList(new ItemResponse("1", "armour"), new ItemResponse("5", "midnight_armour")), dungeonInfo.getInventory()); 
	}

	@Test
	public void testMidnightAttackDefence() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testMidnightAttackDefence", "Standard");
		controller.tick(null, Direction.RIGHT);
		controller.tick(null, Direction.RIGHT);
		//Build midnight_armour
		assertDoesNotThrow(() -> controller.build("midnight_armour"));
		//Fights mercenary
		controller.tick(null, Direction.RIGHT);
		Player player = controller.getDungeon(0).getPlayer();
		//Get health of player after fight to see midnight armour protection
		MidnightArmour midnightArmour = (MidnightArmour) controller.getDungeon(0).getInventory().get(0);
		assertEquals(97, player.getHealth());
		assertEquals(4, midnightArmour.getDurability()); 
	}
}
