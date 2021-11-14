package dungeonmania.allEntities;

import dungeonmania.UtilityEntity;
import dungeonmania.util.Position;


public class InvisibilityPotion extends UtilityEntity {

    public InvisibilityPotion(String id, Position position) {
        super(id, position, "invisibility_potion");
    }

    public void use(Player player) {
        player.setVisibility(false);
    }

}
