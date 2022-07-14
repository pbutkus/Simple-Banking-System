import java.util.Scanner;

public class Main {

    public static int getNumberOfMaxParam(int a, int b, int c) {
        int[] params = new int[3];
        params[0] = a;
        params[1] = b;
        params[2] = c;

        int maxIndex = 1;
        int maxVal = a;

        for (int i = 1; i < params.length; i++) {
            if (maxVal < params[i]) {
                maxVal = params[i];
                maxIndex = i + 1;
            }
        }

        return maxIndex;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        final int a = scanner.nextInt();
        final int b = scanner.nextInt();
        final int c = scanner.nextInt();

        System.out.println(getNumberOfMaxParam(a, b, c));
    }
}