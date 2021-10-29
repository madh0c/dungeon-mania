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
        //assertEquals("enemies AND treasure", dungeonInfo.getGoals());      
        
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
            ""
        );

        assertEquals(expected, controller.getDungeonInfo(0));
        
    }
}