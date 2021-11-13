package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.CollectableEntity;
import dungeonmania.Dungeon;
import dungeonmania.UsableEntity;
import dungeonmania.util.Position;


public class Bow extends UsableEntity {

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
	public void use(Dungeon dungeon, List<CollectableEntity> toBeRemoved) {
		// TODO Auto-generated method stub
		return;
	}

}
