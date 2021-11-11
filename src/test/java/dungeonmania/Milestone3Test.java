package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.allEntities.*;
import dungeonmania.util.Position;

public class Milestone3Test {
	
	@Test
    public void testGenerateDungeon() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.generateDungeon(0, 0, 25, 25, "Hard"));

		assertTrue(controller.getCurrentDungeon().getEntity(new Position(0, 0)) instanceof Player);
		assertTrue(controller.getCurrentDungeon().getEntity(new Position(25, 25)) instanceof Exit);
    }

	@Test
    public void testGenerateDungeonII() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.generateDungeon(0, 0, 25, 25, "Hard"));
		String dungeonGoals = controller.getCurrentDungeon().getGoals();
		assertTrue(dungeonGoals.equals(":exit"));
    }
}
