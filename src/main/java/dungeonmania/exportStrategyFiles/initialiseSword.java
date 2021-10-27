package dungeonmania.exportStrategyFiles;

import dungeonmania.exportStrategy;
import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;
import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.allEntities.Sword;



public class initialiseSword implements exportStrategy {
    
    @Override
    public void initialiseEntity(Position position, Dungeon dungeon) {
        Sword newSword = new Sword(position);
        // dungeon.addEntity(newSword);
    }
}
