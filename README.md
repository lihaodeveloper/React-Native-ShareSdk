1.0.3版
加入qq 登录 for android


安装：   npm i react-native-sharesdk --save

链接：   react-native link

Android:
1.需要在node_modules/react-native-sharesdk/android/build.gradle中替换你申请到的qq appid
         manifestPlaceholders = [
                QQ_APP_ID: "100371282", //qq AppId
         ]
  

2.需要在node_modules/react-native-sharesdk/android/src/main/assets/ShareSDK.xml中替换你申请到的share sdk的 appkey和 qq  appid，appkey。
   <ShareSDK
        AppKey = "androidv1101"/> <!-- 修改成你在sharesdk后台注册的应用的appkey"-->

   <QQ
        Id="7"
        SortId="7"
        AppId="100371282"
        AppKey="aed9b0303e3ed1e27bae87c33761161d"
        ShareByAppClient="true"
        Enable="true" />

3.参照例子开始哈啤
