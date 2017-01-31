package com.qq.rnsharesdk;

import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by cc on 2017/1/29.
 */

public class MobLoginModule extends ReactContextBaseJavaModule implements PlatformActionListener {
    private Context mContext;
    private Promise mPromise;

    public MobLoginModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext.getApplicationContext();
    }

    @Override
    public String getName() {
        return "MobLogin";
    }

    @Override
    public void initialize() {
        super.initialize();
        //初始化Mob
        ShareSDK.initSDK(mContext);
    }

    @ReactMethod
    public void loginWithQQ(Promise promise) {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        if(qq.isAuthValid()){
            qq.removeAccount(true);
        }
        qq.setPlatformActionListener(this);
        qq.showUser(null);
        mPromise = promise;
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        if (action == Platform.ACTION_USER_INFOR) {
            PlatformDb platDB = platform.getDb();
            WritableMap map = Arguments.createMap();
            map.putString("token", platDB.getToken());
            map.putString("user_id", platDB.getUserId());
            map.putString("user_name", platDB.getUserName());
            map.putString("user_gender", platDB.getUserGender());
            map.putString("user_icon", platDB.getUserIcon());
            mPromise.resolve(map);
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        mPromise.reject("LoginError", throwable.getMessage());
    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
