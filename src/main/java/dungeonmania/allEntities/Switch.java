package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Switch extends Entity {

    private boolean isActivated;

    public Switch(Position position) {
        super(position, "switch");
        this.isActivated = false;
    }

    public void setActivated(boolean status) {
        this.isActivated = status;
    }

    public boolean getStatus() {
        return this.isActivated;
    }
}
