package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import dungeonmania.exceptions.InvalidActionException;


import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;


public class ControllerTest {
	@Test
    public void newGameTestNonExistentDungeon() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.newGame("null", "Standard"));
    }

	@Test
    public void newGameTestIllegalGameMode() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.newGame("portals", "illegal"));
    }

	@Test
    public void newGameTestIllegalArguments() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.newGame("illegal", "arguments"));
    }

	@Test
    public void testValidLoadGame() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.loadGame("null"));
    }

	@Test
    public void tickTestInvalidItemUsed() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.tick("invalid", Direction.RIGHT));
    }

	@Test
    public void tickTestItemNotInInventory() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("portals", "Standard"));
	
        assertThrows(InvalidActionException.class, () -> controller.tick("bomb", Direction.LEFT));
    }

	@Test
    public void interactEntityDoesNotExist() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("portals", "Standard"));
	
        assertThrows(IllegalArgumentException.class, () -> controller.interact("4"));
    }

	@Test
    public void interactMercenaryOutOfRange() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testInteractMercenaryOutOfRange", "Standard"));
	
        assertThrows(InvalidActionException.class, () -> controller.interact("1"));
    }

	@Test
    public void interactMercenaryNoGold() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testInteractMercenaryNoGold", "Standard"));
	
        assertThrows(InvalidActionException.class, () -> controller.interact("1"));
    }

	@Test
    public void interactZombieSpawnerOutOfRange() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testInteractZombieSpawnerOutOfRange", "Standard"));
	
        assertThrows(InvalidActionException.class, () -> controller.interact("1"));
    }

	@Test
    public void interactZombieSpawnerWithoutWeapon() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testInteractZombieSpawnerWithoutWeapon", "Standard"));
	
        assertThrows(InvalidActionException.class, () -> controller.interact("1"));
    }

	@Test
    public void interactSpawnerDungeons() {
		int i = 0;
        for (String dungeon : DungeonManiaController.dungeons()) {
			System.out.println(dungeon);
			i++;
		} System.out.println(i);

    }

	


}


