package NetStuff.DataBaseWorks.Hash;
import mainpkg.Main;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringHasher implements Serializable {
    private MessageDigest digest;

    public StringHasher(String algorithm){
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Ошибка: Алгоритм не найден!");
        }
    }

    public String getHash(String str){
        try {
            byte[] bytes = digest.digest(str.getBytes(Main.DEFAULT_CHAR_SET));
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                builder.append(String.format("%2x", bytes[i]));
            }
            return builder.toString().replace(" ", "0");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Ошибка: неподдерживаемая кодировка!");
        }
        return null;
    }
}