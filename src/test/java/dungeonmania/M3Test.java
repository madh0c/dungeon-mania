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


public class M3Test {

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
		controller.tick(null, Direction.NONE);

		// Assert the zombie is still stuck in the swamp tile
        Position swampPos = new Position(2,1);
        Dungeon dREnd = controller.getDungeon(0);
        assertTrue(dREnd.getEntitiesOnCell(swampPos).size() == 2);
    }

    @Test
    public void testGenerateDungeonI() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.generateDungeon(0, 0, 25, 25, "peaceful"));

		assertTrue(controller.getCurrentDungeon().getEntity(new Position(0, 0)) instanceof Player);
		assertTrue(controller.getCurrentDungeon().getEntity(new Position(25, 25)) instanceof Exit);
    }

    @Test
    public void testGenerateDungeonII() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.generateDungeon(11, 11, 23, 23, "standard"));

		String dungeonGoals = controller.getCurrentDungeon().getGoals();
        assertTrue(dungeonGoals.equals(":exit"));
    }


	@Test
    public void testGenerateDungeonIII() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.generateDungeon(2, 2, 24, 24, "hard"));

		assertTrue(controller.getCurrentDungeon().getEntity(new Position(2, 2)) instanceof Player);
		assertTrue(controller.getCurrentDungeon().getEntity(new Position(24, 24)) instanceof Exit);
    }

	@Test
    public void testGenerateDungeonIV() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.generateDungeon(8, 8, 11, 11, "peaceful"));
		String dungeonGoals = controller.getCurrentDungeon().getGoals();
		assertTrue(dungeonGoals.equals(":exit"));
    }

    @Test
    public void testGenerateDungeonV() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.generateDungeon(8, 11, 23, 8, "standard"));
		String dungeonGoals = controller.getCurrentDungeon().getGoals();
		assertTrue(dungeonGoals.equals(":exit"));
    }

    @Test
    public void testSimpleRewind() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("timeTravel", "Standard"));

        for (int i = 0; i < 10; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        for (int i = 0; i < 3; i++) {
            controller.tick(null, Direction.DOWN);
        }

        for (int i = 0; i < 2; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        assertDoesNotThrow(() -> controller.rewind(5));
        assertDoesNotThrow(() -> controller.saveGame("testSimpleRewind-1636079593059"));

        List<Entity> allEnt = controller.getCurrentDungeon().getEntities();

        assertEquals(new Position(11, 1), allEnt.get(19).getPosition());
        assertEquals("older_player", allEnt.get(19).getType());

        assertEquals(new Position(13, 4), allEnt.get(118).getPosition());
        assertEquals("player", allEnt.get(118).getType());

        assertEquals(new Position(7, 1), allEnt.get(41).getPosition());
        assertEquals("mercenary", allEnt.get(41).getType());

        assertEquals(new Position(0, 0), allEnt.get(0).getPosition());
        assertEquals("wall", allEnt.get(0).getType());

    }

    @Test
    public void testCantRewindLessThanZero() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("timeTravel", "Standard"));
        
        controller.tick(null, Direction.RIGHT);

        DungeonResponse dR = controller.getDungeonInfo(controller.getCurrentDungeon().getId());
        
        assertDoesNotThrow(() -> controller.rewind(5));
        
        assertEquals(dR, controller.getDungeonInfo(controller.getCurrentDungeon().getId()));
    }

    /**
     * Travel through a time travelling portal after 30 ticks. The player should be on the other side of the portal
     * and an older player should spawn where the player used to be.
     */
    @Test
    public void testTimeTravellingPortal() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testTimeTravellingPortal", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startTreasureInfo = new EntityResponse("1", "treasure", new Position(1,0), false);
        EntityResponse startTimePortalInfo = new EntityResponse("2", "time_travelling_portal", new Position(2,0), false);

        startList.add(startPlayerInfo);
        startList.add(startTreasureInfo);
        startList.add(startTimePortalInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());
        
        controller.tick(null, Direction.RIGHT);

        for (int i = 0; i < 30; i++) {
            controller.tick(null, Direction.NONE);
        }

        controller.tick(null, Direction.RIGHT);

        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse endOlderPlayerInfo = new EntityResponse("0", "older_player", new Position(1,0), false);
        EntityResponse endTimePortalInfo = new EntityResponse("2", "time_travelling_portal", new Position(2,0), false);
        EntityResponse endPlayerInfo = new EntityResponse("4", "player", new Position(3,0), true);

        endList.add(endOlderPlayerInfo);
        endList.add(endTimePortalInfo);
        endList.add(endPlayerInfo);

        DungeonResponse dREnd = controller.getDungeonInfo(1);
        assertEquals(endList, dREnd.getEntities());
    }

    /**
     * Pick up a treasure and then travel through the portal 30 ticks later. You should be on the other side of the portal
     * and the coin should be where it was before.
     */
    @Test
    public void testOlderPlayerRetraceStepsI() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testTimeTravellingPortal", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startTreasureInfo = new EntityResponse("1", "treasure", new Position(1,0), false);
        EntityResponse startTimePortalInfo = new EntityResponse("2", "time_travelling_portal", new Position(2,0), false);

        startList.add(startPlayerInfo);
        startList.add(startTreasureInfo);
        startList.add(startTimePortalInfo);

        DungeonResponse dR = controller.getDungeonInfo(0);
        assertEquals(startList, dR.getEntities());
        
        controller.tick(null, Direction.RIGHT);
        
        for (int i = 0; i < 6; i++) {
            controller.tick(null, Direction.UP);
            controller.tick(null, Direction.DOWN);
            controller.tick(null, Direction.LEFT);
            controller.tick(null, Direction.RIGHT);
        }

        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);


        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);

        for (int i = 0; i < 25; i++) {
            controller.tick(null, Direction.NONE);
        }

        List<EntityResponse> midList = new ArrayList<EntityResponse>();

        EntityResponse midOlderPlayerInfo = new EntityResponse("0", "older_player", new Position(0,-1), false);
        EntityResponse midTimePortalInfo = new EntityResponse("2", "time_travelling_portal", new Position(2,0), false);
        EntityResponse midPlayerInfo = new EntityResponse("4", "player", new Position(4,0), true);
        
        midList.add(midOlderPlayerInfo);
        midList.add(midTimePortalInfo);
        midList.add(midPlayerInfo);

        dR = controller.getDungeonInfo(1);
        // assertEquals(midList, dR.getEntities());

        controller.tick(null, Direction.NONE);

        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse endOlderPlayerInfo = new EntityResponse("0", "older_player", new Position(1,-1), false);
        EntityResponse endTimePortalInfo = new EntityResponse("2", "time_travelling_portal", new Position(2,0), false);
        EntityResponse endPlayerInfo = new EntityResponse("4", "player", new Position(4,0), true);
        
        endList.add(endOlderPlayerInfo);
        endList.add(endTimePortalInfo);
        endList.add(endPlayerInfo);

        dR = controller.getDungeonInfo(1);
        // assertEquals(endList, dR.getEntities());

        for (int i = 0; i < 5; i++) {
            controller.tick(null, Direction.NONE);
        }
    }

    /**
     * Pick up a treasure and then travel through the portal 30 ticks later. You should be on the other side of the portal
     * and the coin should be where it was before.
     */
    @Test
    public void testOlderPlayerRetraceStepsII() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testTimeTravellingPortal", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startTreasureInfo = new EntityResponse("1", "treasure", new Position(1,0), false);
        EntityResponse startTimePortalInfo = new EntityResponse("2", "time_travelling_portal", new Position(2,0), false);

        startList.add(startPlayerInfo);
        startList.add(startTreasureInfo);
        startList.add(startTimePortalInfo);

        DungeonResponse dR = controller.getDungeonInfo(0);
        assertEquals(startList, dR.getEntities());
        
        controller.tick(null, Direction.RIGHT);
        
        for (int i = 0; i < 6; i++) {
            controller.tick(null, Direction.UP);
            controller.tick(null, Direction.DOWN);
            controller.tick(null, Direction.LEFT);
            controller.tick(null, Direction.RIGHT);
        }

        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);


        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);

        for (int i = 0; i < 25; i++) {
            controller.tick(null, Direction.NONE);
        }

        List<EntityResponse> midList = new ArrayList<EntityResponse>();

        EntityResponse midOlderPlayerInfo = new EntityResponse("0", "older_player", new Position(0,-1), false);
        EntityResponse midTimePortalInfo = new EntityResponse("2", "time_travelling_portal", new Position(2,0), false);
        EntityResponse midPlayerInfo = new EntityResponse("4", "player", new Position(4,0), true);
        
        midList.add(midOlderPlayerInfo);
        midList.add(midTimePortalInfo);
        midList.add(midPlayerInfo);

        dR = controller.getDungeonInfo(1);
        // assertEquals(midList, dR.getEntities());

        controller.tick(null, Direction.NONE);

        List<EntityResponse> endList = new ArrayList<EntityResponse>();

        EntityResponse endOlderPlayerInfo = new EntityResponse("0", "older_player", new Position(1,-1), false);
        EntityResponse endTimePortalInfo = new EntityResponse("2", "time_travelling_portal", new Position(2,0), false);
        EntityResponse endPlayerInfo = new EntityResponse("4", "player", new Position(4,0), true);
        
        endList.add(endOlderPlayerInfo);
        endList.add(endTimePortalInfo);
        endList.add(endPlayerInfo);

        dR = controller.getDungeonInfo(1);
        // assertEquals(endList, dR.getEntities());

        for (int i = 0; i < 3; i++) {
            controller.tick(null, Direction.NONE);
        }

        for (int i = 0; i < 2; i++) {
            controller.tick(null, Direction.NONE);
        }
    }
}
