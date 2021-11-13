package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.CollectableEntity;
import dungeonmania.Dungeon;
import dungeonmania.UsableEntity;
import dungeonmania.util.Position;

public class Armour extends UsableEntity {
        
    public Armour(String id, Position position) {
        super(id, position, "armour", 10);
    }

	@Override
	public void use(Dungeon dungeon, List<CollectableEntity> toBeRemoved) {
		// TODO Auto-generated method stub
		return;
	}

}
