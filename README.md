# ETHForAndroid
eth library for android

[ ![Download](https://api.bintray.com/packages/loveit/maven/ETHForAndroid/images/download.svg) ](https://bintray.com/loveit/maven/ETHForAndroid/_latestVersion)

> # support Android sdk >= 14 #

中文版文档：[https://blog.csdn.net/wypeng2010/article/details/84853134](https://blog.csdn.net/wypeng2010/article/details/84853134)

**- Use it**

1. first add bip44forandroidlibrary dependencies

> implementation 'party.loveit:bip44forandroidlibrary:1.0.7'

2. generate ETH

## code: ##
```java
                    List<String> words = Bip44Utils.generateMnemonicWords(MainActivity.this);
                    Log.e("TAG", "words: " + words.toString());

                    //m / purpose’ / coin_type’ / account’ / change / address_index
                    BigInteger priKey = Bip44Utils.getPathPrivateKey(words, "m/44'/60'/0'/0/0");

                    ECKeyPair ecKeyPair = ECKeyPair.create(priKey);

                    String publicKey = ecKeyPair.getPublicKeyToString();
                    Log.e("TAG", "publicKey: " + publicKey);

                    String privateKey = ecKeyPair.getPrivateKeyToString();
                    Log.e("TAG", "privateKey: " + privateKey);

                    String address = "0x" + Keys.getAddress(ecKeyPair);
                    Log.e("TAG", "address: " + address);
```
## result: ##
```base
2018-12-06 14:04:54.053 6857-7056/party.loveit.ethforandroid E/TAG: words: [kite, portion, actress, prize, multiply, odor, mobile, social, refuse, fruit, tunnel, smart]
2018-12-06 14:04:54.648 6857-7056/party.loveit.ethforandroid E/TAG: publicKey: 0x7eac8e97e93d250630ff507c4339a4950f9b194d0951ac7b4b260c61521613f77b13dc8621cd0691f711c134ba5693a7460dc5231c98636ce6f727d4e725596a
2018-12-06 14:04:54.648 6857-7056/party.loveit.ethforandroid E/TAG: privateKey: 0xc3f0b16731bc42ae07ff0304ba1172e6414dcd8de7a612e1ec281c109181f3d9
2018-12-06 14:04:54.653 6857-7056/party.loveit.ethforandroid E/TAG: address: 0x4a7484c7c9ed536ef428e48240ad5707b21dc6e8
```
3. sign eth transaction

```java
public static String signedEthTransactionData(String privateKey, String to, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String value) throws Exception {
        
        //1ETH = 10^18 Wei
        BigDecimal realValue = Convert.toWei(value, Convert.Unit.ETHER);
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, to, realValue.toBigIntegerExact());
        //fee= (gasPrice * gasLimit ) / 10^18 ether

        Credentials credentials = Credentials.create(privateKey);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        
        return Numeric.toHexString(signedMessage);
    }
```
4. sign eth contract transaction

```java
public static String signedEthContractTransactionData(String privateKey, String contractAddress, String to, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, Double value, Double decimal) throws Exception {
        if (words == null || words.size() != 12) {
            throw new RuntimeException("please generateMnemonic first");
        }
        
        BigDecimal realValue = BigDecimal.valueOf(value * Math.pow(10.0, decimal));

        
        String data = "0xa9059cbb" + Numeric.toHexStringNoPrefixZeroPadded(Numeric.toBigInt(to), 64) + Numeric.toHexStringNoPrefixZeroPadded(realValue.toBigInteger(), 64);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress, data);
        //fee= (gasPrice * gasLimit ) / 10^18 ether

        Credentials credentials = Credentials.create(privateKey);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
       
        return Numeric.toHexString(signedMessage);
    }
```


 **- How to dependencies**
1. Maven

```base
<dependency>
  <groupId>party.loveit</groupId>
  <artifactId>ethforandroidlibrary</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```
2. Gradle

```base
compile 'party.loveit:ethforandroidlibrary:1.0.1'

or

implementation 'party.loveit:ethforandroidlibrary:1.0.1'

```
3. Ivy

```base
<dependency org='party.loveit' name='ethforandroidlibrary' rev='1.0.1'>
  <artifact name='ethforandroidlibrary' ext='pom' ></artifact>
</dependency>
```




 **- License**

     Copyright 2018 52it.party
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.