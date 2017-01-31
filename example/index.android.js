/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  NativeModules
} from 'react-native';

const {MobLogin} = NativeModules

export default class ShareSdkExample extends Component {
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
  qqlogin:{
    width: 150, 
    height: 60, 
    borderColor: 'red', 
    borderWidth: StyleSheet.hairlineWidth, 
    borderRadius: 12, 
    justifyContent: 'center', 
    alignItems: 'center'
  }
});

AppRegistry.registerComponent('ShareSdkExample', () => ShareSdkExample);
