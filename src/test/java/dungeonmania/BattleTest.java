package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.allEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BattleTest {

    @Test
    public void testBasicBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("testBasicBattle.json", "Standard"));

        controller.tick(null, Direction.LEFT);

        Dungeon dungeon = controller.getDungeon(0);
        Position pos = dungeon.getPlayerPosition();
        assertEquals(95, dungeon.getPlayer().getHealth());
        assertFalse(dungeon.entityExists("spider", pos));

        // Next tick
        controller.tick(null, Direction.LEFT);

        assertEquals(90, dungeon.getPlayer().getHealth());
        assertFalse(dungeon.entityExists("spider", pos));

        // Next tick
        controller.tick(null, Direction.LEFT);

        assertEquals(85, dungeon.getPlayer().getHealth());
        assertFalse(dungeon.entityExists("spider", pos));
    }
}
