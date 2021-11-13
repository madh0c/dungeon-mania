package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.CollectableEntity;
import dungeonmania.Dungeon;
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
	public void use(Dungeon dungeon, List<CollectableEntity> toBeRemoved) {
		// Sword sword = (Sword) item;
		// if (sword.getDurability() == 0) {
		// 	toBeRemoved.add(item);
		// 	continue;
		// }
		// playerAtk += sword.getExtraDamage();
		// sword.setDurability(sword.getDurability() - 1);	

		if (getDurability() == 0) {
			toBeRemoved.add(this);
			return;
		}
		Player player = dungeon.getPlayer();
		player.setAttack(player.getAttack() + getExtraDamage());
		setDurability(getDurability() - 1);
		
	}

}
