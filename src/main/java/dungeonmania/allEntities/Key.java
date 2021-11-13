package dungeonmania.allEntities;

import dungeonmania.CollectableEntity;
import dungeonmania.util.Position;


public class Key extends CollectableEntity {

    private int key;

    public Key(String id, Position position, int key) {
        super(id, position, "key");
        this.key = key;
    }

    public int getKey() {
        return key;
    }

}
