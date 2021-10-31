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




25/10
Discussed the behaviour of several entities such as bombs, the movement of the mercenary, portals and planned tests.

Issues
	- Are there going to be duplicate item responses in the List<ItemResponse> inventory?
	- Are the IDs of items and entities separate e.g. can an item in the inventory have the same id as an entity in the dungeon?
	- What are dungeon names? (Are they ints, strings, will they have the same name if string?)
	- Mercenary movement, if there are walls blocking the direct path (he has to move away from the player initially to get to the player), does he find the best path or run into a wall?

Portal is activated and carries momentum of the player
Bomb explodes if it is around a activated switch
Bomb cannot be picked up once put down

type:arrow , id: 1
Type: arrow, id: 2
Type: zombie id: 3

inventory
Type:potion , id: 1

Assumptions
	- Keeping consistent with cardinality of interaction between spawner and character, we are assuming that interacting with the mercenary can only be done from 2 tiles of (up down left right)  movement


27/10
JSON - This is the starting point of the game and does not get modified after.
	- Issue: importing the Json into java class 

Implement for standard game mode first, refactor with state pattern later.


Edge Cases:
	- Spider meets boundary: reversal of path?
	- Character meets boundary: still tick?
	- entity’s momentum carries through a portal into a wall or boundary: still teleport?

Assumption: Cant push a boulder THROUGH a portal
	- Should the boulder stay in place or go on top of the portal, rendering the portal inaccessible?

Spider assumptions
	- After reversing direction when a spider hits a boulder, it rests for one tick before moving in the opposite direction
	- Reversing direction means changing from clockwise to anticlockwise, vice versa
	- If there are more than maximum spiders attempted to be added, exception is thrown: InvalidActionException
	
Zombie assumptions
	- Blocked by walls and boulders,
	- Boulders are not affected by zombies

Mercenary assumptions
	- Spawns every 10 ticks
	- In specs, says spawn “periodically” at entry location

Important notes
	- Random movement will use java class “Random”
	- Entry location of the dungeon is where the player is spawned in
	- Dungeon.java
		- “getEntity(arg)” returns entity
			- Argument is String of ID
		- Need an “exists” functions to check if a given entity exists in the dungeon (.entityExists)
			- Can have different types of arguments:
				- Given entity type exists
				- Something exists in given position
				- Given entity type exists in given position
	- Entity.java
		- getPosition()
			- Returns Position class of position
	- To keep track of what id a new entity should have we should have a tracker, int LastUsedId

Minutes
- Started json file loading
- Finished the main parts of the json loader, but “goals” needs to be done
- Finished the “goals” part of the loader
- Deleted the exportEntities folder (full of classes of intialiseWall.java...) and deleted initialiseEntity.java
	- Decided to have switch cases in the json loader file, with simple code to create the new object, and add it to our map of entities in the dungeon
- Created all subclasses of Entity.java (Wall.java...)
- Added several methods for 
	- Entity.java
		- getPosition()
		- getType()
	- Dungeon.java
		- getEntity(String id)
		- Boolean entityExists()
			- Can have different types of arguments:
			- Given entity type exists
			- Something exists in given position
			- Given entity type exists in given position
- Need to finish tests before full implementation

28/10
- Switched the (switch and case) type and case to factory pattern with if and else if statements for the creation of entities 
- Thinking of using composite pattern to get the boolean operator for goals and the subgoals in the json file 
- Discussed only mercenaries can move through portals while spider and zombies don’t get affected and just traverse through it
- Decided on the following health and attack damage of moving entities 
- For methods to get dungeon / get dungeon info (essentially getting a save state), we need to pass in the dungeon’s unique id, NOT the name of the dungeon e.g. controller.getDungeon(int dungeonId). THis is because you can have 2 different saved games that started from the same dungeon

Assumptions:
- Player health set to max health of 100
- Mercenary spawns periodically → spawns every 10 seconds
	- Mercenary prioritises, then up, then down movement, then left, then right
- Base attack of player is 5
	- When player gets a weapon, they get bonus attack damage
- When player has multiple weapons, they stack attack damage and use all weapons against enemies 
- Arrow doesn’t add on any attack damage, needs a bow to do damage
	- If player has 5 attack damage at start, a sword (+10), a bow (+10*2), gets 35 Attack damage
- Moving entities will check if the square they are going to is valid (if it is out of bounds), the entity is stuck against the wall 
- Play attack damage: 2
- Default layer with most entities will be layer 0
- Floor layer with example switch, will be layer -1
- Mercenary can be bribed with 1 gold
- When mercenary becomes ally and starts following player, his is in the same tile as player
- Enemies cannot collide with each other (i.e. can’t be on the same square simultaneously)
- Boulder cannot be pushed onto CollectibleEntity, or MovableEntity
- MOVABLEENTITIES (apart from Spider) CANNOT coincide with exit

30/10
- Finished goals recursive function if there are multiple subgoals within subgoals
- Added goals inside dungeon response
- Tested with multiple dungeons and goals being includes inside dungeon
Assumption:
- List of buildables will include the list of items that can be crafted from the set inventory, does not include the number of buildable items such as [bow, bow, shield]
- Does not take in account whether the total inventory can cover all the buildable items, i.e if there are 2 wood, 1 treasure and 3 arrows, both bow and shield will be added to list even though the 2 wood cannot cover for both the bow and shield
- total  Player atk = base attack + sumAll (items.getAttack)
- Invincibility lasts for 5 ticks

31/10
- Remember to modify collide for Spider (include exit, bomb static etc.)
- getEntity should return a List<Entity> cos there can be multiple on one square

TODO
- ZombieToastSpawner
- Destroy(interact)
- Exit
- Check if game ends
- Battle move to interface, add to MovableEntity
- Check Buildable with battle
- RareCollectible
- Merc + Zombie drop armour

- Gamemode
- Storing games
- Frontend

- Goals - boolean method
- When each battle finishes, check that there are no more enemies then set it to finished
- When collect a treasure, check all treasure is found on the map
- When reach exit, set exit done
- When all switches are pushed on by boulder

- UML
- Assumptions.md
- minutes.md
- Gradle
