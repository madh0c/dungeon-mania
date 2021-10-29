package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Portal extends Entity {

    private String colour;

    public Portal(Position position, String colour) {
        super(position, "portal");
        this.colour = colour;
    }

    public String getColour() {
        return this.colour;
    }

}
