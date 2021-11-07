package dungeonmania;

// import java.util.Map;

public interface GoalNode {
	
	public Boolean evaluate(Dungeon dungeon);
	public String remainingString();
	//public Boolean isCompleted;
}

