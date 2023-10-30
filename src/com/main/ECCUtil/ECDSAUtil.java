package com.main.ECCUtil;

import com.main.EcPoint;
import com.main.PrivateKey;
import com.main.PublicKey;
import com.main.SignaturePair;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ECDSAUtil {

    public static SignaturePair generateSignature(BigInteger a, BigInteger p, BigInteger n,
                                                  PrivateKey d, EcPoint G, String msg) {
        BigInteger k, r, s;
        do {
            do {
                k = RandomUtil.randomBigInteger(n);
                EcPoint P = ECurveUtil.multiplyScalarPoint(a, p, n, k, G);
                r = P.getX();
                r = r.mod(n);
            } while (r.equals(BigInteger.ZERO));

            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] m = digest.digest(msg.getBytes(StandardCharsets.UTF_8));
            String message = bytesToHex(m);
            BigInteger e = new BigInteger(message, 16);
            s = d.getD().multiply(r).add(e).multiply(k.modInverse(n)).mod(n);
        } while(s.equals(BigInteger.ZERO));
        return new SignaturePair(r, s);
    }

    public static SignaturePair generateSignatureWithK(BigInteger a, BigInteger p, BigInteger n,
                                                                  PrivateKey d, EcPoint G, BigInteger k, String msg) {
        BigInteger r, s;
       do {
            do {
                EcPoint P = ECurveUtil.multiplyScalarPoint(a, p, n, k, G);
                r = P.getX();
                r = r.mod(n);
            } while (r.equals(BigInteger.ZERO));
            k = k.modInverse(n);
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] m = digest.digest(msg.getBytes(StandardCharsets.UTF_8));
           String message = bytesToHex(m);
            BigInteger e = new BigInteger(message, 16);
            s = r.multiply(d.getD()).add(e).multiply(k).mod(n);

        } while(s.equals(BigInteger.ZERO));
        return new SignaturePair(r, s);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static boolean verifySignature(BigInteger a, BigInteger p, BigInteger n, SignaturePair signaturePair,
                                          PublicKey Q, EcPoint G, String message) {
        if (signaturePair.getR().compareTo(BigInteger.TWO) == -1
                || signaturePair.getR().compareTo(n.subtract(BigInteger.ONE)) == 1) {
            return false;
        }
        if (signaturePair.getS().compareTo(BigInteger.TWO) == -1
                || signaturePair.getS().compareTo(n.subtract(BigInteger.ONE)) == 1) {
            return false;
        }
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] m = digest.digest(message.getBytes(StandardCharsets.UTF_8));
        String msg = bytesToHex(m);
        BigInteger e = new BigInteger(msg, 16);
        BigInteger w = signaturePair.getS().modInverse(n);
        BigInteger u1 = w.multiply(e).mod(n);
        BigInteger u2 = w.multiply(signaturePair.getR()).mod(n);
        EcPoint R1 = ECurveUtil.multiplyScalarPoint(a, p, n, u1, G);
        EcPoint R2 = ECurveUtil.multiplyScalarPoint(a, p, n, u2, Q.getQ());
        EcPoint R = ECurveUtil.addTwoPoints(a, p, R1, R2);
        if(R.isInfinity()) return false;
        BigInteger v = R.getX().mod(n);
        return v.equals(signaturePair.getR());
    }
}