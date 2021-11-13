package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.*;
import dungeonmania.util.Position;

public class MidnightArmour extends UsableEntity {

	private int extraDamage = 2;

	public MidnightArmour (String id, Position position) {
		super(id, position, "midnight_armour", 5);
	}

	public int getExtraDamage() {
        return extraDamage;
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

	@Override
	public int use(Dungeon dungeon, List<CollectableEntity> toBeRemoved, int enemyAtk) {
		if (getDurability() == 0) {
			toBeRemoved.add(this);
			return enemyAtk;
		}
		Player player = dungeon.getPlayer();
		player.setAttack(player.getAttack() + getExtraDamage());
		useDurability();
		return enemyAtk/3;
	}

}
