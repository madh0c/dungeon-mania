package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovingEntity;
import dungeonmania.util.Position;


public class ZombieToastSpawner extends Entity {

	private final int tickrate;

    public ZombieToastSpawner(String id, Position position, int tickrate) {
        super(id, position, "zombie_toast_spawner");
		this.tickrate = tickrate;
    }

	/**
	 * Spawns in a Zombie in a cardinally-adjacent square to this spawner periodically<ul>
	 * <li>Peaceful Mode - Every 20 ticks
	 * <li>Standard Mode - Every 20 ticks
	 * <li>Hard Mode - Every 15 ticks</ul>
	 * @param currentDungeon	Current dungeon of spawner
	 */
    public void spawnZombie(Dungeon currentDungeon) {

		if (currentDungeon.getTickNumber() % tickrate != 0 || currentDungeon.getTickNumber() <= 1) {
			return;
		}

		List<Position> spawnOrder = this.getPosition().getCardinallyAdjPositions();

		for (Position spawnPoint : spawnOrder) {
			int newId = currentDungeon.getHistoricalEntCount();
			ZombieToast zombie = (ZombieToast)currentDungeon.getFactory().createEntity(String.valueOf(newId), "zombie_toast", spawnPoint);
			boolean canSpawn = true;
			List<Entity> conflictEntities = currentDungeon.getEntitiesOnCell(spawnPoint);
			for (Entity entity : conflictEntities) {
				if (!zombie.collide(entity, currentDungeon)) {
					canSpawn = false;
				}
			}

			if (canSpawn) {
				currentDungeon.addEntity(zombie);
			}


			// boolean canSpawn = true;
			// for (Entity conflictE : conflictEntities) {
			// 	if (conflictE.getType().equals("boulder") || conflictE.getType().equals("wall") || conflictE instanceof MovingEntity) {
			// 		canSpawn = false;

			// 	}
			// }
			// if (canSpawn) {
			// 	int newId = currentDungeon.getHistoricalEntCount();				
			// 	Entity zombie = currentDungeon.getFactory().createEntity(String.valueOf(newId), "zombie_toast", spawnPoint);
			// 	currentDungeon.addEntity(zombie);
            //     break;
			// }

		}
	}
}