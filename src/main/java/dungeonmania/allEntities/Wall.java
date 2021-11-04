package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Wall extends Entity {

    public Wall(String id, Position position) {
        super(id, position, "wall");
    }

}
