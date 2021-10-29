package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        entitiesInfo.contains(new EntityResponse("0", "player", new Position(0, 0), true));
        entitiesInfo.contains(new EntityResponse("0", "portal", new Position(1, 0), true));
        entitiesInfo.contains(new EntityResponse("0", "portal", new Position(4, 0), true));
        assertEquals(new ArrayList<ItemResponse>(), dungeonInfo.getInventory());
        assertEquals(new ArrayList<String>(), dungeonInfo.getBuildables());
        assertEquals("", dungeonInfo.getGoals());        
        
    }
}