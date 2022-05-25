import java.lang.reflect.Array;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.axis.AxisFault;
import org.apache.commons.codec.binary.Base64;

public class Base64Encoder {
   public String encrypt(String message) throws AxisFault {
      String str = "";

      try {
         MessageDigest md = MessageDigest.getInstance("SHA-1");
         byte[] digestOfPassword = md.digest("TDES2006YWR".getBytes("utf-8"));
         byte[] keyBytes = (byte[])resizeArray(digestOfPassword, 24);
         int j = 0;

         for(int var7 = 16; j < 8; keyBytes[var7++] = keyBytes[j++]) {
         }

         SecretKey key = new SecretKeySpec(keyBytes, "DESede");
         IvParameterSpec iv = new IvParameterSpec(new byte[8]);
         Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
         cipher.init(1, key, iv);
         byte[] plainTextBytes = message.getBytes("utf-8");
         byte[] cipherText = cipher.doFinal(plainTextBytes);
         byte[] encryptedString = Base64.encodeBase64(cipherText);
         str = new String(encryptedString);
         return str;
      } catch (Exception var12) {
         return str;
      }
   }

   public String decrypt(String message) throws AxisFault {
      String str = "";

      try {
         MessageDigest md = MessageDigest.getInstance("SHA-1");
         byte[] digestOfPassword = md.digest("TDES2006YWR".getBytes("utf-8"));
         byte[] keyBytes = (byte[])resizeArray(digestOfPassword, 24);
         int j = 0;

         for(int var7 = 16; j < 8; keyBytes[var7++] = keyBytes[j++]) {
         }

         SecretKey key = new SecretKeySpec(keyBytes, "DESede");
         IvParameterSpec iv = new IvParameterSpec(new byte[8]);
         Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
         decipher.init(2, key, iv);
         byte[] encData = Base64.decodeBase64(message.getBytes());
         byte[] plainText = decipher.doFinal(encData);
         str = new String(plainText, "UTF-8");
         return str;
      } catch (Exception var11) {
         return str;
      }
   }

   private static Object resizeArray(Object oldArray, int newSize) {
      int oldSize = Array.getLength(oldArray);
      Class elementType = oldArray.getClass().getComponentType();
      Object newArray = Array.newInstance(elementType, newSize);
      int preserveLength = Math.min(oldSize, newSize);
      if (preserveLength > 0) {
         System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
      }

      return newArray;
   }
}
