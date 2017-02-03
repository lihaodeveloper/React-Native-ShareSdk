//
//  MobLogin.m
//  rnsharesdk
//
//  Created by cc on 2017/2/1.
//  Copyright © 2017年 qq. All rights reserved.
//
#import "MobLogin.h"

@implementation MobLogin

RCT_EXPORT_MODULE();

- (instancetype)init
{
    if(self = [super init]){
        NSLog(@"initShareSdk()!");
        /**
         *  设置ShareSDK的appKey，如果尚未在ShareSDK官网注册过App，请移步到http://mob.com/login 登录后台进行应用注册，
         *  在将生成的AppKey传入到此方法中。我们Demo提供的appKey为内部测试使用，可能会修改配置信息，请不要使用。
         *  方法中的第二个参数用于指定要使用哪些社交平台，以数组形式传入。第三个参数为需要连接社交平台SDK时触发，
         *  在此事件中写入连接代码。第四个参数则为配置本地社交平台时触发，根据返回的平台类型来配置平台信息。
         *  如果您使用的时服务端托管平台信息时，第二、四项参数可以传入nil，第三项参数则根据服务端托管平台来决定要连接的社交SDK。
         */
        [ShareSDK registerApp:@"126c6d35d0ecc"
              activePlatforms:@[@(SSDKPlatformTypeWechat),@(SSDKPlatformTypeQQ)]
                     onImport:^(SSDKPlatformType platformType) {
                         
                         switch (platformType)
                         {
                             case SSDKPlatformTypeWechat:
                                 [ShareSDKConnector connectWeChat:[WXApi class]];
                                 break;
                             case SSDKPlatformTypeQQ:
                                 [ShareSDKConnector connectQQ:[QQApiInterface class]
                                            tencentOAuthClass:[TencentOAuth class]];
                                 break;
                             default:
                                 break;
                         }
                     }
              onConfiguration:^(SSDKPlatformType platformType, NSMutableDictionary *appInfo) {
                  
                  switch (platformType)
                  {
                      case SSDKPlatformTypeWechat:
                          [appInfo SSDKSetupWeChatByAppId:@"wx4868b35061f87885"
                                                appSecret:@"64020361b8ec4c99936c0e3999a9f249"];
                          break;
                      case SSDKPlatformTypeQQ:
                          [appInfo SSDKSetupQQByAppId:@"1105893125"
                                               appKey:@"I6R9XKy29SCZTTvp"
                                             authType:SSDKAuthTypeBoth];
                          break;
                      default:
                          break;
                  }
              }];
    }
    return self;
}

RCT_EXPORT_METHOD(loginWithQQ:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    dispatch_async(dispatch_get_main_queue(), ^{
        if([ShareSDK hasAuthorized:(SSDKPlatformTypeQQ)]){
            [ShareSDK cancelAuthorize:(SSDKPlatformTypeQQ)];
        }else{
            [ShareSDK getUserInfo:SSDKPlatformTypeQQ
                   onStateChanged:^(SSDKResponseState state, SSDKUser *user, NSError *error)
             {
                 if (state == SSDKResponseStateSuccess){
                     NSString * const genderStatusName[]={
                         [SSDKGenderMale] = @"m",
                         [SSDKGenderFemale] = @"f",
                         [SSDKGenderUnknown] = @"u",
                     };
                     NSMutableDictionary *result = [NSMutableDictionary dictionary];
                     [result setObject:user.credential.token forKey:@"token"];
                     [result setObject:user.uid forKey:@"user_id"];
                     [result setObject:user.nickname forKey:@"user_name"];
                     [result setObject:genderStatusName[user.gender] forKey:@"user_gender"];
                     [result setObject:user.icon forKey:@"icon"];
                     resolve(result);
                 }else{
                     reject(@"loginWithQQ: ", error, nil);
                 }
             }];
        }
    });
}

@end
