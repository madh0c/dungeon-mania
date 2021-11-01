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

import dungeonmania.allEntities.Player;
import dungeonmania.allEntities.ZombieToast;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class GamemodesTest {
    @Test
    public void testBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testGamemode.json", "Standard"));

        //move to right, then use potion
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));
        assertDoesNotThrow(() ->controller.tick("invincibility_potion", Direction.NONE));
        Player player = controller.getDungeon(0).getPlayer();

        assertEquals(7, player.getInvincibleTickDuration());

        // check health 
        assertEquals(100, player.getHealth()); 

        // check invincibility potion runs out
        for (int i = 0; i < 8; i++) {
            assertDoesNotThrow(() ->controller.tick(null, Direction.NONE));
        }
        assertEquals(0, player.getInvincibleTickDuration());


        for (int i = 0; i < 12; i++) {
            assertDoesNotThrow(() ->controller.tick(null, Direction.NONE));
        }
                
        assertTrue(controller.getDungeon(0).getEntity(new Position(2, 1)) instanceof ZombieToast);

        
        
    }
}