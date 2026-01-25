package com.ns.frame.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {
    

   // private static final String SECRET_KEY = "1234567890123456"; // 16-char secret key for AES-128
    private static final String ALGORITHM = "AES";

    private static final String SECRET_KEY = System.getProperty("secret.key") != null ? System.getProperty("secret.key") : null ;

    static {
        if (SECRET_KEY == null || SECRET_KEY.length() != 16) {
            throw new IllegalStateException("SECRET_KEY environment variable is missing or length should be 16");
        }
    }

    //------------Encryption Logic----------------//

    public static String encrypt(String plainText){
try{
SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
Cipher cipher = Cipher.getInstance(ALGORITHM);
cipher.init(Cipher.ENCRYPT_MODE, key);  
byte[] encryptBytes = cipher.doFinal(plainText.getBytes());
return Base64.getEncoder().encodeToString(encryptBytes);

}catch(Exception e){
    e.printStackTrace();
    return null;

    }
    }

 //-----------Decryption Logic----------------//

    public static String decrypt(String encryptedText){
        try{
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
             System.out.println("decrypt Text: " + key);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);  
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptBytes = cipher.doFinal(decodedBytes);
            return new String(decryptBytes);
            
            }catch(Exception e){
                e.printStackTrace();
                return null;
            
                }
    }   


    public static void main(String[] args) {
        String username = "nonmember@test.com";
        String pwd="Pass123456";
        String encryptedUsername = encrypt(username );
         String encryptedPwd = encrypt( pwd);
        String decryptedUsr = decrypt(encryptedUsername);
  String decryptedPwd = decrypt(encryptedPwd);
     //   System.out.println("Original Text: " + (username + ":" + pwd));
        System.out.println("Encrypted Text: " + encryptedUsername);
        System.out.println("Encrypted Text: " + encryptedPwd);
        System.out.println("Decrypted Text: " + decryptedUsr);
        System.out.println("Decrypted Text: " + decryptedPwd);
    }

}
