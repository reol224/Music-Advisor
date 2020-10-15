import java.util.Scanner;

class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        StringReverser reverser = new StringReverser() {
            @Override
            public StringBuilder reverse(String str) {
                StringBuilder strBuilder = new StringBuilder(str);
                return strBuilder.reverse();
                //return new StringBuilder(str).reverse().toString();
            }
        };

        System.out.println(reverser.reverse(line));
    }

    interface StringReverser {

        StringBuilder reverse(String str);
    }

}