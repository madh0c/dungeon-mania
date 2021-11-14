package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;

public class GoalsTest {

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
		assertEquals(controller.getDungeon(0).getGoals(), ":treasure");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testEnemyReappearing() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testAndGoal", "Hard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:enemies AND :treasure)");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), ":treasure");
		for (int i = 0; i < 10; i++) {
			controller.tick(null, Direction.UP);
		}
		assertEquals(controller.getDungeon(0).getGoals(), "(:enemies AND :treasure)");
	}

	@Test 
	public void testOrGoal() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testOrGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:boulders OR :exit)");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testTreasureExitGoal () {
		DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testTreasureExitGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:treasure AND :exit)");
		controller.tick(null, Direction.DOWN);
		assertEquals(controller.getDungeon(0).getGoals(), ":treasure");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "(:treasure AND :exit)");
		controller.tick(null, Direction.UP);
		assertEquals(controller.getDungeon(0).getGoals(), ":exit");
		controller.tick(null, Direction.DOWN);
		controller.tick(null, Direction.LEFT);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testAndOr1() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testAndOrGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:enemies AND (:boulders OR :treasure))");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "(:boulders OR :treasure)");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testAndOr2() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testAndOrGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:enemies AND (:boulders OR :treasure))");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "(:boulders OR :treasure)");
		controller.tick(null, Direction.DOWN);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testOrAnd1() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testOrAndGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:boulders OR (:exit AND :enemies))");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "(:boulders OR :exit)");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testOrAnd2() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testOrAndGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:boulders OR (:exit AND :enemies))");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "(:boulders OR :exit)");
		controller.tick(null, Direction.DOWN);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testOrOr1() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testOrOrGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:boulders OR (:exit OR :enemies))");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testOrOr2() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testOrOrGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:boulders OR (:exit OR :enemies))");
		controller.tick(null, Direction.DOWN);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test 
	public void testAndAnd() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testAndAndGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:boulders AND (:exit AND :enemies))");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "(:boulders AND :exit)");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), ":exit");
		controller.tick(null, Direction.DOWN);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testComplicatedGoal1() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testComplicatedGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:enemies AND (:exit OR (:treasure AND :boulders)))");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "((:exit OR (:treasure AND :boulders)))");
		controller.tick(null, Direction.DOWN);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testComplicatedGoal2() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testComplicatedGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:enemies AND (:exit OR (:treasure AND :boulders)))");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "((:exit OR (:treasure AND :boulders)))");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "(:exit OR :treasure)");
		controller.tick(null, Direction.DOWN);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}

	@Test
	public void testComplicatedGoalAnd() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testComplicatedAnd", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:enemies AND (:exit AND (:treasure AND :boulders)))");
		controller.tick(null, Direction.RIGHT);
		assertDoesNotThrow(() -> controller.interact("2"));
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "((:exit AND (:treasure AND :boulders)))");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "((:exit AND :treasure))");
		controller.tick(null, Direction.DOWN);
		assertEquals(controller.getDungeon(0).getGoals(), ":exit");
		controller.tick(null, Direction.LEFT);
		assertEquals(controller.getDungeon(0).getGoals(), "");
	}
	
	@Test
	public void saveGoals() {
		DungeonManiaController controller = new DungeonManiaController();
		controller.newGame("testComplicatedGoal", "Standard");
		assertEquals(controller.getDungeon(0).getGoals(), "(:enemies AND (:exit OR (:treasure AND :boulders)))");
		controller.tick(null, Direction.RIGHT);
		assertEquals(controller.getDungeon(0).getGoals(), "((:exit OR (:treasure AND :boulders)))");
		assertDoesNotThrow(() -> controller.saveGame("saveGoalsAnd-1636079593059"));
		assertDoesNotThrow(() -> controller.loadGame("saveGoalsAnd-1636079593059"));
		assertEquals(controller.getDungeon(0).getGoals(), "((:exit OR (:treasure AND :boulders)))");
	}
	
}
