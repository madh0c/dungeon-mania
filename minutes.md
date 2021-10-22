21/10
The design of the project was discussed, and the UML was designed. More specifically, we discussed the relationships between the entity classes.

Issues
    - Is staticEntity going to be a subclass of entities superclass? Or the individual static entities going to be subclasses of the entity superclass?
    - Should the playable character be aggregated to the moving entity class, or made into a separate subclass of entity for sake of readibility?
    - Inheritance vs Composition?
        -Interaction between entities and the map, or between themselves such as movement should be made into an interface 
    - How is inventory stored
        - Map (entityType, int) → currently preferred to 
        - List<CollectibleEntities>
        - List<List<CollectibleEntities>>

    How to use collectibles
        - Potion and bomb functionality; involves character and dungeon, how do we implement this? 
        - For items that interact with the map, maybe pass in a pointer to the dungeon?
        - For items that interact with player, could implement as methods in the player class itself e.g usePotion()

Assumptions
    - The rare collectible is spawned at the location of battle rather than automatically added to the inventory
    - Everything the player can obtain is a collectibleEntity (collectible entities, rare collectible entities, buildable entities)

Questions for tutors
    - Are we given the map of the dungeons, initial spawn points of entities?
    - What are your thoughts on all the inheritance in the UML diagram?
