package dungeonmania.allEntities;

import dungeonmania.MovingEntity;
import dungeonmania.util.Position;


public class ZombieToast extends MovingEntity {

    public ZombieToast(String id, Position position) {
        super(id, position, "zombie_toast");
        super.setHealth(20);
        super.setBaseAttack(10);
    }

}
