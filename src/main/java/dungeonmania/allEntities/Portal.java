package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Portal extends Entity {
    
    private String colour;

    public Portal(String id, Position position, String colour) {

        super(id, position, "portal");

        String portalType = portalType(colour);
        this.setType(portalType);
        this.colour = colour;
    }

    public String getColour() {
        return this.colour;
    }

    public String portalType(String colour) {
        String colourType = "portal_blue";
        switch(colour) {
            case("orange"):
                colourType = "portal_orange";
                break;
            case("grey"):
                colourType = "portal_grey";
                break;
        } return colourType;
    }
}
