package dungeonmania.allEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dungeonmania.Battle;
import dungeonmania.CollectibleEntity;
import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovableEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class Mercenary extends MovableEntity {

	private boolean isAlly;
	private Direction currentDir;

    public Mercenary(Position position) {
        super(position, "mercenary");
		this.isAlly = false;
		super.setHealth(30);
		super.setBaseAttack(5);
    }

	public boolean getIsAlly() {
		return this.isAlly;
	}

	public Direction getCurrentDir() {
		return currentDir;
	}

	public void setCurrentDir(Direction currentDir) {
		this.currentDir = currentDir;
	}


	public boolean collide(Entity entity, Dungeon dungeon) {
		// If empty space
		if (entity == null) {
			return true;
		}
		
		if (entity instanceof Wall) {
			return false;
		} else if (entity instanceof BombStatic) {
			return false;
		} else if (entity instanceof ZombieToastSpawner) {
			return false;
		} else if (entity instanceof Door) {
			return false;
		} else if (entity instanceof Boulder) {
			return false;		
		} else if (entity instanceof Portal) {
			Portal portal1 = (Portal) entity;
			for (Map.Entry<String, Entity> entry : dungeon.getEntities().entrySet()) {
				Entity currentEntity = entry.getValue();
				if (!currentEntity.getId().equals(portal1.getId())) {
					if (!(currentEntity instanceof Portal)) {
						continue;
					}
					Portal portal2 = (Portal) currentEntity;
					if (portal1.getColour().equals((portal2).getColour())) {
						// Find position of p2
						// Move in direciton of currDir
						Entity nextTo = dungeon.getEntity(portal2.getPosition().translateBy(currentDir));
						return collide(nextTo, dungeon);
					}
				}
	
			}
		if (entity instanceof CollectibleEntity) {
			return true;
		}
			return false;
		}
		
		if (entity instanceof Player) {
			// If not ally, battle
			if (!isAlly) {
				Battle.battle(this, dungeon);;
				return true;
			}
			// If ally
			return true;
		}

		// Have to add MovableEntity
		if (entity instanceof MovableEntity) {


			return true;
		}
		
		return true;
	}

	public boolean bribeable(Dungeon dungeon) {
		// If empty inv, can't bribe
		if ((dungeon.getInventory() == null) || (dungeon.getInventory().isEmpty())) {
			return false;
		}

		// Check number of gold in player's inventory
		int numberOfGold = 0;
		List<Integer> goldPos = new ArrayList<>();
		for (CollectibleEntity ent : dungeon.getInventory()) {
			if (ent instanceof Treasure) {
				goldPos.add(Integer.parseInt(ent.getId()));
				numberOfGold++;
			}
		}

		// Bribeable
		if (numberOfGold > 0) {
			for (int i = 0; i < numberOfGold; i++) {
				dungeon.getInventory().remove(goldPos.get(i));
			}
			isAlly = true;
			return true;
		}


		return false;
	}
	
	// Can be in a player
	@Override
	public boolean collide(Entity entity) {
		// If empty space
		if (entity == null) {
			return true;
		}
		
		if (entity instanceof Boulder) {
			return false;
		} else if (entity instanceof Wall) {
			return false;
		} else if (entity instanceof ZombieToastSpawner) {
			return false;
		} else if (entity instanceof Door) {
			return false;
		} else if (entity instanceof Exit) {
			return false;
		} else if (entity instanceof Player) {
			return true;
		} else if (entity instanceof MovableEntity) {
			return false;
		}
		
		return true;
	}
}
