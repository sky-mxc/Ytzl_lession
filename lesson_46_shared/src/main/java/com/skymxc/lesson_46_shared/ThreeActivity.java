package com.skymxc.lesson_46_shared;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.skymxc.lesson_46_shared.entity.User;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * Created by sky-mxc
 */
public class ThreeActivity extends AppCompatActivity  implements View.OnClickListener{
    private static final String TAG = "ThreeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        ShareSDK.initSDK(this,"18f1584c22248");
        SMSSDK.initSDK(this,"18f382b3c800e","525f29f9295adfac360ab7c3204cb159");

        SMSSDK.registerEventHandler(eventHandler);

    }

    private EventHandler eventHandler = new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Log.e(TAG, "afterEvent: 提交验证码成功:"+data);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    Log.e(TAG, "afterEvent:获取验证码成功 +"+data);
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                    Log.e(TAG, "afterEvent: 返回支持发送验证码的国家列表 ="+data);
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.qq:
                Platform platform = ShareSDK.getPlatform(QQ.NAME);
                //检查授权
                if(platform.isAuthValid()){ //已经授权
                    Log.e(TAG, "onClick: 已经授权");
                    //移除授权
                    platform.removeAccount();
//                    action(platform);
                }else{  //未授权
                    Log.e(TAG, "onClick: 未授权");
                    platform.setPlatformActionListener(actionLis);
                    platform.SSOSetting(false);      
                    platform.showUser(null);        //不指定账号授权
                }
                break;
        }
    }

    /**
     * 进行注册或者 登录操作
     * @param platform
     */
    private void action(Platform platform) {
        //获取数据
        String token = platform.getDb().getToken();
        if (isReg(token)){      //已经注册
            Log.e(TAG, "action: 已经注册");
            //登录
            Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
        }else{  //未注册
            Log.e(TAG, "action: 未注册");
            User user = new User();
            //获取基本信息
            user.setNick(platform.getDb().getUserName());
            user.setGraden(platform.getDb().getUserGender());
            user.setPhoto(platform.getDb().getUserIcon());
            //设置用户名和密码
            user.setAccount("sky-"+user.getNick());
            user.setPwd(user.getAccount());
            user.save();
            Toast.makeText(this, "已经注册咯", Toast.LENGTH_SHORT).show();
        }
    }

    private PlatformActionListener actionLis = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            Toast.makeText(ThreeActivity.this, "授权成功！", Toast.LENGTH_SHORT).show();
            action(platform);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            Toast.makeText(ThreeActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Toast.makeText(ThreeActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 是否注册
     * @param token
     * @return
     */
    private boolean isReg(String token){
       return new Select().from(User.class).where("token = ?",token).exists();
    }

    @Override
    protected void onPause() {
        super.onPause();
       SMSSDK.unregisterAllEventHandler();
    }
}
