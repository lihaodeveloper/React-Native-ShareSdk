# react-native-sharesdk
share sdk for react-native

## install

`npm i react-native-sharesdk --save`

## link

`react-native link react-native-sharesdk`

### IOS

 **qq login**

 1. Open your app's Xcode project, Find the `node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK` directory 
    and drag it into your [$(SRCROOT)] directory, uncheck 'copy items if needed'.

 2. Under the "Build Settings" tab of your project configuration, find the "Framework Search Paths" section and edit the value. Add new value,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK`,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/Optional`,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/PlatformConnector`,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/Required`,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/PlatformSDK/QQSDK`,
    find the "Library Search Paths" section and edit the value. Add new value,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/PlatformSDK/WeChatSDK`,

 3.Click the plus sign underneath the "Link Binary With Libraries" list and add the
   `libicucore.tbd, libz.tbd, libstdc++.tbd, JavaScriptCore.framework, libsqlite3.tbd`
   ![Add tbd](https://github.com/lihaodeveloper/React-Native-ShareSdk/tree/master/asset/tbdimg.png)

 4.Under the "Info" tab of your project configuration, find the "URL Types" section and add your app Id.
   exm:tencent100371282, 100371282 is your app Id.

 5.Under the "Info" tab of your project configuration, add LSApplicationQueriesSchemes of type Array For QQ SDK.
   
 6.specify your sharesdk appkey, qq appid and appkey in `node_modules/react-native-sharesdk/ios/rnsharesdk/MobLogin.m`
   ```objectiv-c
      [ShareSDK registerApp:@"iosv1101"
   ```

   ```objectiv-c
      onConfiguration:^(SSDKPlatformType platformType, NSMutableDictionary *appInfo) {
                  
                  switch (platformType)
                  {
                      case SSDKPlatformTypeQQ:
                          [appInfo SSDKSetupQQByAppId:@"100371282"
                                               appKey:@"aed9b0303e3ed1e27bae87c33761161d"
                                             authType:SSDKAuthTypeBoth];
                          break;
                      default:
                          break;
                  }
              }];
   ```


### ANDROID

 **qq login**

1. specify your qq AppId in `node_modules/react-native-sharesdk/android/build.gradle`

  ```xml
  defaultConfig {
        ...
        manifestPlaceholders = [
                QQ_APP_ID: "100371282", //qq AppId
        ]
    }
  ```

2. specify your sharesdk appkey in `node_modules/react-native-sharesdk/android/src/main/assets/ShareSDK.xml`

  ```xml
  <ShareSDK
        AppKey = "androidv1101"/> <!-- 修改成你在sharesdk后台注册的应用的appkey"-->
  ```

3. specify your qq appid and appkey in `node_modules/react-native-sharesdk/android/src/main/assets/ShareSDK.xml`

  ```xml
  <!-- ShareByAppClient标识是否使用微博客户端分享，默认是false -->
	<QQ
        Id="7"
        SortId="7"
        AppId="100371282"
        AppKey="aed9b0303e3ed1e27bae87c33761161d"
        ShareByAppClient="true"
        Enable="true" />
  ```

## usage

```
...
import { NativeModules } from 'react-native'
const {MobLogin} = NativeModules

...
_onPressLogin() {
    MobLogin.loginWithQQ().then((data) => {
      console.log('token: ', data.token)
      console.log('user_id: ', data.user_id)
      console.log('user_name: ', data.user_name)
      console.log('user_gender: ', data.user_gender)
      console.log('user_icon: ', data.user_icon)
    }, (code, mes) => {
      console.log(code, mes)
    })
}

render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity style={styles.qqlogin} onPress={()=>this._onPressLogin()}>
          <Text style={{fontSize: 18, color: 'black'}}>QQLogin</Text>
        </TouchableOpacity>
      </View>
    )
}
