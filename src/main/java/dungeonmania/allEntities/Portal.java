package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Portal extends Entity {
    
    private final String colour;

    public Portal(String id, Position position, String colour) {

        super(id, position, "portal");
        this.colour = colour;
    }

    public String getColour() {
        return this.colour;
    }
}
