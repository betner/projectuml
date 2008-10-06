package projectuml;

/**
 * Saves out scenery-objects
 * @author Jens Thuresson, Steve Eriksson
 */
public class TestScenery {

    public static void main(String[] args) {
        System.out.print("Saving scenery...");
        GeneralSerializer<StarField> saver = new GeneralSerializer<StarField>();
        saver.save(new StarField(200), "starfield.scenery");
        System.out.println("OK");
    }
    
}
