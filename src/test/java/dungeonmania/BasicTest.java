package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;


public class BasicTest {
    @Test
    public void testBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("portals", "Standard"));

        List<EntityResponse> entityListCheck = new ArrayList<EntityResponse>();

        entityListCheck.add(new EntityResponse("0", "player", new Position(0, 0), true));
        entityListCheck.add(new EntityResponse("1", "portal", new Position(1, 0), false));
        entityListCheck.add(new EntityResponse("2", "portal", new Position(4, 0), false));
        
        Dungeon currentDungeon = controller.getCurrentDungeon();
        List<AnimationQueue> newAnimation = new ArrayList<>();

        int currPlayerHealth = controller.getCurrentDungeon().getPlayer().getHealth();
        double doubleHP = currPlayerHealth;
        double healthFrac = doubleHP/100.0;
        String healthString = Double.toString(healthFrac);

        if (healthFrac > 0.75) {
            newAnimation.add(new AnimationQueue("PostTick", currentDungeon.getPlayer().getId(), Arrays.asList("healthbar set " + healthString, "healthbar tint 0x00ff00"), true, -1));
        } else if (healthFrac > 0.5) {
            newAnimation.add(new AnimationQueue("PostTick", currentDungeon.getPlayer().getId(), Arrays.asList("healthbar set " + healthString, "healthbar tint 0xffff00"), true, -1));
        } else if (healthFrac > 0.2) {
            newAnimation.add(new AnimationQueue("PostTick", currentDungeon.getPlayer().getId(), Arrays.asList("healthbar set " + healthString, "healthbar tint 0xffa500"), true, -1));
        } else {
            newAnimation.add(new AnimationQueue("PostTick", currentDungeon.getPlayer().getId(), Arrays.asList("healthbar set " + healthString, "healthbar tint 0xff0000"), true, -1));
        }
        

        DungeonResponse expected = new DungeonResponse (
            "0", 
            "portals", 
            entityListCheck, 
            new ArrayList<ItemResponse>(),
            new ArrayList<String>(), 
            "",
            newAnimation
        );

        assertEquals(expected, controller.getDungeonInfo(0));
        
    }
	@Test
	public void multipleTest() {
		DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("goals", "Standard"));
        
        List<EntityResponse> entityListCheck = new ArrayList<EntityResponse>();

        entityListCheck.add(new EntityResponse("0", "player", new Position(0, 0), true));
        entityListCheck.add(new EntityResponse("1", "boulder", new Position(2, 1), false));
        entityListCheck.add(new EntityResponse("2", "bomb", new Position(3, 4), false));
        entityListCheck.add(new EntityResponse("3", "mercenary", new Position(10, 5), true));
        entityListCheck.add(new EntityResponse("4", "treasure", new Position(4, 8), false));
        entityListCheck.add(new EntityResponse("5", "switch", new Position(3, 3), false));
        entityListCheck.add(new EntityResponse("6", "exit", new Position(9, 9), false));


        DungeonResponse expected = new DungeonResponse (
            "0", 
            "goals", 
            entityListCheck, 
            new ArrayList<ItemResponse>(),
            new ArrayList<String>(), 
            "(:enemies AND (:treasure OR (:exit AND (:exit OR :boulders))))"
        );

        assertEquals(expected, controller.getDungeonInfo(0));

        assertDoesNotThrow(() -> controller.newGame("portals", "Standard"));

		List<EntityResponse> entityListCheck1 = new ArrayList<EntityResponse>();

        entityListCheck1.add(new EntityResponse("0", "player", new Position(0, 0), true));
        entityListCheck1.add(new EntityResponse("1", "portal", new Position(1, 0), false));
        entityListCheck1.add(new EntityResponse("2", "portal", new Position(4, 0), false));

		DungeonResponse expected1 = new DungeonResponse (
            "1", 
            "portals", 
            entityListCheck1, 
            new ArrayList<ItemResponse>(),
            new ArrayList<String>(), 
            ""
        );

		assertEquals(expected1, controller.getDungeonInfo(1));

        assertEquals(expected, controller.getDungeonInfo(0));
		assertEquals(expected1, controller.getDungeonInfo(1));
	}

}