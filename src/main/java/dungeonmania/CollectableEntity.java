package dungeonmania;

import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entity {

    /**
     * Constructor for Collectable Entity.
     * @param id
     * @param position
     * @param type
     */
    public CollectableEntity (String id, Position position, String type) {
        super(id, position, type);
    }

}
