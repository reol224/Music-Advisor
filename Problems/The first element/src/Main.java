// do not remove imports
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
class ArrayUtils {
    // define getFirst method here
    public static <T> T getFirst(T[] arr) {
        if(arr.length >0){
            return arr[0];
        }
        return null;
        //return Arrays.stream(array).findFirst().orElse(null);
    }
}