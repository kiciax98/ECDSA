package com.main;

import java.math.BigInteger;
import java.util.Objects;


public class EcPoint {
    public final static EcPoint POINT_AT_INFINITY = new EcPoint(BigInteger.ZERO,BigInteger.ZERO,true);

    private BigInteger x;
    private BigInteger y;
    private boolean isInfinity;

    public EcPoint(BigInteger x, BigInteger y) {
        if(x == null || y == null){
            throw new NullPointerException();
        }
        this.x = x;
        this.y = y;
        this.isInfinity = false;
    }

    public EcPoint(String x, String y) {
        if(x == null || y == null){
            throw new NullPointerException();
        }
        this.x = new BigInteger(x, 16);
        this.y = new BigInteger(y, 16);
        this.isInfinity = false;
    }

    public EcPoint(BigInteger x, BigInteger y, boolean isInfinity) {
        this.x = x;
        this.y = y;
        this.isInfinity = isInfinity;
    }

    public BigInteger getX() {
        return x;
    }
    public BigInteger getY() {
        return y;
    }
    public boolean isInfinity() {
        return isInfinity;
    }

    @Override
    public String toString() {
        if(this.equals(EcPoint.POINT_AT_INFINITY)) return "Point at infinity";
        else return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EcPoint point = (EcPoint) obj;
        return Objects.equals(x, point.x) && Objects.equals(y, point.y);
    }
}
