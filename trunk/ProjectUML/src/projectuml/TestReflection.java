
package projectuml;

import java.lang.reflect.*;

public class TestReflection {
    
    public static void main(String[] main) {
        java.lang.Package p = java.lang.Package.getPackage("projectuml");
        for (Class<?> c : p.getClass().getClasses()) {
            
        }
    }
    
}
