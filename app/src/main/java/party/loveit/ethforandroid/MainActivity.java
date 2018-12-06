package party.loveit.ethforandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.math.BigInteger;
import java.util.List;

import party.loveit.bip44forandroidlibrary.utils.Bip44Utils;
import party.loveit.ethforandroidlibrary.crypto.ECKeyPair;
import party.loveit.ethforandroidlibrary.crypto.Keys;
import party.loveit.ethforandroidlibrary.utils.Numeric;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
