package dungeonmania.exportStrategyFiles;

import dungeonmania.allEntities.Portal;
import dungeonmania.util.Position;
import dungeonmania.Dungeon;


public class initialisePortal {
    
    public initialisePortal(Position position, String colour, Dungeon dungeon) {

        Portal newPortal = new Portal(position, colour);
        dungeon.addEntity(newPortal);

        
    }
    
}
