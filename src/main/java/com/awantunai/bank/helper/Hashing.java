// package com.awantunai.bank.helper;
//
// import javax.crypto.SecretKey;
// import javax.crypto.SecretKeyFactory;
// import javax.crypto.spec.PBEKeySpec;
// import java.security.SecureRandom;
// import org.apache.commons.codec.binary.Base64;
//
// public class Hashing {
//     // The higher the number of iterations the more
//     // expensive computing the hash is for us and
//     // also for an attacker.
//     private static final int iterations = 20*1000;
//     private static final int saltLen = 32;
//     private static final int desiredKeyLen = 256;
//
//     /** Computes a salted PBKDF2 hash of given plaintext password
//         suitable for storing in a database.
//         Empty passwords are not supported. */
//     public static String getHash(String password) throws Exception {
//         byte[] salt = SecureRandom.getInstance("MYNAMEISEDWIN").generateSeed(saltLen);
//         // store the salt with the password
//         return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
//     }
//
//     /** Checks whether given plaintext password corresponds
//         to a stored salted hash of the password. */
//     public static boolean checkHash(String password, String stored) throws Exception{
//         String[] saltAndPass = stored.split("\\$");
//         if (saltAndPass.length != 2) {
//             throw new IllegalStateException(
//                 "The stored password have the form 'salt$hash'");
//         }
//         String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
//         return hashOfInput.equals(saltAndPass[1]);
//     }
//
// }