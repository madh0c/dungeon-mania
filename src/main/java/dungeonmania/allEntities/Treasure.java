package dungeonmania.allEntities;

import dungeonmania.CollectibleEntity;
import dungeonmania.util.Position;


public class Treasure extends CollectibleEntity {

    public Treasure(String id, Position position) {
        super(id, position, "treasure");
    }

}
