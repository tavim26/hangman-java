package io.github.tavim26.hangman.service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Hashes passwords with PBKDF2-HMAC-SHA256 and a random salt.
 * Stored format: pbkdf2$iterations$salt$hash (salt and hash in Base64).
 */
public final class PasswordHasher
{
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 600_000;
    private static final int SALT_BYTES = 16;
    private static final int HASH_BITS = 256;

    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordHasher()
    {
    }

    public static String hash(String password)
    {
        byte[] salt = new byte[SALT_BYTES];
        RANDOM.nextBytes(salt);

        byte[] hash = pbkdf2(password, salt, ITERATIONS);

        return "pbkdf2$" + ITERATIONS
                + "$" + Base64.getEncoder().encodeToString(salt)
                + "$" + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verify(String password, String storedHash)
    {
        String[] parts = storedHash.split("\\$");
        if (parts.length != 4 || !parts[0].equals("pbkdf2"))
        {
            return false;
        }

        int iterations = Integer.parseInt(parts[1]);
        byte[] salt = Base64.getDecoder().decode(parts[2]);
        byte[] expected = Base64.getDecoder().decode(parts[3]);

        byte[] actual = pbkdf2(password, salt, iterations);

        // Constant-time comparison, so the check does not leak timing information
        return MessageDigest.isEqual(expected, actual);
    }

    private static byte[] pbkdf2(String password, byte[] salt, int iterations)
    {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, HASH_BITS);
        try
        {
            return SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec).getEncoded();
        }
        catch (GeneralSecurityException e)
        {
            throw new IllegalStateException("PBKDF2 is not available in this Java runtime", e);
        }
        finally
        {
            spec.clearPassword();
        }
    }
}