package dungeonmania;

import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entity {

    public CollectableEntity (String id, Position position, String type) {
        super(id, position, type);
    }

}
