package dungeonmania.allEntities;

import dungeonmania.UtilityEntity;
import dungeonmania.util.Position;


public class HealthPotion extends UtilityEntity {

    public HealthPotion(String id, Position position) {
        super(id, position, "health_potion");
    }

    public void use(Player player) {
        player.setHealth(player.getInitialHealth());
    }

}
