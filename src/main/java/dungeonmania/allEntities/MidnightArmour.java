package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.*;
import dungeonmania.util.Position;

public class MidnightArmour extends Armour {

	public MidnightArmour (String id, Position position) {
		super(id, position);
		super.setType("midnight_armour");
		super.setDurability(20);
	}

	public void build (Dungeon currentDungeon) {
		List <CollectableEntity> currentInventory = currentDungeon.getInventory();
		int counterArmour = 0;
		int counterSunStone = 0;
		for (int i = 0; i < currentInventory.size(); i++) {
			CollectableEntity found = currentInventory.get(i);
			if (found.getType().equals("armour") && counterArmour < 1) {
				counterArmour++;
				currentInventory.remove(i);
				i--;
			} else if (found.getType().equals("sun_stone") && counterSunStone < 1) {
				counterSunStone++;
				currentInventory.remove(i);
				i--;
			}
		}
	}

}
