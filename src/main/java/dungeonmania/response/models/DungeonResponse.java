package dungeonmania.response.models;

import java.util.ArrayList;
import java.util.List;

public final class DungeonResponse {
    private final String dungeonId;
    private final String dungeonName;
    private final List<EntityResponse> entities;
    private final List<ItemResponse> inventory;
    private final List<String> buildables;
    private final String goals;
    private final List<AnimationQueue> animations;

    public DungeonResponse(String dungeonId, String dungeonName, List<EntityResponse> entities,
            List<ItemResponse> inventory, List<String> buildables, String goals) {
        this(dungeonId, dungeonName, entities, inventory, buildables, goals, new ArrayList<>());
    }

    public DungeonResponse(String dungeonId, String dungeonName, List<EntityResponse> entities,
            List<ItemResponse> inventory, List<String> buildables, String goals,
            List<AnimationQueue> animations) {
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;
        this.entities = entities;
        this.inventory = inventory;
        this.buildables = buildables;
        this.goals = goals;
        this.animations = animations;
    }

    public List<AnimationQueue> getAnimations() {
        return animations;
    }

    public final String getDungeonName() {
        return dungeonName;
    }

    public final List<ItemResponse> getInventory() {
        return inventory;
    }

    public final List<String> getBuildables() {
        return buildables;
    }

    public final String getGoals() {
        return goals;
    }

    public final String getDungeonId() {
        return dungeonId;
    }

    public final List<EntityResponse> getEntities() {
        return entities;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == null || obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        } 

        if (getClass() != obj.getClass()) {
            return false;
        }
        DungeonResponse other = (DungeonResponse) obj;
        if (dungeonId == null) {
            if (other.dungeonId != null) {
                return false;
            }
        } else if (!dungeonId.equals(other.dungeonId)) {
            return false;
        }

        if (dungeonName == null) {
            if (other.dungeonName != null) {
                return false;
            }
        } else if (!dungeonName.equals(other.dungeonName)) {
            return false;
        }

        if (entities == null) {
            if (other.entities != null) {
                return false;
            }
        } else if (!entities.equals(other.entities)) {
            return false;
        }

        if (inventory == null) {
            if (other.inventory != null) {
                return false;
            }
        } else if (!inventory.equals(other.inventory)) {
            return false;
        }

        if (buildables == null) {
            if (other.buildables != null) {
                return false;
            }
        } else if (!buildables.equals(other.buildables)) {
            return false;
        }

        if (goals == null) {
            if (other.goals != null) {
                return false;
            }
        } else if (!goals.equals(other.goals)) {
            return false;
        }

        // if (animations == null) {
        //     if (other.animations != null) {
        //         return false;
        //     }
        // } else if (!animations.equals(other.animations)) {
        //     return false;
        // }

        return true;
    }
}
