package dungeonmania.allEntities;

import dungeonmania.MovableEntity;
import dungeonmania.util.Position;


public class ZombieToast extends MovableEntity {

    public ZombieToast(Position position) {
        super(position, "zombie_toast");
        super.setHealth(20);
        super.setBaseAttack(10);
    }

}
