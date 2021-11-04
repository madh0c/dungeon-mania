package dungeonmania.allEntities;

import dungeonmania.CollectibleEntity;
import dungeonmania.util.Position;


public class Sword extends CollectibleEntity {

    private int durability;
    private int extraDamage = 5;

    
    public Sword(String id, Position position) {
        super(id, position, "sword");
        this.durability = 10;
    }

    public int getExtraDamage() {
        return extraDamage;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
