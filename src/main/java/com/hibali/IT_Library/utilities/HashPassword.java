package com.hibali.IT_Library.utilities;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class HashPassword {
    private HashPassword() {
    }

    public static String hash(String password) {
        // here am not using random salt due to not wanting to change the database
        String signature = "hicham";
        byte[] salt = signature.getBytes();
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 1000, 256);
        try{
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        }catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
        }
        return null;
    }
}
