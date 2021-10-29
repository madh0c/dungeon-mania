package dungeonmania;

import java.util.Map;

public class GoalGoal implements GoalNode {
	private Object goal;

	public GoalGoal(Object goal) {
		this.goal = goal;
	}
	@Override
	public String evaluate() {
		return (String)goal;
	}
	
}
