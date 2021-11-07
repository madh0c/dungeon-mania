package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;

public class GoalsTest {

	@Test
	public void testTreasureGoal () {
		DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testTreasureGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), ":treasure");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testEnemiesGoal () {
		DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testEnemiesGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), ":enemies");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testAndGoal() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testAndGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:enemies AND :treasure)");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "(:enemies AND :treasure)");
		controller.tick(null, Direction.RIGHT);
		//assertEquals(controller.getDungeon(0).getGoals(), "");
	}
	
}
