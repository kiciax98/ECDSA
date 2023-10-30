package com.main.ECCUtil;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomUtil {

    public static BigInteger randomBigInteger(BigInteger n) {
        SecureRandom random = new SecureRandom();

        BigInteger result = new BigInteger(n.bitLength(), random);
        while(result.compareTo(BigInteger.ZERO) == 0) {
            result = new BigInteger(n.bitLength(), random);
        }
        return result;
    }
}
