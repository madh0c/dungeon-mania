package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.Position;

public class PeacefulFactory extends EntityFactory {
	@Override
	public Player createPlayer(String id, Position position) {
		Player player = new Player(id, position, 100, false, 8);
		return player;
	}

	@Override
	public ZombieToastSpawner createSpawner(String id, Position position) {
		return new ZombieToastSpawner(id, position, 20);
	}

	@Override
	public Spider createSpider(String id, Position position) {
		return new Spider(id, position, false);
	}

	@Override
	public ZombieToast createZombieToast(String id, Position position) {
		return new ZombieToast(id, position, false);
	}

	@Override
	public Mercenary createMercenary(String id, Position position) {
		return new Mercenary(id, position, false);
	}

	@Override
	public Assassin createAssassin(String id, Position position) {
		return new Assassin(id, position, false);
	}

	@Override
	public Hydra createHydra(String id, Position position) {
		return new Hydra(id, position, false);
	}

	@Override
	public OlderPlayer createOlderPlayer(String id, Position position) {
		return new OlderPlayer(id, position, false);
	}
}
