import java.util.Random;

class Main {
    static String generateSecretToken() {
        Random r = new Random();
        return Long.toHexString(r.nextLong());
    }

    public static void main(String[] args) {
        System.out.println(generateSecretToken());
    }
}
