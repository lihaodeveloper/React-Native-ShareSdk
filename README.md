# react-native-sharesdk
share sdk for react-native

## install

`npm i react-native-sharesdk --save`

## link

`react-native link react-native-sharesdk`

### android

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
