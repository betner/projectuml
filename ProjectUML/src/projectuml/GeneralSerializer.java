package projectuml;

import java.io.*;

/**
 * GeneralSerializer
 * 
 * Implements a save and load-method for a specified
 * type (that must be serializable).
 * 
 * @author Jens Thuresson, Steve Eriksson
 */
public class GeneralSerializer<Type> {

    /**
     * Serializes an object to file.
     * 
     * @param object Serializable object to save
     * @param filename Path to file
     * @throws NotSerializableException
     */
    public void save(Type object, String filename) {
        if (object != null && object instanceof Serializable) {
            try {
                FileOutputStream file = new FileOutputStream(filename);
                ObjectOutputStream outstream = new ObjectOutputStream(file);
                outstream.writeObject(object);
                outstream.close();
                file.close();
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } else {
            // Wrong type
            System.err.println("***  " + object.toString() + " doesn't extend Serializable!  ***");
        }
    }

    /**
     * Deserializes an object from file.
     * 
     * @param filename Path to file
     * @return Object of the specified type, or null
     */
    @SuppressWarnings("unchecked")
    public Type load(String filename) {
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream instream = new ObjectInputStream(file);
            Type obj = (Type) instream.readObject();
            instream.close();
            file.close();
            return obj;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
}
