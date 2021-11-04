package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovableEntity;
import dungeonmania.util.Position;


public class ZombieToastSpawner extends Entity {

    public ZombieToastSpawner(String id, Position position) {
        super(id, position, "zombie_toast_spawner");
    }

    public void spawnZombie(Dungeon currentDungeon) {

		List<Position> spawnOrder = this.getPosition().getCardinallyAdjPositions();

		for (Position spawnPoint : spawnOrder) {
			List<Entity> conflictEntities = currentDungeon.getEntitiesOnCell(spawnPoint);
			boolean canSpawn = true;
			for (Entity conflictE : conflictEntities) {
				if (conflictE.getType().equals("boulder") || conflictE.getType().equals("wall") || conflictE instanceof MovableEntity) {
					canSpawn = false;

				}
			}
			if (canSpawn) {
				int newId = currentDungeon.getHistoricalEntCount();				
				ZombieToast zombie = new ZombieToast(String.valueOf(newId), spawnPoint);
				currentDungeon.setHistoricalEntCount(newId + 1);
				currentDungeon.addEntity(zombie);
                break;
			}
		}
	}
}
