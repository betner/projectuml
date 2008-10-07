package projectuml;

import java.util.*;

/**
 * An abstract factory, used by the other more concrete factories
 * 
 * @author Jens Thuresson, Steve Eriksson
 * @param <E> Class to store in the factory
 */
abstract public class GeneralFactory<E> implements Iterable<String> {
    
    // Holds our creators
    private Hashtable<String, GeneralFactoryCreator<E>> creators;
    
    /**
     * Represents an abstract creator
     * 
     * @param <E> Class to create
     */
    abstract protected class GeneralFactoryCreator<E> {
        // Returns a new instance of a class of type E
        abstract public E create();
    }
    
    /**
     * Initiates the factory
     */
    public GeneralFactory() {
        creators = new Hashtable<String, GeneralFactory<E>.GeneralFactoryCreator<E>>();
    }
    
    /**
     * Adds a creator to our factory
     * 
     * @param name Name of which the creator should go by
     * @param creator The creator instance
     */
    protected void add(String name, GeneralFactoryCreator<E> creator) {
        creators.put(name, creator);
    }
    
    /**
     * Returns an instance of a class of type E, if there's
     * a creator registered by that name. Otherwise null is
     * returned
     * 
     * @param name Name of the object type
     * @return A new instance, or null
     */
    public E create(String name) {
        if (creators.containsKey(name)) {
            GeneralFactoryCreator<E> creator = creators.get(name);
            return creator.create();
        } else {
            return null;
        }
    }
    
    /**
     * Creates an instance from a randomly chosen creator
     * 
     * @return A new instance, or null if there aren't any creators
     */
    public E createRandom() {
        if (creators.isEmpty()) {
            return null;
        } else {
            int index = Randomizer.getRandomNumber(0, creators.size()-1);
            String name = (String)creators.keySet().toArray()[index];
            return creators.get(name).create();
        }
    }

    /**
     * @return An iterator to the creators (their names)
     */
    public Iterator<String> iterator() {
        return creators.keySet().iterator();
    }

}