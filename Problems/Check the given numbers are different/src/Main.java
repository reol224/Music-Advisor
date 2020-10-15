import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n0 = 0,n1 = 0,n2 = 0;
        n0 = scanner.nextInt();
        n1 = scanner.nextInt();
        n2 = scanner.nextInt();
        System.out.println(n0 != n1 && n0 != n2 && n1!= n2);

    }
}