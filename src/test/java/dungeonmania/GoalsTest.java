package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;

public class GoalsTest {

	@Test
	public void testTreasureGoal () {
		DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testTreasureGoal", "Peaceful");
		//assertEquals(controller.getDungeon(0).getGoals(), "treasure");
		//controller.tick(null, Direction.RIGHT);
		//assertEquals(controller.getDungeon(0).getGoals(), "");
	}
	
}
