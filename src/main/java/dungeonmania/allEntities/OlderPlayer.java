package dungeonmania.allEntities;

import dungeonmania.Dungeon;
import dungeonmania.MovingEntity;
import dungeonmania.util.Position;

public class OlderPlayer extends MovingEntity {
    public OlderPlayer (String id, Position position, boolean enemyAttack) {
        super(id, position, "older_player", enemyAttack);
    }

    @Override
    public void move(Dungeon dungeon) {
        // TODO Auto-generated method stub
        
    }
}
