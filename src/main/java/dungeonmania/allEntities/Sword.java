package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Sword extends Entity {

    int durability;

    public Sword(Position position) {
        super(position, "sword");
        this.durability = 10;
    }

    public int getDurability() {
        return durability;
    }
}
