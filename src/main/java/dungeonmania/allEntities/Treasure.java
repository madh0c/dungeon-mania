package dungeonmania.allEntities;

import dungeonmania.CollectableEntity;
import dungeonmania.util.Position;


public class Treasure extends CollectableEntity {

    public Treasure(String id, Position position) {
        super(id, position, "treasure");
    }

}
