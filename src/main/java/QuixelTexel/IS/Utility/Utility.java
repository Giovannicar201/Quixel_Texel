package QuixelTexel.IS.Utility;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utility {

    /**
     * Cripta una stringa.
     *
     * @param string La stringa da criptare.
     * @return Una rappresentazione esadecimale dell'hash della stringa.
     * @throws NoSuchAlgorithmException Se l'algoritmo di hashing non è disponibile.
     */
    public static String encrypt(String string) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] message = md.digest(string.getBytes());

        BigInteger no = new BigInteger(1, message);

        String cryptedString = no.toString();

        while(cryptedString.length() < 32){
            cryptedString = "0" + cryptedString;
        }

        return cryptedString;
    }
}


