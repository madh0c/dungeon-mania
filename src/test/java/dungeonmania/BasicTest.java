package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;


public class BasicTest {
    @Test
    public void testBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("portals.json", "Standard"));
        DungeonResponse dungeon = new DungeonResponse("0", "portals.json", null, null, null,"");
        assertTrue(dungeon.equals(controller.getDungeonInfo(0)));
        /**
         * TODO: instead of using controller.getDungeonInfo(), 
         * should get the dungeon instance itself (Dungeon dungeon = controller.getDungeon()), then do dungeon.getName
         * dungeon.getAllEntities(), and make sure each field is correct
         */
        
        
    }
}