
class Problem {
    public static void main(String[] args) {
        int pos = -1;

        for (int i = 0; i < args.length; i++) {
            if ("test".equals(args[i])) {
                pos = i;
            }
        }

        System.out.println(pos);
    }
}