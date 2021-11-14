package dungeonmania;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoalAnd implements GoalNode{
	private List<GoalNode> subGoals = new ArrayList<>();
	private String operator;
	private Boolean hasCompleted;
	public GoalAnd(String operator) {
		this.operator = operator;
		hasCompleted = false;
	}

	@Override
	public Boolean evaluate(Dungeon dungeon) {
		if (subGoals.stream().allMatch(x-> x.evaluate(dungeon).equals(true))) {
			hasCompleted = true;
		} else {
			hasCompleted = false;
		}
		return hasCompleted;
	}

	@Override
	public String remainingString() {
		if (hasCompleted) {
			return "";
		} 
		String notDoneGoals = subGoals.stream().map(GoalNode :: remainingString) .filter(x -> !x.equals("")).collect(Collectors.joining(" "  + operator + " "));
		if (notDoneGoals.contains(operator)) {
			return "(" + notDoneGoals + ")";
		} else {
			return notDoneGoals;
		}
	}
	public void addAndList(GoalNode goal) {
		subGoals.add(goal);
	}

	public List<GoalNode> getList() {
		return subGoals;
	}

}
