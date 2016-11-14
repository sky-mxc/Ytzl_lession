package com.skymxc.lesson_47_encrypt_decrypt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by sky-mxc
 */
public class Main1Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Main1Activity";

    private EditText etContent ;
    private EditText etPwd ;
    private TextView tvEncontent;
    private TextView tvPulicKey;
    private TextView tvPrivateKey;
    private CheckBox cbGen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        etContent  = (EditText) findViewById(R.id.content);
        etPwd = (EditText) findViewById(R.id.pwd);
        tvEncontent = (TextView) findViewById(R.id.encontent);
        tvPulicKey= (TextView) findViewById(R.id.public_key);
        tvPrivateKey=(TextView) findViewById(R.id.private_key);
        cbGen=(CheckBox) findViewById(R.id.cb_generate);
        cbGen.setOnCheckedChangeListener(onCheckedChangeListener);
        tvPrivateKey.setText(read("private_key"));
        tvPulicKey.setText(read("public_key"));
    }
    
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
                String[] strArr =AsymmetricEncryption.generateKey();
                if (strArr !=null) {
                    tvPrivateKey.setText(strArr[0]);
                    tvPulicKey.setText(strArr[1]);
                }else{
                    Toast.makeText(Main1Activity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }else{
                //读取key
                tvPrivateKey.setText(read("private_key"));
                tvPulicKey.setText(read("public_key"));
            }
        }
    };

    private  String read(String fileName){
        BufferedReader br =null;
        try {
             br = new BufferedReader(new InputStreamReader(getAssets().open(fileName),"utf-8"));
            if (br.ready()) {
                return br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.encrypt:
                //加密
                String content = etContent.getText().toString();
                String encryptContent =AsymmetricEncryption.encrypt(read("public_key"),content);
                Log.e(TAG, "onClick: encrypt="+encryptContent);
                tvEncontent.setText(encryptContent);
                Log.e(TAG, "onClick: md5="+Digest.MD5(content));
                Log.e(TAG, "onClick: sha1 = "+Digest.SHA1(content));

//                URLEncoder.encode();
//                URLDecoder.decode();
//                Base64
                break;
            case R.id.decrypt:
                //解密
                encryptContent = etContent.getText().toString();
                content = AsymmetricEncryption.decrypt(read("private_key"),encryptContent);
                Log.e(TAG, "onClick: content="+content);
                tvEncontent.setText(content);
                break;
        }
    }
}
