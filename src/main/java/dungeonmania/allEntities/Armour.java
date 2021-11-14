package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.CollectableEntity;
import dungeonmania.Dungeon;
import dungeonmania.DurableEntity;
import dungeonmania.util.Position;

public class Armour extends DurableEntity {
        
    public Armour(String id, Position position) {
        super(id, position, "armour", 10);
    }

	@Override
	public int use(Dungeon dungeon, List<CollectableEntity> toBeRemoved, int enemyAtk) {
		if (getDurability() == 0) {
			toBeRemoved.add(this);
			return enemyAtk;
		}
		useDurability();
		return enemyAtk/2;
	}

}
