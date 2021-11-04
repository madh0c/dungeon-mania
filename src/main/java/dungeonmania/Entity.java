package dungeonmania;

import dungeonmania.util.Position;


public abstract class Entity {
    
    Position position;
    String type;
	String id;

    public Entity(String id, Position position, String type) {
		this.id = id;
        this.position = position;
        this.type = type;
    }

	public Position getPosition() {
		return position;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

    public boolean isInteractable() {
        return (type == "player" || type == "zombie_toast_spawner" || type == "mercenary");
    }

	/**
	 * collides this.entity with the given entity
	 * @param entity
	 * @return true if they can collide
	 */
	public boolean collide(Entity entity) {
		// If empty space
		if (entity == null) {
			return true;
		}
		return false;
	}


	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		Entity other = (Entity) obj;

		if (id == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!id.equals(other.getId())) {
			return false;
		} 

		if (position == null) {
			if (other.getPosition() != null) {
				return false;
			}
		} else if (!position.equals(other.getPosition())) {
			return false;
		}

		if (type == null) {
			if (other.getType() != null) {
				return false;
			}
		} else if (!type.equals(other.getType())) {
			return false;
		}

		return true;
	}
}
