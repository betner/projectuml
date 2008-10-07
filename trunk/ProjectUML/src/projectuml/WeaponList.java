package projectuml;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * WeaponList
 *
 * A list that holds a number of weapons. Could be attached to a ship
 * or anyother sprite that needs to hold weapons.
 * Mounts are positions relative to thw words X- and Y-axis. That means that
 * when shots are fired the weapons position needs to be translated over
 * the weapon owners current position i space.
 *
 * @see Weapon
 * @see Ship
 * @author Steve Eriksson, Jens Thuresson
 */
public class WeaponList implements Serializable, Iterable<Weapon> {

    private ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
    private ArrayList<Point> weaponMount = new ArrayList<Point>();
    private int numberOfWeapons;
    private int currentWeapon;
    private int currentMount;

    /** 
     * Creates a new instance of WeaponList 
     */
    public WeaponList() {
        this.numberOfWeapons = 0;
        currentWeapon = 0;
        currentMount = 0;
    }

    /**
     * Adds a weapon the cyclic list.
     * The weapon gets it's position from the weaponmountlist
     * which must be initialized before weapons are added.
     * Null pointer exception will be thrown otherwise.
     *
     * @param weapon
     */
    public void addWeapon(Weapon weapon) {
        int index = currentWeapon % numberOfWeapons; // List is cyclic

        // If position in list i empty, add weapon, otherwise
        // replace current.
        if (weaponList.size() <= index) {
            weaponList.add(weapon);
        } else {
            weaponList.set(index, weapon);
        }

        weapon.setPosition(weaponMount.get(index));
        weaponList.set(index, weapon);
        currentWeapon++; // Point to next slot in list
    }

    /**
     * Add a weaponmount. Weaponmount is a position on the object
     * where weapons are located. 
     */
    public void addWeaponMount(Point mount) {
        // Only add mount if there is place for it
        if (currentMount < numberOfWeapons) {
            weaponMount.add(mount);
            currentMount++;
        }
    }

    /**
     * Create iterator for the list.
     * 
     * @return listIterator
     */
    public Iterator<Weapon> iterator() {
        return weaponList.listIterator();
    }

    /**
     * Set number of weapons that is supported by the ship.
     * This number is used to make the list cyclic. 
     * The list wraps when an index higher than this value
     * is used.
     * 
     * @param number
     */
    public void setNumberOfWeapons(int number) {
        numberOfWeapons = number;
    }

    /**
     * Method used in serialization.
     * 
     * @param in
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
}
