package com.main;

import com.main.ECCUtil.ECDSAUtil;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        String msg = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer semper vestibulum tortor quis tristique. Fusce pretium, dolor eget placerat sodales, nisl tellus rhoncus metus, eget volutpat ligula nibh et elit. Sed purus massa, ullamcorper quis pulvinar a, cursus id neque. Nam mollis, tortor a ultricies pretium, mi nisi ornare neque, a dignissim ex est id arcu. Sed laoreet, lorem eget consectetur dictum, sem urna auctor quam, id finibus sem magna nec diam. Maecenas venenatis sem ac ligula biam.";

        BigInteger p = new BigInteger("01ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);
        BigInteger a = new BigInteger("01fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffc", 16);
        BigInteger b = new BigInteger("0051953eb9618e1c9a1f929a21a0b68540eea2da725b99b315f3b8b489918ef109e156193951ec7e937b1652c0bd3bb1bf073573df883d2c34f1ef451fd46b503f00", 16);
        BigInteger n = new BigInteger("01fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffa51868783bf2f966b7fcc0148f709a5d03bb5c9b8899c47aebb6fb71e91386409", 16);
        EcPoint G = new EcPoint("00c6858e06b70404e9cd9e3ecb662395b4429c648139053fb521f828af606b4d3dbaa14b5e77efe75928fe1dc127a2ffa8de3348b3c1856a429bf97e7e31c2e5bd66"
                , "011839296a789a3bc0045c8a5fb42c7d1bd998f54449579b446817afbd17273e662c97ee72995ef42640c550b9013fad0761353c7086a272c24088be94769fd16650");
        ECurve p256 = new ECurve(p, a, b, G, n);


        PrivateKey d = PrivateKey.generatePrivateKey(n);
        PublicKey Q = PublicKey.generatePublicKey(d, a, p, n, G);
        SignaturePair signaturePair = ECDSAUtil.generateSignature(a, p, n, d, G, msg);
        boolean isCorrect = ECDSAUtil.verifySignature(a,p,n,signaturePair,Q,G,msg);
    }
}

