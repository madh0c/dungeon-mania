package dungeonmania.allEntities;

import dungeonmania.CollectableEntity;
import dungeonmania.util.Position;


public class Key extends CollectableEntity {

    private String corresponding;

    public Key(String id, Position position, String corresponding) {
        super(id, position, "key");
        this.corresponding = corresponding;
    }

    public String getCorresponding() {
        return corresponding;
    }

}
