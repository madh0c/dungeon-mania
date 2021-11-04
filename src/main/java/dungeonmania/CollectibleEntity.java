package dungeonmania;

import dungeonmania.util.Position;

public abstract class CollectibleEntity extends Entity {

    public CollectibleEntity(String id, Position position, String type) {
        super(id, position, type);
    }
    
}
