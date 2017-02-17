# react-native-sharesdk
share sdk for react-native

## install

`npm i react-native-sharesdk --save`

## link

`react-native link react-native-sharesdk`

### IOS

 **qq 微信登录 分享**

 1. 打开Xcode app项目文件夹, 找到路径为`node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK`的目录
    并且拖动到`Libraries`目录,不要勾选`copy items if needed`。

 2. 在项目中找到`Build Settings`这一栏, 继续往下找到`Framework Search Paths`这一节，加入以下这些路径值：
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK`,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/Optional`,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/PlatformConnector`,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/Required`,
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/PlatformSDK/QQSDK`,
    继续找到`Library Search Paths`这一节，加入以下这些路径值：
    `$(SRCROOT)/../node_modules/react-native-sharesdk/ios/rnsharesdk/ShareSDK/Support/PlatformSDK/WeChatSDK`,

 3.在项目中找到`Build Phases`这一栏, 继续往下找到`Link Binary With Libraries`这一节，加入以下这些库：
   `libicucore.tbd, libz.tbd, libstdc++.tbd, JavaScriptCore.framework, libsqlite3.tbd`
   具体依赖如下图

   ![tbdimg](https://github.com/lihaodeveloper/React-Native-ShareSdk/blob/master/asset/tbdimg.png)

 4.在项目中找到`Info`这一栏, 往下找到`App Transport Security Settings`这一栏，添加新的一行`Allow Arbitrary Loads`，设置为`YES`
   ![arbitrary](https://github.com/lihaodeveloper/React-Native-ShareSdk/blob/master/asset/arbitrary.png)
   继续往下找到`URL Types`这一节，点击`+`号添加一栏数据，填入qq appid,微信app key
   例如:`tencent100371282`, 100371282 是qq appid.
   ![urltype](https://github.com/lihaodeveloper/React-Native-ShareSdk/blob/master/asset/urltype.png)
   在`URL Types`中添加QQ的AppID，其格式为：”QQ” ＋ AppId的16进制（如果appId转换的16进制数不够8位则在前面补0，如转换的是：5FB8B52，
   则最终填入为：QQ05FB8B52 注意：转换后的字母要大写） 转换16进制的方法：echo ‘ibase=10;obase=16;801312852′|bc，其中801312852为QQ的AppID
   ![urltype16](http://wiki.mob.com/wp-content/uploads/2015/09/9406F13D-F78B-4261-A52B-CFBC7ECF4890.png)

   ### 具体细节可参考：[ios简洁版快速集成](http://wiki.mob.com/ios简洁版快速集成/) 


 5.在项目中找到`Info`这一栏, 添加 `LSApplicationQueriesSchemes` 设置类型 `Array`.

   ![aqs](https://github.com/lihaodeveloper/React-Native-ShareSdk/blob/master/asset/aqs.png)

   ![lsqschemesimg](https://github.com/lihaodeveloper/React-Native-ShareSdk/blob/master/asset/lsqschemes.png)
   
   ### 具体细节可参考： [ios9-对sharesdk的影响](http://wiki.mob.com/ios9-对sharesdk的影响（适配ios-9必读）/) 
   
 6.添加你的 sharesdk appkey, qq appid 和 appkey 在 `node_modules/react-native-sharesdk/ios/rnsharesdk/MobLogin.m`
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

 **qq 微信登录 分享**

1. 添加你的 qq AppId 在 `node_modules/react-native-sharesdk/android/build.gradle`

  ```xml
  defaultConfig {
        ...
        manifestPlaceholders = [
                QQ_APP_ID: "100371282", //qq AppId
        ]
    }
  ```

2. 添加你的 qq sharesdk appkey 在 `node_modules/react-native-sharesdk/android/src/main/assets/ShareSDK.xml`

  ```xml
  <ShareSDK
        AppKey = "androidv1101"/> <!-- 修改成你在sharesdk后台注册的应用的appkey"-->
  ```

3. 添加你的 qq appid 和 appkey 在 `node_modules/react-native-sharesdk/android/src/main/assets/ShareSDK.xml`

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
