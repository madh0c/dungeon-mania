package dungeonmania.allEntities;

import dungeonmania.CollectibleEntity;
import dungeonmania.util.Position;

public class Armour extends CollectibleEntity {
    
    private int durability;
    
    public Armour(String id, Position position) {
        super(id, position, "armour");
        this.durability = 10;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
