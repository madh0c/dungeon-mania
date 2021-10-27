package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;

public class buildableTest {
    //Not enough materials to build bow.
    @Test
    public void testInvalidBuildBow () {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "peaceful");
        for (int i = 0; i < 13; i++) {
            controller.tick("", Direction.DOWN);
        }
        //collects one arrow
        for (int i = 0; i < 10; i++) {
            controller.tick("", Direction.RIGHT);
        }
        assertThrows(InvalidActionException.class, () -> controller.build("Bow"));
        //collects another arrow
        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("Bow"));
        controller.tick("", Direction.LEFT);
        //Has 3 arrows now.
        controller.tick("", Direction.UP);
        assertThrows(InvalidActionException.class, () -> controller.build("Bow"));
    }
    //Not enough materials to build shield.
    @Test
    public void testInvalidBuildShield() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "peaceful");
        for (int i = 0; i < 8; i++) {
            controller.tick("", Direction.DOWN);
        }
        for (int i = 0; i < 6; i++) {
            controller.tick("", Direction.RIGHT);
        }
        //collects treasure
        controller.tick("", Direction.DOWN);
        assertThrows(InvalidActionException.class, () -> controller.build("Shield"));
        for (int i = 0; i < 4; i++) {
            controller.tick("", Direction.RIGHT);
        }
        //collects key
        assertThrows(InvalidActionException.class, () -> controller.build("Shield"));
        //collects wood
        controller.tick("", Direction.DOWN);
        assertThrows(InvalidActionException.class, () -> controller.build("Shield"));
    }
    //Build a bow and is stored in inventory.
    @Test
    public void testBuildBow() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "peaceful");
        for (int i = 0; i < 13; i++) {
            controller.tick("", Direction.DOWN);
        }
        //collects one arrow
        for (int i = 0; i < 10; i++) {
            controller.tick("", Direction.RIGHT);
        }
        //collects another arrow
        controller.tick("", Direction.RIGHT);
        //collects wood
        controller.tick("", Direction.RIGHT);
        assertThrows(InvalidActionException.class, () -> controller.build("Bow"));
        controller.tick("", Direction.LEFT);
        controller.tick("", Direction.LEFT);
        //Has 3 arrows now and bow.
        controller.tick("", Direction.UP);
        assertDoesNotThrow(() -> controller.build("Bow"));
        assertEquals(new ItemResponse("1", "Bow"), controller.getItemInfo("1"));

    }
    //Build a shield with treasure or key 
    @Test
    public void testBuildShieldTreasure() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "peaceful");
        for (int i = 0; i < 8; i++) {
            controller.tick("", Direction.DOWN);
        }
        for (int i = 0; i < 6; i++) {
            controller.tick("", Direction.RIGHT);
        }
        controller.tick("", Direction.DOWN);
        for (int i = 0; i < 4; i++) {
            controller.tick("", Direction.RIGHT);
        }
        controller.tick("", Direction.DOWN);
        controller.tick("", Direction.DOWN);
        assertDoesNotThrow(() -> controller.build("Shield"));
        assertEquals(new ItemResponse("1", "Key"), controller.getItemInfo("1"));
        assertEquals(new ItemResponse("2", "Shield"), controller.getItemInfo("2"));

    }
    
    @Test
    public void testBuildShieldKey() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "peaceful");
        for (int i = 0; i < 10; i++) {
            controller.tick("", Direction.RIGHT);
        }

    }

}
