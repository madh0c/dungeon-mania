package dungeonmania;

public class HardMode implements Mode {

	@Override
	public int getZombieTick() {
		return 15;
	}

	@Override
	public boolean enemyAttack() {
		return true;
	}

	@Override
	public int getHealth() {
		return 60;
	}

	@Override
	public int getInvincDuration() {
		return 0;
	}
	
}
