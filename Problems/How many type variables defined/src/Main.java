// Do not remove imports
//import org.junit.runners.model.TestClass;

import java.lang.reflect.TypeVariable;
import java.util.Scanner;

class TypeVariablesInspector {
    public void printTypeVariablesCount(TestClass obj, String methodName) throws Exception {
        TypeVariable<Class<TypeVariablesInspector>>[] count = TypeVariablesInspector.class.;
        System.out.println(count.length);
    }
}