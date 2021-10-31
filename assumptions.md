ASSUMPTIONS

General:
- Default layer with most entities will be layer 0
- Floor layer with example switch, will be layer -1


Moving Entity:
- Enemies cannot collide with each other (i.e. can’t be on the same square simultaneously)
- MoveableEntity (apart from Spider) CANNOT coincide with exit

- Mercenary:
	- Keeping consistent with cardinality of interaction between spawner and character, we are assuming that interacting with the mercenary can only be done from 2 tiles of (up down left right)  movement
	- Spawns every 10 ticks
	- In specs, says spawn “periodically” at entry location
	- Mercenary spawns periodically → spawns every 10 seconds
	- Mercenary prioritises up, then down movement, then left, then right
	- Mercenary can be bribed with 1 gold
	- When mercenary becomes ally and starts following player, his is in the same tile as player


- Spider:
	- After reversing direction when a spider hits an obstruction, it rests for one tick before moving in the opposite direction
	- Reversing direction means changing from clockwise to anticlockwise, vice versa
	- If there are more than maximum spiders attempted to be added, exception is thrown: InvalidActionException

- Zombie:
	- Blocked by walls and boulders,
	- Boulders are not affected by zombies

Static Entity:
- Boulder
	- Cant push a boulder ONTO a portal


Collectible Entity:

- Everything the player can obtain is a collectibleEntity (collectible entities, rare collectible entities, buildable entities)

- Rare Collectible Entity:
	- The rare collectible is spawned at the location of battle rather than automatically added to the inventory

- Bow and Arrow:
	- Arrow doesn’t add on any attack damage, needs a bow to do damage
		- If player has 5 attack damage at start, a sword (+10), a bow (+10*2), gets 35 Attack damage

- Boulder:
	- Boulder cannot be pushed onto CollectibleEntity, or MovableEntity



Character:
- Player health set to max health of 100
- Base attack of player is 5
	- When player gets a weapon, they get bonus attack damage
- When player has multiple weapons, they stack attack damage and use all weapons against enemies 
- Play attack damage: 2
- When a player picks up a weapon, only calculated when you battle
