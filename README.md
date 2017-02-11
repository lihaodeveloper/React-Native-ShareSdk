# react-native-sharesdk
share sdk for react-native

## install

`npm i react-native-sharesdk --save`

## link

`react-native link react-native-sharesdk`

### IOS

 **qq 微信登录**

 1. 打开Xcode app项目文件夹, 找到路径为`node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK`的目录
    并且拖动到`Libraries`目录,不要勾选'copy items if needed'。

 2. 在项目中找到"Build Settings"这一栏, 继续往下找到"Framework Search Paths" 这一节，加入以下这些路径值：
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK`,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/Optional`,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/PlatformConnector`,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/Required`,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/PlatformSDK/QQSDK`,
    继续找到"Library Search Paths" 这一节，加入以下这些路径值：
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/PlatformSDK/WeChatSDK`,

 3.在项目中找到"Build Phases"这一栏, 继续往下找到"Link Binary With Libraries"这一节，加入以下这些库：
   `libicucore.tbd, libz.tbd, libstdc++.tbd, JavaScriptCore.framework, libsqlite3.tbd`
   具体依赖如下图

   ![tbdimg](https://github.com/lihaodeveloper/React-Native-ShareSdk/blob/master/asset/tbdimg.png)

 4.在项目中找到"Info"这一栏, 继续往下找到"URL Types"这一节，点击"+"号添加一栏数据，填入qq appid,微信app key
   例如:`tencent100371282`, 100371282 是qq appid.

 5.Under the "Info" tab of your project configuration, add LSApplicationQueriesSchemes of type Array For QQ SDK.

   ![lsqschemesimg](https://github.com/lihaodeveloper/React-Native-ShareSdk/blob/master/asset/lsqschemes.png)
   
   ### items details from [LSApplicationQueriesSchemes](http://wiki.mob.com/ios9-对sharesdk的影响（适配ios-9必读）/) 
   
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
_onPressQQLogin() {
    MobLogin.loginWithQQ().then((data) => {
      console.log('token: ', data.token)
      console.log('user_id: ', data.user_id)
      console.log('user_name: ', data.user_name)
      console.log('user_gender: ', data.user_gender)
      console.log('user_icon: ', data.user_icon)
    }, (err) => {
      console.log(err)
    })
  }

  _onPressWeChatLogin() {
    MobLogin.loginWithWeChat().then((data) => {
      console.log('token: ', data.token)
      console.log('user_id: ', data.user_id)
      console.log('user_name: ', data.user_name)
      console.log('user_gender: ', data.user_gender)
      console.log('user_icon: ', data.user_icon)
    }, (err) => {
      console.log(err)
    })
  }

  _onPressShare() {
    MobLogin.showShare('我是标题', '分享什么内容')
  }

render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity style={styles.qqlogin} onPress={()=>this._onPressLogin()}>
          <Text style={{fontSize: 18, color: 'black'}}>QQLogin</Text>
        </TouchableOpacity>
        ....
      </View>
    )
}
