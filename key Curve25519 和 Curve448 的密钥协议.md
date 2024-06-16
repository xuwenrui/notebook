```java
  public static void main(String[] args) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {  
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("XDH");  
        NamedParameterSpec paramSpec = new NamedParameterSpec("X25519");  
        kpg.initialize(paramSpec); // equivalent to kpg.initialize(255)  
// alternatively: kpg = KeyPairGenerator.getInstance("X25519")  
        KeyPair kp = kpg.generateKeyPair();  
  
        KeyFactory kf = KeyFactory.getInstance("XDH");  
        BigInteger u =BigInteger.valueOf(197986L);  
        XECPublicKeySpec pubSpec = new XECPublicKeySpec(paramSpec, u);  
        PublicKey pubKey = kf.generatePublic(pubSpec);  
  
        KeyAgreement ka = KeyAgreement.getInstance("XDH");  
        ka.init(kp.getPrivate());  
        ka.doPhase(pubKey, true);  
        byte[] secret = ka.generateSecret();  
        String hashedPassword = Base64.getEncoder().encodeToString(secret);  
        System.out.println(hashedPassword);  
    }
```