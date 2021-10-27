package dungeonmania.allEntities;

import dungeonmania.MovableEntity;
import dungeonmania.util.Position;


public class Mercenary extends MovableEntity {

	private boolean isAlly;

    public Mercenary(Position position) {
        super(position, "mercenary");
		this.isAlly = false;
    }

	public boolean getIsAlly() {
		return this.isAlly;
	}

}
