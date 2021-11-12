package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.allEntities.*;
import dungeonmania.response.models.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class MiscTest {
    @Test
    public void testDungeons() {
        assertTrue(DungeonManiaController.dungeons().size() > 0);
        assertTrue(DungeonManiaController.dungeons().contains("maze"));
    }
    
    @Test
    public void testDurability() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testDurability", "Hard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0,0), true);
        EntityResponse startE1 = new EntityResponse("1", "sword", new Position(1,0), false);
        EntityResponse startE2 = new EntityResponse("2", "armour", new Position(2,0), false);
        EntityResponse startE3 = new EntityResponse("3", "bow", new Position(3,0), false);
        EntityResponse startE4 = new EntityResponse("4", "shield", new Position(4,0), false);
        EntityResponse startE5 = new EntityResponse("5", "wall", new Position(5,0), false);

        startList.add(startPlayerInfo);
        startList.add(startE1);
        startList.add(startE2);
        startList.add(startE3);
        startList.add(startE4);
        startList.add(startE5);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

		// Move player away from the mercenary.
        for (int i = 0; i < 5; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        // Assert the mercenary has not moved from spawn
        List<EntityResponse> midList = new ArrayList<EntityResponse>();

        EntityResponse midPlayerInfo = new EntityResponse("0", "player", new Position(4,0), true);
        EntityResponse midE1 = new EntityResponse("5", "wall", new Position(5,0), false);

        midList.add(midPlayerInfo);
        midList.add(midE1);

        // Move player away from the mercenary.
        for (int i = 0; i < 25; i++) {
            controller.tick(null, Direction.RIGHT);
        }
        List<ItemResponse> expInvList = new ArrayList<ItemResponse>();

        ItemResponse i1 = new ItemResponse("1", "sword");
        ItemResponse i2 = new ItemResponse("2", "armour");
        ItemResponse i3 = new ItemResponse("3", "bow");
        ItemResponse i4 = new ItemResponse("4", "shield");

        expInvList.add(i1);
        expInvList.add(i2);
        expInvList.add(i3);
        expInvList.add(i4);

        DungeonResponse dRMid = controller.getDungeonInfo(0);
        assertEquals(midList, dRMid.getEntities());
        assertTrue(dRMid.getInventory().containsAll(expInvList));

        List<CollectableEntity> currentInv = controller.getCurrentDungeon().getInventory();

        Sword swordInv = (Sword) currentInv.get(0);
        assertEquals(8, swordInv.getDurability());

        Armour armourInv = (Armour) currentInv.get(1);
        assertEquals(8, armourInv.getDurability());

        Bow bowInv = (Bow) currentInv.get(2);
        assertEquals(3, bowInv.getDurability());

        Shield shieldInv = (Shield) currentInv.get(3);
        assertEquals(3, shieldInv.getDurability());

        for (int i = 0; i < 50; i++) {
            controller.tick(null, Direction.RIGHT);
        }

        DungeonResponse dREnd = controller.getDungeonInfo(0);

        assertFalse(dREnd.getInventory().containsAll(expInvList));
    }

    @Test
    public void testPreActiveSwitch() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testPreActiveSwitch", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(1,1), true);
        EntityResponse startE1 = new EntityResponse("1", "bomb", new Position(2,1), false);
        EntityResponse startE2 = new EntityResponse("2", "switch", new Position(3,1), false);
        EntityResponse startE3 = new EntityResponse("3", "boulder", new Position(3,1), false);
        EntityResponse startE4 = new EntityResponse("4", "wall", new Position(3,0), false);
        EntityResponse startE5 = new EntityResponse("5", "wall", new Position(3,2), false);

        startList.add(startPlayerInfo);
        startList.add(startE1);
        startList.add(startE2);
        startList.add(startE3);
        startList.add(startE4);
        startList.add(startE5);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

        List<Entity> currEnts = controller.getCurrentDungeon().getEntities();
        Switch swch = (Switch) currEnts.get(2);

        assertTrue(swch.getStatus());

        assertDoesNotThrow(() -> controller.tick(null, Direction.RIGHT));
        assertDoesNotThrow(() -> controller.tick("1", Direction.NONE));

        DungeonResponse dREnd = controller.getDungeonInfo(0);

        List<EntityResponse> endList = new ArrayList<>();
        EntityResponse eP = new EntityResponse("0", "player", new Position(2,1), true);
        endList.add(eP);

        assertEquals(endList, dREnd.getEntities());
    }

    /**
     * Testing the fields of entity responses.
     */
	@Test
    public void testEntityResponseFields() {
        DungeonManiaController controller = new DungeonManiaController();

        // Creating First New Game
		assertDoesNotThrow(() -> controller.newGame("portals", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList1 = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(0, 0), true);
        EntityResponse startWallInfo = new EntityResponse("1", "portal", new Position(1,0), false);
        EntityResponse startMercenaryInfo = new EntityResponse("2", "portal", new Position(4,0), false);

        startList1.add(startPlayerInfo);
        startList1.add(startWallInfo);
        startList1.add(startMercenaryInfo);

        DungeonResponse dRStart1 = controller.getDungeonInfo(0);
        assertEquals(startList1, dRStart1.getEntities());

        List<EntityResponse> currEnts = controller.getDungeonInfo(0).getEntities();
        
        EntityResponse playerResponse = currEnts.get(0);
        assertTrue(playerResponse.isInteractable());
        assertEquals("0", playerResponse.getId());
        assertEquals("player", playerResponse.getType());
        assertEquals(new Position(0,0), playerResponse.getPosition());

        EntityResponse portalResponse1 = currEnts.get(1);

        assertTrue(!portalResponse1.isInteractable());
        assertEquals("1", portalResponse1.getId());
        assertEquals("portal", portalResponse1.getType());
        assertEquals(new Position(1,0), portalResponse1.getPosition());
    }
    
    /**
	 * A player and a mercenary are spawned with a wall in between them in a collinear fashion. The player will walk 
	 * away from the wall in a straight light and the mercenary should not fail to move as it should now be able to track 
     * the player using Dijkstra's algortihm.
	 */
	@Test
    public void testWallNoLongerBlocksMercenaryMovement() {
		DungeonManiaController controller = new DungeonManiaController();
		assertDoesNotThrow(() -> controller.newGame("testWallBlocksMercenaryMovement", "Standard"));

        // Assert correct spawn positions
        List<EntityResponse> startList = new ArrayList<EntityResponse>();

        EntityResponse startPlayerInfo = new EntityResponse("0", "player", new Position(2,0), true);
        EntityResponse startWallInfo = new EntityResponse("1", "wall", new Position(1,0), false);
        EntityResponse startMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,0), true);

        startList.add(startPlayerInfo);
        startList.add(startWallInfo);
        startList.add(startMercenaryInfo);

        DungeonResponse dRStart = controller.getDungeonInfo(0);
        assertEquals(startList, dRStart.getEntities());

		// Move player away from the mercenary.
		controller.tick(null, Direction.RIGHT);

		// Assert the mercenary has moved from spawn
        EntityResponse expectedMercenaryInfo = new EntityResponse("2", "mercenary", new Position(0,1), true);

        DungeonResponse dREnd = controller.getDungeonInfo(0);
        assertNotEquals(expectedMercenaryInfo, dREnd.getEntities().get(2));
    }
}
