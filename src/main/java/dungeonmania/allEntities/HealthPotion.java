package dungeonmania.allEntities;

import dungeonmania.CollectableEntity;
import dungeonmania.util.Position;


public class HealthPotion extends CollectableEntity {

    public HealthPotion(String id, Position position) {
        super(id, position, "health_potion");
    }

}
