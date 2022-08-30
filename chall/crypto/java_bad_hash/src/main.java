import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;

class Main {
    public static void main(String[] args) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger no = new BigInteger(1, md.digest(args[0].getBytes("ASCII")));
            String hashtext = no.toString(16);
            if (hashtext.equals("d47f18dc7780fe47c24759714e2cd58f")) {
                System.out.println("Good Password!");
            } else {
                System.out.println("BAD Password!");
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
