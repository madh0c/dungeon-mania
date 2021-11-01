package dungeonmania;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Exporting a java object as a file; code inspired from
 * https://examples.javacodegeeks.com/core-java/io/fileoutputstream/how-to-write-an-object-to-file-in-java/,
 * https://examples.javacodegeeks.com/core-java/io/file/how-to-read-an-object-from-file-in-java/.
 */

public class Persist { 

	public void exportJava(Object dungeon, String filePath) { 
		try { 
			FileOutputStream output = new FileOutputStream(filePath); 
			ObjectOutputStream dungeonOut = new ObjectOutputStream(output); 
			dungeonOut.writeObject(dungeon); 
			dungeonOut.close(); 
		} catch (Exception exception) { 
			exception.printStackTrace(); 
		} 
	} 

	public Object readFile(String filePath) {
        try {
            FileInputStream input = new FileInputStream(filePath);
            ObjectInputStream dungeonIn = new ObjectInputStream(input);
            Object dungeon = dungeonIn.readObject();
            dungeonIn.close();
            return dungeon;
 
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}