package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.CollectableEntity;
import dungeonmania.Dungeon;
import dungeonmania.DurableEntity;
import dungeonmania.util.Position;


public class Shield extends DurableEntity {

	public Shield(String id, Position position) {
        super(id, position, "shield", 5);
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
			} else if ((found.getType().equals("treasure") && counterTreasure < 1) || (found.getType().contains("key") && counterKey < 1)) {
				counterTreasure++;
				counterKey++;
				currentInventory.remove(i);
				i--;
			}
		}
	}

	@Override
	public int use(Dungeon dungeon, List<CollectableEntity> toBeRemoved, int enemyAtk) {
		if (getDurability() == 0) {
			toBeRemoved.add(this);
			return enemyAtk;
		}
		useDurability();
		return enemyAtk/5;
	}
}
