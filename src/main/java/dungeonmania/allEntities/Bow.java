package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.*;
import dungeonmania.util.Position;


public class Bow extends DurableEntity {

	private int extraDamage = 3;
   
    public Bow(String id, Position position) {
        super(id, position, "bow", 5);
    }
	public int getExtraDamage() {
        return extraDamage;
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
	@Override
	public int use(Dungeon dungeon, List<CollectableEntity> toBeRemoved, int enemyAtk) {
		if (getDurability() == 0) {
			toBeRemoved.add(this);
			return enemyAtk;
		}
		Player player = dungeon.getPlayer();
		player.setAttack(player.getAttack() + getExtraDamage()*2);
		useDurability();
		return enemyAtk;
	}
}
