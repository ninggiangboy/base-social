package dev.ngb.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public final class RsaKeyLoaderUtils {

    private RsaKeyLoaderUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static PrivateKey loadPrivateKeyFromPem(String pem) {
        try {
            byte[] decoded = decodePem(
                    pem,
                    "-----BEGIN PRIVATE KEY-----",
                    "-----END PRIVATE KEY-----"
            );
            return KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(decoded));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load RSA private key", e);
        }
    }

    public static PublicKey loadPublicKeyFromPem(String pem) {
        try {
            byte[] decoded = decodePem(
                    pem,
                    "-----BEGIN PUBLIC KEY-----",
                    "-----END PUBLIC KEY-----"
            );
            return KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(decoded));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load RSA public key", e);
        }
    }

    public static PrivateKey loadPrivateKeyFromFile(Path path) {
        return loadPrivateKeyFromPem(readFile(path));
    }

    public static PublicKey loadPublicKeyFromFile(Path path) {
        return loadPublicKeyFromPem(readFile(path));
    }

    public static PrivateKey loadPrivateKeyFromClasspath(String resource) {
        return loadPrivateKeyFromPem(readClasspath(resource));
    }

    public static PublicKey loadPublicKeyFromClasspath(String resource) {
        return loadPublicKeyFromPem(readClasspath(resource));
    }

    private static byte[] decodePem(String pem, String header, String footer) {
        String content = pem
                .replace(header, "")
                .replace(footer, "")
                .replaceAll("\\s", "");

        return Base64.getDecoder().decode(content);
    }

    private static String readFile(Path path) {
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read key file: " + path, e);
        }
    }

    private static String readClasspath(String resource) {
        try (InputStream is =
                     RsaKeyLoaderUtils.class.getClassLoader().getResourceAsStream(resource)) {

            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + resource);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new IllegalStateException("Failed to read classpath resource: " + resource, e);
        }
    }
}
