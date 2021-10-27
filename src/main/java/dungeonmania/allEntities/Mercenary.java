package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Mercenary extends Entity {

	boolean isAlly;

    public Mercenary(Position position) {
        super(position, "mercenary");
		this.isAlly = false;
    }

}
