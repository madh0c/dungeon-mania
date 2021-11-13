package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.CollectableEntity;
import dungeonmania.Dungeon;
import dungeonmania.MovingEntity;
import dungeonmania.UsableEntity;
import dungeonmania.util.Position;


public class Sword extends UsableEntity {

    private int extraDamage = 2;
    
    public Sword(String id, Position position) {
        super(id, position, "sword", 10);
    }

    public int getExtraDamage() {
        return extraDamage;
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

		return enemyAtk;
	}

}
