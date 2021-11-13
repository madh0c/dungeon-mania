package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Door extends Entity {

    private boolean isOpen;
    private String corresponding;

    public Door(String id, Position position, String corresponding) {
        super(id, position, "door");
        this.isOpen = false;
        this.corresponding = corresponding;
    }

    public String getCorresponding() {
        return corresponding;
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
