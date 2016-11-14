package com.skymxc.lesson_47_encrypt_decrypt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private static  final String TAG = "MainActivity";
    
    private EditText etContent ;
    private EditText etPwd ;
    private TextView tvEncontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etContent  = (EditText) findViewById(R.id.content);
        etPwd = (EditText) findViewById(R.id.pwd);
        tvEncontent = (TextView) findViewById(R.id.encontent);
    }

    @Override
    public void onClick(View view) {
                String pwd = etPwd.getText().toString();
        switch (view.getId()){
            case R.id.encrypt:
                //加密
                String content = etContent.getText().toString();
                String encryptContent =SymmetricEncryption.encrypt(SymmetricEncryption.TYPE_AES,pwd,content);
                Log.e(TAG, "onClick: encrypt="+encryptContent);
                tvEncontent.setText(encryptContent);
                etContent.append(encryptContent);
                break;
            case R.id.decrypt:
                //解密
                encryptContent = etContent.getText().toString();
                content = SymmetricEncryption.decrypt(SymmetricEncryption.TYPE_AES,pwd,encryptContent);
                Log.e(TAG, "onClick: content="+content);
                tvEncontent.setText(tvEncontent.getText()+"\n"+content);

                break;
        }
    }
}
