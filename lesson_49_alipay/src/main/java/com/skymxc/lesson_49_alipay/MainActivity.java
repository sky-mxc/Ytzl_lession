package com.skymxc.lesson_49_alipay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final  String TAG ="MainActivity";

    private String APP_ID ="2015092000305080";
    private String RSA_PRIVATE = "";

    private TextView tvResult;
    private String orderParam ;
    private   String orderSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = (TextView) findViewById(R.id.create_order);
        tvResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pay:
                Log.e(TAG, "onClick: pay");
                pay();
                break;
            case R.id.create_order:
                Log.e(TAG, "onClick: create_order");
                createOrder();

                break;
            case R.id.sign:
                Log.e(TAG, "onClick: sign");
                sign();
                break;
            case R.id.result:
                break;
        }
    }

    /**
     * 支付
     */
    private void pay() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayTask task =new PayTask(MainActivity.this);
                final Map<String,String> map= task.payV2(orderParam+"&sign="+orderSign,true);
                tvResult.post(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText(tvResult.getText()+"\n支付结果"+map.toString());
                    }
                });
            }
        };

        new Thread(runnable).start();
    }

    /**
     * 签名
     */
    private void sign() {
         orderSign = sign(orderParam,RSA_PRIVATE);
        tvResult.setText(tvResult.getText()+"\n"+orderSign);
    }

    /**
     * 创建订单
     */
    private void createOrder() {
        //订单参数
        Map<String ,String> map = new HashMap<>();


        map.put("app_id",APP_ID);

        map.put("biz_content","{\"timeout_express\":\"1h\",\"product_code\":\"QUICK_MSECUrITY_PAY\",\"total_amount\":\"999999999\",\"subject\":\"测试支付宝\",\"body\":\"我是测试数据\",\"out_trade_no\":\"M0987654321\"}");

        map.put("charset", "utf-8");

        map.put("method", "alipay.trade.app.pay");

        map.put("sign_type", "RSA");

        map.put("timestamp", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

        map.put("version", "1.0");

        //将参数拼接为 字符串

        StringBuffer sb = new StringBuffer();
        List<String> keys = new ArrayList<>(map.keySet());
        //签名必须先排序
        Collections.sort(keys);
        for (String key :keys){
            String value = map.get(key);
            sb.append(key);
            sb.append("=");
            sb.append(urlEncode(value));
            sb.append("&");
        }

        //去掉 &
        if (sb.length()>0) {
            sb.setLength(sb.length() - 1);
        }

        orderParam = sb.toString();
        tvResult.setText("未签："+orderParam);


    }

    /**
     * 对 值进行 url 编码
     * @param value
     * @return
     */
    private String urlEncode(String value) {
        try {
            return URLEncoder.encode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return value;
        }
    }


    /**
     * 得到签名 （真实情况应该在服务端进行 签名，然后返回）
     * @param content 订单参数
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey,Base64.DEFAULT));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance("SHA1WithRSA");

            signature.initSign(priKey);
            signature.update(content.getBytes("utf-8"));

            byte[] signed = signature.sign();

            return Base64.encodeToString(signed,Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
