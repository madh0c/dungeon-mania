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
	 * Use an item in battle<ul>
	 * <li> Adds attack damage to player for certain items
	 * <li> Reduces attack damage of enemy for certain items
	 * </ul>
	 * @param dungeon	Dungeon of item
	 */
	public abstract int use(Dungeon dungeon, List<CollectableEntity> toBeRemoved, int enemyAtk);//{
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

	/**
	 * Use the UsableEntity once, i.e. reduce the durability by one
	 */
	public void useDurability() {
		this.durability--;
	}
}
