https://openjdk.org/jeps/324
支持Curve25519 和 Curve448 的关键协议

> [!NOTE]
> Curve25519 和 Curve448 是两种椭圆曲线，它们在密码学中主要用于密钥交换协议，特别是在实现前向安全的密钥协商上表现优秀。这两种曲线因其良好的安全性和性能特性，在现代加密通信中得到了广泛应用。

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