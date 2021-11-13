package dungeonmania;

import java.util.List;

import dungeonmania.allEntities.Player;
import dungeonmania.util.Position;

public abstract class UsableEntity extends CollectableEntity {

	/**
	 * Amount of uses of the UsableEntity left
	 */
	int durability;

	public UsableEntity(String id, Position position, String type, int durability) {
		super(id, position, type);
		this.durability = durability;
	}

	// public UsableEntity(String id, Position position, String type) {
	// 	super(id, position, type);
	// }

	/**
	 * Use an item in battle
	 * @param dungeon	Dungeon of item
	 */
	public abstract void use(Dungeon dungeon, List<CollectableEntity> toBeRemoved);//{
	// 	// Sword sword = (Sword) item;
	// 	// if (sword.getDurability() == 0) {
	// 	// 	toBeRemoved.add(item);
	// 	// 	continue;
	// 	// }
	// 	// playerAtk += sword.getExtraDamage();
	// 	// sword.setDurability(sword.getDurability() - 1);
	// 	if (getDurability() == 0) {
	// 		return this;
	// 	}

	// 	Player player = dungeon.getPlayer();
	// 	player.setAttack(player.getAttack() + );

	// 	return null;
	// }
	
	public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
