package com.main;

import com.main.ECCUtil.RandomUtil;

import java.math.BigInteger;

public class PrivateKey {
    public BigInteger d;

    public PrivateKey(BigInteger d) {
        this.d = d;
    }

    public PrivateKey(String d) {
        this.d = new BigInteger(d,16);
    }

    public BigInteger getD() {
        return d;
    }

    public static PrivateKey generatePrivateKey(BigInteger n) {
        return new PrivateKey(RandomUtil.randomBigInteger(n));
    }

    @Override
    public String toString() {
        return d.toString();
    }
}
