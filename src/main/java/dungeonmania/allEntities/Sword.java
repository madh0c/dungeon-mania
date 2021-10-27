package dungeonmania.allEntities;

import dungeonmania.CollectibleEntity;
import dungeonmania.util.Position;


public class Sword extends CollectibleEntity {

    private int durability;

    public Sword(Position position) {
        super(position, "sword");
        this.durability = 10;
    }

    public int getDurability() {
        return durability;
    }
}
