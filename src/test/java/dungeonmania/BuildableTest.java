package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.allEntities.Bow;
import dungeonmania.allEntities.Player;
import dungeonmania.allEntities.Shield;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
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
        assertEquals(Arrays.asList(new ItemResponse("10", "key"), new ItemResponse("12", "shield")), dungeonInfo.getInventory());
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
        assertEquals(Arrays.asList(new ItemResponse("1", "arrow"), new ItemResponse("2", "wood"), new ItemResponse("8", "wood"), new ItemResponse("9", "wood"), new ItemResponse("10", "key")), dungeonInfo.getInventory());
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
}
