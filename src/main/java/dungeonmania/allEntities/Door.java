package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Door extends Entity {

    private boolean isOpen;

    public Door(String id, Position position) {
        super(id, position, "door");
        this.isOpen = false;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void unlock() {
        this.isOpen = true;
        this.setType("door_unlocked");
    }

}
