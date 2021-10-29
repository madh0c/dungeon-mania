package dungeonmania.response.models;

import dungeonmania.util.Position;

public final class EntityResponse {
    private final String id;
    private final String type;
    private final Position position;
    private final boolean isInteractable;

    public EntityResponse(String id, String type, Position position, boolean isInteractable) {
        this.id = id;
        this.type = type;
        this.position = position;
        this.isInteractable = isInteractable;
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public final String getId() {
        return id;
    }

    public final String getType() {
        return type;
    }

    public final Position getPosition() {
        return position;
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
        EntityResponse other = (EntityResponse) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }

        if (position != other.position) {
            return false;
        }

        if (isInteractable != other.isInteractable){
            return false;
        }

        return true;
    }
}
