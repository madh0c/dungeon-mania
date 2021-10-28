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
        assertEquals(new DungeonResponse("0", "portals.json", null, null, null, null), controller.getDungeonInfo(0));
    }
}