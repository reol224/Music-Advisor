import java.util.ArrayList;
import java.util.List;

///**
// * Class to work with
// */
//class Bakery {}
//
//class Cake extends Bakery {}
//
///* This one should not */
//class Paper {}
//
///* These boxes are used to pack stuff */
//class Box<T> {
//    void put(T item) { /* implementation omitted */ }
//    T get() { /* implementation omitted */ }
//}
//
///* This quality checker ensures that boxes for sale contain Bakery and anything else */
//class NaiveQualityControl {
//
//    public static boolean check(List<Box<? extends Bakery>> boxes) {
//    /* Method signature guarantees that all illegal
//       calls will produce compile-time error... or not? */
//        return true;
//    }
//
//}
class Violator {

    public static List<Box<? extends Bakery>> defraud() {
        // Add implementation here
        List<Box<? extends Bakery>> list = new ArrayList<>();
        var box = new Box<Paper>();
        box.put(new Paper());
        ((List)list).add(box);
        return list;


    }

}