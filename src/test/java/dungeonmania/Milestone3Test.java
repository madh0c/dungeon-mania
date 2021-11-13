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
    public void testZombieStuckInSwamp() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testZombieStuckInSwamp", "Standard"));

		// Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(2,2), true);
        EntityResponse startSwampInfo = new EntityResponse("1", "swamp_tile", new Position(2,1), false);
        EntityResponse startSpawnInfo = new EntityResponse("2", "zombie_toast_spawner", new Position(1,1), true);
		EntityResponse startWall1Info = new EntityResponse("3", "wall", new Position(0,1), false);
		EntityResponse startWall2Info = new EntityResponse("4", "wall", new Position(1,0), false);
		EntityResponse startWall3Info = new EntityResponse("5", "wall", new Position(1,2), false);

        startList.add(startPlayerInfo);
        startList.add(startSwampInfo);
        startList.add(startSpawnInfo);
		startList.add(startWall1Info);
        startList.add(startWall2Info);
        startList.add(startWall3Info);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

		// Move player away from the mercenary.
		for (int i = 0; i < 21; i++) {
			controller.tick(null, Direction.NONE);
		}

		// // Assert correct spawn positions
        // List<EntityResponse> midList = new ArrayList<EntityResponse>();

        // EntityResponse midPlayerInfo = new EntityResponse("0", "player", new Position(2,2), true);
        // EntityResponse midSwampInfo = new EntityResponse("1", "swamp_tile", new Position(2,1), false);
        // EntityResponse midSpawnInfo = new EntityResponse("2", "zombie_toast_spawner", new Position(1,1), true);
		// EntityResponse midWall1Info = new EntityResponse("3", "wall", new Position(0,1), false);
		// EntityResponse midWall2Info = new EntityResponse("4", "wall", new Position(1,0), false);
		// EntityResponse midWall3Info = new EntityResponse("5", "wall", new Position(1,2), false);
		// EntityResponse midZombInfo = new EntityResponse("7", "zombie_toast", new Position(2,1), false);

        // midList.add(midPlayerInfo);
        // midList.add(midSwampInfo);
        // midList.add(midSpawnInfo);
		// midList.add(midWall1Info);
        // midList.add(midWall2Info);
        // midList.add(midWall3Info);
		// midList.add(midZombInfo);

        // DungeonResponse dRMid = controller.getDungeonInfo(0);
        // assertEquals(midList, dRMid.getEntities());

		controller.tick(null, Direction.NONE);

		// Assert the zombie is still stuck in the swamp tile
        Position swampPos = new Position(2,1);
        Dungeon dREnd = controller.getDungeon(0);
        assertTrue(dREnd.getEntitiesOnCell(swampPos).size() == 2);
    }

	@Test
    public void testGenerateDungeon() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.generateDungeon(0, 0, 25, 25, "hard"));

		assertTrue(controller.getCurrentDungeon().getEntity(new Position(0, 0)) instanceof Player);
		assertTrue(controller.getCurrentDungeon().getEntity(new Position(25, 25)) instanceof Exit);
    }

	@Test
    public void testGenerateDungeonII() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.generateDungeon(0, 0, 25, 25, "hard"));
		String dungeonGoals = controller.getCurrentDungeon().getGoals();
		assertTrue(dungeonGoals.equals(":exit"));
    }
}
