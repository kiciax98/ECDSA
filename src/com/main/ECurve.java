package com.main;

import java.math.BigInteger;

public class ECurve {
    private BigInteger p;
    private BigInteger a;
    private BigInteger b;
    private EcPoint g;
    private BigInteger n;


    public ECurve(BigInteger p, BigInteger a, BigInteger b, EcPoint g, BigInteger n) {
        this.p = p;
        this.a = a;
        this.b = b;
        this.g = g;
        this.n = n;

    }

    public BigInteger getP() {
        return p;
    }
    public BigInteger getA() {
        return a;
    }
    public BigInteger getB() {
        return b;
    }
    public EcPoint getG() {
        return g;
    }
    public BigInteger getN() {
        return n;
    }

}
