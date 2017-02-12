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
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

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

    @ReactMethod
    public void loginWithWeChat(Promise promise) {
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        if(wechat.isAuthValid()){
            wechat.removeAccount(true);
        }
        wechat.setPlatformActionListener(this);
        wechat.showUser(null);
        mPromise = promise;
    }

    @ReactMethod
    public void showShare(String title, String text, String url, String imageUrl) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(true);
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(imageUrl);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // 启动分享GUI
        oks.show(mContext);
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
