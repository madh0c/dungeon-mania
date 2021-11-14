package dungeonmania.allEntities;

import dungeonmania.CollectableEntity;
import dungeonmania.util.Position;


public class Key extends CollectableEntity {

    private int key;

    public Key(String id, Position position, int key) {
        super(id, position, "key");
        this.key = key;

        if (key == 1) {
            super.setType("key_1");
        } else if (key == 2) {
            super.setType("key_2");
        }
    }

    public int getKey() {
        return key;
    }

}
