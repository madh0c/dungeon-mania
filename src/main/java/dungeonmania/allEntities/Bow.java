package dungeonmania.allEntities;

import dungeonmania.CollectibleEntity;
import dungeonmania.util.Position;


public class Bow extends CollectibleEntity {

	private int durability;

    public Bow(Position position) {
        super(position, "bow");
    }
	
	public int getDurability () {
		return durability;
	}

}
