package projectuml;

/**
 * Saves out scenery-objects
 * @author Jens Thuresson, Steve Eriksson
 */
public class TestScenery {

    public static void main(String[] args) {
        System.out.print("Saving scenery...");
        GeneralSerializer<StarField> saver = new GeneralSerializer<StarField>();
        StarField obj = new StarField(500);
        saver.save(obj, "starfield.scenery");
        System.out.println("OK");
    }
    
}
