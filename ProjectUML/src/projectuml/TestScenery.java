package projectuml;

/**
 * Saves out scenery-objects
 * @author Jens Thuresson, Steve Eriksson
 */
public class TestScenery {

    public static void main(String[] args) {
        GeneralSerializer<StarField> saver = new GeneralSerializer<StarField>();
        StarField obj = new StarField(500);
        saver.save(obj, "starfield.scenery");
    }
    
}
