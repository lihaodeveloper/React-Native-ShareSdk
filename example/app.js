import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  NativeModules
} from 'react-native';

const {MobLogin} = NativeModules

export default class App extends Component {
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
        <TouchableOpacity style={styles.login} onPress={() => this._onPressQQLogin()}>
          <Text style={{ fontSize: 18, color: 'black' }}>QQ登录</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.login} onPress={() => this._onPressWeChatLogin()}>
          <Text style={{ fontSize: 18, color: 'black' }}>微信登录</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.login} onPress={() => this._onPressShare()}>
          <Text style={{ fontSize: 18, color: 'black' }}>分享</Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  login: {
    width: 150,
    height: 60,
    borderColor: 'red',
    borderWidth: StyleSheet.hairlineWidth,
    borderRadius: 12,
    marginBottom: 10,
    justifyContent: 'center',
    alignItems: 'center'
  }
});