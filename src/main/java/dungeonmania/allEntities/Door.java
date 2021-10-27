package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Door extends Entity {

    boolean isOpen;

    public Door(Position position) {
        super(position, "door");
        this.isOpen = false;
    }

}
