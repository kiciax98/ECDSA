package com.main;

import com.main.ECCUtil.ECurveUtil;

import java.math.BigInteger;

public class PublicKey {
    private EcPoint Q;

    public PublicKey(EcPoint q) {
        Q = q;
    }

    public PublicKey(PrivateKey d, BigInteger a, BigInteger p, BigInteger n, EcPoint G) {
        Q = ECurveUtil.multiplyScalarPoint(a, p, n, d.getD(), G);
    }

    public EcPoint getQ() {
        return Q;
    }

    public static PublicKey generatePublicKey(PrivateKey d, BigInteger a, BigInteger p, BigInteger n, EcPoint G) {
        EcPoint q = ECurveUtil.multiplyScalarPoint(a, p, n, d.getD(), G);
        return new PublicKey(q);
    }

    @Override
    public String toString() {
        return "(" + Q.getX() + ",\n" + Q.getY() + ")";
    }
}
