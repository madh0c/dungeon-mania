package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;


public class BasicTest {
    @Test
    public void testBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("portals.json", "Standard"));

        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);

        List<EntityResponse> entitiesInfo = dungeonInfo.getEntities(); 

        assertEquals("0", dungeonInfo.getDungeonId()); 
        assertEquals("portals.json", dungeonInfo.getDungeonName()); 

        assertEquals(entitiesInfo.get(0).getId(), "0");
        assertEquals(entitiesInfo.get(0).getType(), "player");
        assertEquals(entitiesInfo.get(0).getPosition(), new Position(0, 0));
        assertEquals(entitiesInfo.get(0).isInteractable(), true);

        assertEquals(entitiesInfo.get(1).getId(), "1");
        assertEquals(entitiesInfo.get(1).getType(), "portal");
        assertEquals(entitiesInfo.get(1).getPosition(), new Position(1, 0));
        assertEquals(entitiesInfo.get(1).isInteractable(), false);
        
        assertEquals(entitiesInfo.get(2).getId(), "2");
        assertEquals(entitiesInfo.get(2).getType(), "portal");
        assertEquals(entitiesInfo.get(2).getPosition(), new Position(4, 0));
        assertEquals(entitiesInfo.get(2).isInteractable(), false);

        assertEquals(new ArrayList<ItemResponse>(), dungeonInfo.getInventory());
        assertEquals(new ArrayList<String>(), dungeonInfo.getBuildables());
        assertEquals("enemies AND treasure", dungeonInfo.getGoals());      
        
        List<EntityResponse> entityListCheck = new ArrayList<EntityResponse>();

        entityListCheck.add(new EntityResponse("0", "player", new Position(0, 0), true));
        entityListCheck.add(new EntityResponse("1", "portal", new Position(1, 0), false));
        entityListCheck.add(new EntityResponse("2", "portal", new Position(4, 0), false));
        
        DungeonResponse expected = new DungeonResponse (
            "0", 
            "portals.json", 
            entityListCheck, 
            new ArrayList<ItemResponse>(),
            new ArrayList<String>(), 
            "enemies AND treasure"
        );

        assertEquals(expected, controller.getDungeonInfo(0));
        
    }
	@Test
	public void multipleTest() {
		DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("goals.json", "Standard"));
		assertDoesNotThrow(() -> controller.newGame("portals.json", "Standard"));

        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
		DungeonResponse dungeonInfo1 = controller.getDungeonInfo(1);

        List<EntityResponse> entitiesInfo = dungeonInfo.getEntities(); 

        assertEquals("0", dungeonInfo.getDungeonId()); 
        assertEquals("goals.json", dungeonInfo.getDungeonName()); 
		assertEquals("1", dungeonInfo1.getDungeonId());
		assertEquals("portals.json", dungeonInfo1.getDungeonName()); 

        assertEquals(entitiesInfo.get(0).getId(), "0");
        assertEquals(entitiesInfo.get(0).getType(), "player");
        assertEquals(entitiesInfo.get(0).getPosition(), new Position(0, 0));
        assertEquals(entitiesInfo.get(0).isInteractable(), true);

        assertEquals(entitiesInfo.get(1).getId(), "1");
        assertEquals(entitiesInfo.get(1).getType(), "boulder");
        assertEquals(entitiesInfo.get(1).getPosition(), new Position(2, 1));
        assertEquals(entitiesInfo.get(1).isInteractable(), true);
        
        assertEquals(entitiesInfo.get(2).getId(), "2");
        assertEquals(entitiesInfo.get(2).getType(), "bomb");
        assertEquals(entitiesInfo.get(2).getPosition(), new Position(3, 4));
        assertEquals(entitiesInfo.get(2).isInteractable(), true);

        assertEquals(new ArrayList<ItemResponse>(), dungeonInfo.getInventory());
        assertEquals(new ArrayList<String>(), dungeonInfo.getBuildables());
        assertEquals("enemies AND (treasure OR (exit AND (bomb OR boulder)))", dungeonInfo.getGoals());      
        
        List<EntityResponse> entityListCheck = new ArrayList<EntityResponse>();

        entityListCheck.add(new EntityResponse("0", "player", new Position(0, 0), true));
        entityListCheck.add(new EntityResponse("1", "boulder", new Position(2, 1), true));
        entityListCheck.add(new EntityResponse("2", "bomb", new Position(3, 4), true));


		List<EntityResponse> entityListCheck1 = new ArrayList<EntityResponse>();

        entityListCheck1.add(new EntityResponse("0", "player", new Position(0, 0), true));
        entityListCheck1.add(new EntityResponse("1", "portal", new Position(1, 0), false));
        entityListCheck1.add(new EntityResponse("2", "portal", new Position(4, 0), false));

        DungeonResponse expected = new DungeonResponse (
            "0", 
            "goals.json", 
            entityListCheck, 
            new ArrayList<ItemResponse>(),
            new ArrayList<String>(), 
            "enemies AND (treasure OR (exit AND (bomb OR boulder)))"
        );

		DungeonResponse expected1 = new DungeonResponse (
            "1", 
            "portals.json", 
            entityListCheck1, 
            new ArrayList<ItemResponse>(),
            new ArrayList<String>(), 
            "enemies AND treasure"
        );

        assertEquals(expected, controller.getDungeonInfo(0));
		assertEquals(expected1, controller.getDungeonInfo(1));
	}

}