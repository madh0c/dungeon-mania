package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Door extends Entity {

    private boolean isOpen;

    public Door(Position position) {
        super(position, "door");
        this.isOpen = false;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

}
