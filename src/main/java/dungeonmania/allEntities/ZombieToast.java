package dungeonmania.allEntities;

import dungeonmania.MovableEntity;
import dungeonmania.util.Position;


public class ZombieToast extends MovableEntity {

    public ZombieToast(String id, Position position) {
        super(id, position, "zombie_toast");
        super.setHealth(20);
        super.setBaseAttack(10);
    }

}
