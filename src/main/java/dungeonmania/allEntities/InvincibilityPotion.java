package dungeonmania.allEntities;

import dungeonmania.UtilityEntity;
import dungeonmania.util.Position;


public class InvincibilityPotion extends UtilityEntity {

    public InvincibilityPotion(String id, Position position) {
        super(id, position, "invincibility_potion");
    }

    public void use(Player player) {
        player.setInvincibleTickDuration(player.getInvincibleAmount());

    }
}
