package dungeonmania;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dungeonmania.allEntities.Player;
import dungeonmania.allEntities.Portal;
import dungeonmania.allEntities.*;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import java.lang.String;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInOut {

	public static void toJSON(String path, Dungeon dungeon) throws IOException {

		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
			
			Writer writer = new FileWriter(path);
			gson.toJson(dungeon, writer);
			writer.flush(); 
        	writer.close();
		} catch (Exception e) {
			System.out.println("Game Doesn't Exist");
		}
	}

	public static Dungeon fromJSON(String fileName, String feed, int lastUsedDungeonId) throws IOException {
        Map<String, Entity> returnMap = new HashMap<String, Entity>();
		List<CollectibleEntity> returnInv = new ArrayList<>();
		String goals = null;
		String playMode = null; 
		String jsonString = null;


		try {
			jsonString = FileLoader.loadResourceFile("/savedGames/" + fileName);
            Map<String, Object> jsonMap = new Gson().fromJson(jsonString, Map.class);

			playMode = (String)jsonMap.get("gameMode"); 
			goals = (String)jsonMap.get("goals"); 

			Map<String, Map<String, Object>> entityMap = (Map<String, Map<String, Object>>)jsonMap.get("entities"); 

			for (Map.Entry<String, Map<String, Object>> entry : entityMap.entrySet()) {
                Map<String, Object> currentEntity = entry.getValue();
                
				String entityType = (String)currentEntity.get("type");
				String entityId = (String)currentEntity.get("id");
   
				Map<String, Double> entityPosition = (Map<String, Double>)currentEntity.get("position");

				Double xDouble = (Double)entityPosition.get("x");
                Double yDouble = (Double)entityPosition.get("y");
				Double zDouble = (Double)entityPosition.get("layer");

				int xCoord = xDouble.intValue();
				int yCoord = yDouble.intValue();
				int zCoord = zDouble.intValue();
				
                Position exportPos = new Position(xCoord, yCoord, zCoord);

				if (entityType.contains("portal")) {
					String colour = (String)currentEntity.get("colour");
					Portal portal = new Portal(entityId, exportPos, colour);
					returnMap.put(entityId, portal);
				} else if (entityType.contains("player")) {
					Player player = new Player(entityId, exportPos, playMode);
                
					Double healthD = (Double)currentEntity.get("health");
					int health = healthD.intValue();

					Double attackD = (Double)currentEntity.get("attack");
					int attack = attackD.intValue();

					boolean visible = (boolean)currentEntity.get("visible");
					boolean haveKey = (boolean)currentEntity.get("haveKey");

					Double invinceD = (Double)currentEntity.get("invincibleTickDuration");
					int invincibleTickDuration = invinceD.intValue();

					player.setHealth(health);
					player.setAttack(attack);
					player.setVisibility(visible);
					player.setCurrentDir(Direction.UP);
					player.setHaveKey(haveKey);
					player.setInvincibleTickDuration(invincibleTickDuration);

					returnMap.put(entityId, player);

				} else {
					Entity newEntity = EntityFactory.createEntity(entityId, entityType, exportPos);
					returnMap.put(entityId, newEntity);
				}

			}

			List<Map<String,Object>> inventoryList = (List<Map<String,Object>> )jsonMap.get("inventory"); 

			for (Map<String, Object> itemMap : inventoryList) {

				Map<String, Object> currentItem = itemMap;

				String itemType = (String)currentItem.get("type");
				String itemId = (String)currentItem.get("id");

				Map<String, Double> entityPosition = (Map<String, Double>)currentItem.get("position");

				Double xDouble = (Double)entityPosition.get("x");
                Double yDouble = (Double)entityPosition.get("y");
				Double zDouble = (Double)entityPosition.get("layer");

				int xCoord = xDouble.intValue();
				int yCoord = yDouble.intValue();
				int zCoord = zDouble.intValue();
				
                Position itemPos = new Position(xCoord, yCoord, zCoord);

				if (itemType.equals("treasure")) {
					Treasure newTreasure = new Treasure(itemId, itemPos);
					returnInv.add(newTreasure);
				} else if (itemType.equals("key")){
					Key newKey = new Key(itemId, itemPos);
					returnInv.add(newKey);
				} else if (itemType.equals("health_potion")){
					HealthPotion newHP = new HealthPotion(itemId, itemPos);
					returnInv.add(newHP);
				} else if (itemType.equals("invincibility_potion")){
					InvincibilityPotion newINVCP = new InvincibilityPotion(itemId, itemPos);
					returnInv.add(newINVCP);
				} else if (itemType.equals("invisibility_potion")){
					InvisibilityPotion newINVSP = new InvisibilityPotion(itemId, itemPos);
					returnInv.add(newINVSP);
				} else if (itemType.equals("wood")){
					Wood newWood = new Wood(itemId, itemPos);
					returnInv.add(newWood);
				} else if (itemType.equals("arrow")){
					Arrow newArrow = new Arrow(itemId, itemPos);
					returnInv.add(newArrow);
				} else if (itemType.equals("bomb")){
					BombItem newBomb = new BombItem(itemId, itemPos);
					returnInv.add(newBomb);
				} else if (itemType.equals("sword")){
					Double durabilityD = (Double)currentItem.get("durability");
					int durability = durabilityD.intValue();
					Sword newSword = new Sword(itemId, itemPos);
					newSword.setDurability(durability);
					returnInv.add(newSword);
				} else if (itemType.equals("armour")){
					Double durabilityD = (Double)currentItem.get("durability");
					int durability = durabilityD.intValue();
					Armour newArmour = new Armour(itemId, itemPos);
					newArmour.setDurability(durability);
					returnInv.add(newArmour);
				} else if (itemType.equals("one_ring")){
					OneRing newRing = new OneRing(itemId, itemPos);
					returnInv.add(newRing);
				} else if (itemType.equals("bow")){
					Double durabilityD = (Double)currentItem.get("durability");
					int durability = durabilityD.intValue();
					Bow newBow = new Bow(itemId, itemPos);
					newBow.setDurability(durability);
					returnInv.add(newBow);
				} else if (itemType.equals("shield")){
					Double durabilityD = (Double)currentItem.get("durability");
					int durability = durabilityD.intValue();
					Shield newShield = new Shield(itemId, itemPos);
					newShield.setDurability(durability);
					returnInv.add(newShield);
				}
			}

			Dungeon returnDungeon = new Dungeon(lastUsedDungeonId, feed, returnMap, playMode, goals);

			Double tickD = (Double)jsonMap.get("tickNumber"); 
			int tickNumber = tickD.intValue();

			Double hisD = (Double)jsonMap.get("historicalEntCount");  
			int historicalEntCount = hisD.intValue();

			Map<String, Double> spawnPos = (Map<String, Double>)jsonMap.get("spawnpoint");

			Double xSD = (Double)spawnPos.get("x");
			Double ySD = (Double)spawnPos.get("y");
			Double zSD = (Double)spawnPos.get("layer");

			int xSC = xSD.intValue();
			int ySC= ySD.intValue();
			int zSC = zSD.intValue();
			
			Position spawnpoint = new Position(xSC, ySC, zSC);
			returnDungeon.setId(lastUsedDungeonId);
			returnDungeon.setName(feed);
			returnDungeon.setInventory(returnInv);
			returnDungeon.setEntities(returnMap);
			returnDungeon.setGoals(goals);
			returnDungeon.setHistoricalEntCount(historicalEntCount);
			returnDungeon.setTickNumber(tickNumber);
			returnDungeon.setSpawnpoint(spawnpoint);
			
			String path = ("src/main/resources/savedGames/" + fileName); 
			File removeFile = new File(path);
			
			removeFile.delete();  


			return returnDungeon;

		} catch (Exception e) {
			System.out.println("Error Loading Game");
		}

		return null;
	}

}