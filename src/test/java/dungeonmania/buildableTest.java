package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
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
        assertEquals(new ItemResponse("1", "arrow"), controller.getItemInfo("1"));
        assertEquals(new ItemResponse("2", "wood"), controller.getItemInfo("2"));
        assertEquals(new ItemResponse("3", "arrow"), controller.getItemInfo("3"));
        assertEquals(new ItemResponse("4", "arrow"), controller.getItemInfo("4"));
        assertDoesNotThrow(() -> controller.build("bow"));
        assertEquals(new ItemResponse("1", "bow"), controller.getItemInfo("1"));
    }
    //Build a shield with treasure.
    @Test
    public void testBuildShieldTreasure() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildableMap", "peaceful");
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        assertDoesNotThrow(() -> controller.build("shield"));
        assertEquals(new ItemResponse("1", "key"), controller.getItemInfo("1"));
        assertEquals(new ItemResponse("2", "shield"), controller.getItemInfo("2"));
    }
    //Builds a shield with key.
    @Test
    public void testBuildShieldKey() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildableMap", "peaceful");
        controller.tick("", Direction.RIGHT);
        assertEquals(new ItemResponse("1", "arrow"), controller.getItemInfo("1"));
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.LEFT);
        assertEquals(new ItemResponse("2", "wood"), controller.getItemInfo("2"));
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        assertEquals(new ItemResponse("4", "key"), controller.getItemInfo("4"));
        assertDoesNotThrow(() -> controller.build("shield"));
        assertEquals(new ItemResponse("2", "shield"), controller.getItemInfo("2"));
    }

    //Test bow damage
    @Test
    public void testBowDamange() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildableMap", "peaceful");
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        controller.tick("", Direction.RIGHT);
        assertDoesNotThrow(() -> controller.build("bow"));
        assertEquals(new ItemResponse("1", "bow"), controller.getItemInfo("1"));
        //Both mercenary and player should be on same square
		assertEquals(25, controller.getEntityInfo("1").getAttackDamage());
		controller.tick("", Direction.RIGHT);
		//They start fighting
			
    }
    
    //Test shield blocking
    public void testShieldBlock() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("buildableMap", "peaceful");
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        assertDoesNotThrow(() -> controller.build("shield"));
        assertEquals(new ItemResponse("1", "shield"), controller.getItemInfo("1"));
        //Block smth
		controller.tick("", Direction.DOWN);
		controller.tick("", Direction.DOWN);
		//Both mercenary and player should be on same square
		

    }
}
