package com.main;

import java.math.BigInteger;

public class SignaturePair {
    private BigInteger r;
    private BigInteger s;

    public SignaturePair(BigInteger r, BigInteger s) {
        this.r = r;
        this.s = s;
    }

    public BigInteger getR() {
        return r;
    }

    public BigInteger getS() {
        return s;
    }

    @Override
    public String toString() {
        return "(" + r + ",\n" + s + ")";
    }
}
