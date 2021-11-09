package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.CollectableEntity;
import dungeonmania.Dungeon;
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
    
	public void build (Dungeon currentDungeon) {
		List <CollectableEntity> currentInventory = currentDungeon.getInventory();
		int counterTreasure = 0;
		int counterKey = 0;
		int counterWood = 0;
		for (int i = 0; i < currentInventory.size(); i++) {
			CollectableEntity found = currentInventory.get(i);
			if (found.getType().equals("wood") && counterWood < 2) {
				counterWood++;
				currentInventory.remove(i);
				i--;
			} else if ((found.getType().equals("treasure") && counterTreasure < 1) || (found.getType().equals("key") && counterKey < 1)) {
				counterTreasure++;
				counterKey++;
				currentInventory.remove(i);
				i--;
			}
		}
	}
}
