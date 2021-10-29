package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.allEntities.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BuildableTest {
    //Not enough materials to build bow.
    @Test
    public void testInvalidBuildBow () {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildableMap", "peaceful");
        //Has 0 arrows.
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        //collects one arrow.
        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        //Has 3 arrows now.
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
    }
    //Not enough materials to build shield.
    @Test
    public void testInvalidBuildShield() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildableMap", "peaceful");
        //collects treasure
        controller.tick("", Direction.DOWN);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        controller.tick("", Direction.DOWN);
        //collects wood
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.LEFT);
        assertThrows(InvalidActionException.class, () -> controller.build("shield"));
    }
    //Build a bow and is stored in inventory.
    @Test
    public void testBuildBow() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildableMap", "peaceful");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        //collects one arrow
        controller.tick("", Direction.RIGHT);
        //collects wood
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.RIGHT);
        //collects another arrow
        controller.tick("", Direction.UP);
        assertThrows(InvalidActionException.class, () -> controller.build("bow"));
        //Has 3 arrows now and bow.
        controller.tick("", Direction.RIGHT);
        assertEquals(Arrays.asList(new ItemResponse("1", "arrow"), new ItemResponse("2", "wood"), new ItemResponse("3", "arrow"), (new ItemResponse("4", "arrow"))), dungeonInfo.getInventory());
        assertDoesNotThrow(() -> controller.build("bow"));
        assertEquals(Arrays.asList(new ItemResponse("1", "bow")), dungeonInfo.getInventory());
    }
    //Build a shield with treasure.
    @Test
    public void testBuildShieldTreasure() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildableMap", "peaceful");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        assertDoesNotThrow(() -> controller.build("shield"));
        assertEquals(Arrays.asList(new ItemResponse("1", "key"), new ItemResponse("2", "shield")), dungeonInfo.getInventory());
    }
    //Builds a shield with key.
    @Test
    public void testBuildShieldKey() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildableMap", "peaceful");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        controller.tick("", Direction.RIGHT);
        assertEquals(Arrays.asList(new ItemResponse("1", "arrow")), dungeonInfo.getInventory());
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.LEFT);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        assertEquals(Arrays.asList(new ItemResponse("1", "arrow"), new ItemResponse("2", "wood"), new ItemResponse("3", "wood"), new ItemResponse("4", "key")), dungeonInfo.getInventory());
        assertDoesNotThrow(() -> controller.build("shield"));
        assertEquals(Arrays.asList(new ItemResponse("1", "arrow"), new ItemResponse("2", "shield")), dungeonInfo.getInventory());
    }

    //Test bow damage
    @Test
    public void testBowDamange() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildableMap", "peaceful");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        assertDoesNotThrow(() -> controller.build("bow"));
		assertEquals(Arrays.asList(new ItemResponse("1", "bow")), dungeonInfo.getInventory());        
		Player player = controller.getDungeon(0).getPlayer();
		assertEquals(25, player.getAttackDamage());
		//Both mercenary and player should be on same square
		controller.tick("", Direction.RIGHT);
		
		// TODO: Simulate a fight between player and mercenary 
			
    }
    
    //Test shield blocking
    public void testShieldBlock() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildableMap", "peaceful");
		DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        assertDoesNotThrow(() -> controller.build("shield"));
		assertEquals(Arrays.asList(new ItemResponse("1", "shield")), dungeonInfo.getInventory());        
        //Block smth
		controller.tick("", Direction.DOWN);
		controller.tick("", Direction.DOWN);
		//Both mercenary and player should be on same square
		
		// TODO: Simulate a fight between player and mercenary 

    }
}
