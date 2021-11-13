package dungeonmania.allEntities;

import dungeonmania.*;
import dungeonmania.util.Position;

public class Anduril extends CollectableEntity {
	private int durability;
    private final int dmgMultiplier = 3;

	public Anduril (String id, Position position) {
		super(id, position, "anduril");
		this.durability = 15;
	}

	public int getDmgMultiplier() {
		return dmgMultiplier;
	}

	public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

}
