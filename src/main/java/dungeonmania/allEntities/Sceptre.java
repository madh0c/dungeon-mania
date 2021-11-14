package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.*;
import dungeonmania.util.Position;

public class Sceptre extends CollectableEntity {

	public Sceptre (String id, Position position) {
		super(id, position, "sceptre");
	}

	public void build (Dungeon currentDungeon) {
		List <CollectableEntity> currentInventory = currentDungeon.getInventory();
		int counterWood = 0;
		int counterArrow = 0;
		int counterKey = 0;
		int counterTreasure = 0;
		int counterSunStone = 0;
		for (int i = 0; i < currentInventory.size(); i++) {
			CollectableEntity found = currentInventory.get(i);
			if (found.getType().equals("wood") && counterWood < 1 && counterArrow < 2) {
				counterWood++;
				currentInventory.remove(i);
				i--;
			} else if (found.getType().equals("arrow") && counterArrow < 2 && counterWood < 1) {
				counterArrow++;
				currentInventory.remove(i);
				i--;
			} else if ((found.getType().equals("treasure") && counterTreasure < 1) || (found.getType().contains("key") && counterKey < 1)) {
				counterTreasure++;
				counterKey++;
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
