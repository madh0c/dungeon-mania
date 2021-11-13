package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Door extends Entity {

    private boolean isOpen;
    private int key;

    public Door(String id, Position position, int key) {
        super(id, position, "door");
        this.isOpen = false;
        this.key = key;
    }

    public int getKey() {
        return key;
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
