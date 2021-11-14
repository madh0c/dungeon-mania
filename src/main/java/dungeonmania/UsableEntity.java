package dungeonmania;

import java.util.List;

import dungeonmania.util.Position;

public abstract class UsableEntity extends CollectableEntity {

	/* Amount of uses of the UsableEntity left */
	int durability;

	public UsableEntity(String id, Position position, String type, int durability) {
		super(id, position, type);
		this.durability = durability;
	}
	
	/**
	 * Use an item in battle<ul>
	 * <li> Adds attack damage to player for certain items
	 * <li> Reduces attack damage of enemy for certain items
	 * </ul>
	 * @param dungeon	Dungeon of item
	 */
	public abstract int use(Dungeon dungeon, List<CollectableEntity> toBeRemoved, int enemyAtk);
	
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
