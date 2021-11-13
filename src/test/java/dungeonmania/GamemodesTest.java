package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.allEntities.Player;
import dungeonmania.allEntities.ZombieToast;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class GamemodesTest {
    @Test
    public void testGamemodeBasic() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testGamemode", "Peaceful"));

        //move to right, then use potion
        assertDoesNotThrow(() ->controller.tick(null, Direction.RIGHT));

        DungeonResponse dungeonInfo = controller.getDungeonInfo(0);
        assertEquals(Arrays.asList(new ItemResponse("1", "invincibility_potion")), dungeonInfo.getInventory());
        
        assertDoesNotThrow(() ->controller.tick("1", Direction.NONE));
        Player player = controller.getDungeon(0).getPlayer();

        assertEquals(7, player.getInvincibleTickDuration());

        // check health 
        assertEquals(100, player.getHealth()); 

        // check invincibility potion runs out
        for (int i = 0; i < 8; i++) {
            assertDoesNotThrow(() ->controller.tick(null, Direction.NONE));
        }
        assertEquals(0, player.getInvincibleTickDuration());


        for (int i = 0; i < 9; i++) {
            assertDoesNotThrow(() ->controller.tick(null, Direction.NONE));
        }

		controller.tick(null, Direction.RIGHT);
		Position pos = new Position(2,1);
                
        assertTrue(controller.getDungeon(0).getEntity(pos) instanceof ZombieToast);

		controller.tick(null, Direction.DOWN);

		assertEquals(100, player.getHealth());
		MovingEntity zom = (MovingEntity)controller.getDungeon(0).getEntity("4");
		assertEquals(20, zom.getHealth());
        
    }

	@Test
	public void testZombieHard() {
		DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testGamemode", "Hard"));

		Position pos = new Position(2, 1);
        for (int i = 0; i < 15; i++) {
			assertFalse(controller.getDungeon(0).entityExists(pos));
            assertDoesNotThrow(() ->controller.tick(null, Direction.NONE));
        }

		// Check zomebie spawns now
        assertTrue(controller.getDungeon(0).getEntity(pos) instanceof ZombieToast);
		// assertTrue(controller.getDungeon(0).entityExists(pos));
	}

	@Test
	public void testHardHealth() {
		DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testGamemode", "Hard"));

        Player currP = (Player) controller.getDungeon(0).getPlayer();
		assertEquals(60, currP.getHealth());
	}
}