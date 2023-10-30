package com.main.ECCUtil;

import com.main.EcPoint;

import java.math.BigInteger;

public class ECurveUtil {

    public static EcPoint multiplyScalarPoint(BigInteger a, BigInteger p, BigInteger n, BigInteger k, EcPoint P) {
        EcPoint temp = P;
        k = k.mod(n);
        byte one = 48;
        byte[] kByteArray = k.toString(2).getBytes();
        for(int i = 0; i < kByteArray.length; i++) {
            kByteArray[i] = (byte) (kByteArray[i] - one);
        }
        for(int i = 1; i < kByteArray.length; i++) {
            P = pointDouble(a, p, P);
            if(kByteArray[i] == 1) {
                P = addTwoPoints(a, p, P, temp);
            }
        }
        return P;
    }

    public static EcPoint normalMultiply(BigInteger a, BigInteger p, BigInteger k, EcPoint P) {
        EcPoint temp = P;
        for(BigInteger i = BigInteger.ONE; i.compareTo(k) != 0 ; i = i.add(new BigInteger("1"))) {
            temp = addTwoPoints(a, p, temp, P);
        }
        return temp;
    }

    public static EcPoint addTwoPoints(BigInteger a, BigInteger p, EcPoint p1, EcPoint p2) {
        if(p1.equals(p2))
            return pointDouble(a, p, p1);
        else if (p1.equals(EcPoint.POINT_AT_INFINITY))
            return p2;
        else if (p2.equals(EcPoint.POINT_AT_INFINITY))
            return p1;
        else if (p1.getX().equals(p2.getX()) && !p1.getY().equals(p2.getY()))
            return EcPoint.POINT_AT_INFINITY;
        BigInteger m = (p2.getY().subtract(p1.getY())).multiply(p2.getX().subtract(p1.getX()).modInverse(p)).mod(p);
        BigInteger x = (m.modPow(new BigInteger("2"), p).subtract(p1.getX())).subtract(p2.getX()).mod(p);
        BigInteger y = m.multiply(p1.getX().subtract(x)).subtract(p1.getY()).mod(p);
        return new EcPoint(x, y);
    }

    public static EcPoint pointDouble(BigInteger a, BigInteger p, EcPoint P) {
        if(P.equals(EcPoint.POINT_AT_INFINITY)) {
            return P;
        }
        BigInteger m = (P.getX().pow(2)).multiply(new BigInteger("3"));
        m = m.add(a);
        m = m.multiply(P.getY().multiply(new BigInteger("2")).modInverse(p));
        BigInteger x = m.pow(2).subtract(P.getX().multiply(new BigInteger("2"))).mod(p);
        BigInteger y = (P.getY().negate()).add(m.multiply(P.getX().subtract(x))).mod(p);
        return new EcPoint(x, y);
    }
}
