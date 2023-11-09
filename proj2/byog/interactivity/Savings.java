package byog.interactivity;

import byog.TileEngine.TETile;
import byog.world.World;
import net.sf.saxon.trans.SymbolicName;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.*;

public class Savings {
    public static class Saving implements Serializable {
        private String saveFileName;
        public Saving(World world, String worldName) {
            try {
                saveFileName = "./byog/Core/saves/" + worldName + ".ser";
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFileName));
                oos.writeObject(world);
                oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public World getWorld() {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFileName));
                return (World) ois.readObject();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private Map<String, Saving> savings = new HashMap<>();
    public static final String savesFile = "./byog/Core/saves/saves.per";
    public Savings() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(savesFile));
            savings = (Map<String, Saving>) ois.readObject();
        } catch (FileNotFoundException e) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savesFile));
                oos.writeObject(new HashMap<String, Saving>());
                oos.close();
            } catch (IOException i) {
                throw new RuntimeException(i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void save(World world, String worldName) {
        Saving newSaving = new Saving(world, worldName);
        savings.put(worldName, newSaving);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savesFile));
            oos.writeObject(savings);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Saving> getSavings() {
        return savings;
    }
}
