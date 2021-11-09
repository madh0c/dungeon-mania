package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.CollectableEntity;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;


public class Bow extends CollectableEntity {

	private int durability;
	private int extraDamage = 5;
   
    public Bow(String id, Position position) {
        super(id, position, "bow");
		this.durability = 5;
    }
	public int getExtraDamage() {
        return extraDamage;
    }
	public int getDurability () {
		return durability;
	}
	public void setDurability(int durability) {
		this.durability = durability;
	}

	public void build(Dungeon currentDungeon) {
		List <CollectableEntity> currentInventory = currentDungeon.getInventory();
		int counterArrow = 0;
		int counterWood = 0;
		for (int i = 0; i < currentInventory.size(); i++) {
			CollectableEntity found = currentInventory.get(i);
			if (found.getType().equals("arrow") && counterArrow < 3) {
				counterArrow++;
				currentInventory.remove(i);
				i--;
			} else if (found.getType().equals("wood") && counterWood < 1) {
				counterWood++;
				currentInventory.remove(i);
				i--;
			}
		}
	}

}
