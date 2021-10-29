package dungeonmania.allEntities;

import dungeonmania.CollectableEntity;
import dungeonmania.util.Position;

public class Armour extends CollectableEntity {
    
    private int durability;
    
    public Armour(Position position) {
        super(position, "armour");
        this.durability = 10;
    }

    public int getDurability() {
        return durability;
    }
}
