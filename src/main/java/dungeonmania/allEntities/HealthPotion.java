package dungeonmania.allEntities;

import dungeonmania.CollectibleEntity;
import dungeonmania.util.Position;


public class HealthPotion extends CollectibleEntity {

    public HealthPotion(String id, Position position) {
        super(id, position, "health_potion");
    }

}
