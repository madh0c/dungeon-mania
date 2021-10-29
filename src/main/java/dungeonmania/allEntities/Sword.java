package dungeonmania.allEntities;

import dungeonmania.CollectableEntity;
import dungeonmania.util.Position;


public class Sword extends CollectableEntity {

    private int durability;

    public Sword(Position position) {
        super(position, "sword");
        this.durability = 10;
    }

    public int getDurability() {
        return durability;
    }
}
