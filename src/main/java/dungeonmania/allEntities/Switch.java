package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Switch extends Entity {

    boolean isActivated;

    public Switch(Position position) {
        super(position, "switch");
        this.isActivated = false;
    }

    public boolean getStatus() {
        return this.isActivated;
    }
}
