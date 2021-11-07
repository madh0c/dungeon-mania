package dungeonmania.allEntities;

import dungeonmania.CollectableEntity;
import dungeonmania.util.Position;


public class Shield extends CollectableEntity {
    private double damageFactorReduction = 0.2;
    private int durability = 5;
    public Shield(String id, Position position) {
        super(id, position, "shield");
    }

    public double getDamageFactorReduction() {
        return damageFactorReduction;
    }

    public void setDamageFactorReduction(double damageFactorReduction) {
        this.damageFactorReduction = damageFactorReduction;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
    

}
