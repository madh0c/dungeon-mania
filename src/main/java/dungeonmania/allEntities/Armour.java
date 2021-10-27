package dungeonmania.allEntities;

import dungeonmania.CollectibleEntity;
import dungeonmania.util.Position;

public class Armour extends CollectibleEntity {
    
    private int durability;
    
    public Armour(Position position) {
        super(position, "armour");
        this.durability = 10;
    }

    public int getDurability() {
        return durability;
    }
}
