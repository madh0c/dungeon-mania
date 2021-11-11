package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.allEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.response.models.*;


public class Milestone3Test {

	@Test
    public void testMercenaryAvoidsSwamp() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testMercenaryAvoidsSwamp", "Standard"));

		// Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(2,0), true);
        EntityResponse startSwampInfo = new EntityResponse("1", "swamp_tile", new Position(1,0), false);
        EntityResponse startMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,0), true);

        startList.add(startPlayerInfo);
        startList.add(startSwampInfo);
        startList.add(startMercenaryInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

		// Move player away from the mercenary.
		controller.tick(null, Direction.NONE);

		// Assert the mercenary has not moved from spawn
        List<EntityResponse> expectedList = new ArrayList<EntityResponse>();

        EntityResponse expectedPlayerInfo = new EntityResponse("0", "player", new Position(2,0), true);
        EntityResponse expectedSwampInfo = new EntityResponse("1", "swamp_tile", new Position(1,0), false);
        EntityResponse expectedMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,1), true);

        expectedList.add(expectedPlayerInfo);
        expectedList.add(expectedSwampInfo);
        expectedList.add(expectedMercenaryInfo);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertEquals(expectedList, dREnd.getEntities());
    }

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
