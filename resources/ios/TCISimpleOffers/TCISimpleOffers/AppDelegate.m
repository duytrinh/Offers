//
//  AppDelegate.m
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 1/21/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import "AppDelegate.h"
#import <OffersKit/OffersKit.h>

@interface AppDelegate ()

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    
    // OffersKitSDKの初期化
    // Initial OffersKitSDK
    [[OffersKit sharedInstance] setServerHost:OFFERS_API_SERVER_HOSTNAME
                                     protocol:OFFERS_API_SERVER_PROTOCOL
                                   apiVersion:OFFERS_API_SERVER_API_VERSION
                               applicationKey:OFFERS_API_SERVER_APPLICATION_KEY
     ];
    
    // 画像サイズの変更(変更が不要な場合は、nilまたは未指定)
    // Change image size( Let nil or empty incase unnecessary)
    [[OffersKit sharedInstance] setThumbnailImageName:@"largethumb"];
    // タイムアウト
    // Set timeout
    [[OffersKit sharedInstance] setRequestTimeoutInterval:30.0f];
    
    // サーバで定義されたアプリ情報をよみこむ
    // Get apllication information which defined from server
    if([[OffersKit sharedInstance] loadClientAppConfig] == YES ) {
        NSLog( @"AppConfig: %@", [OffersKit sharedInstance].clientAppConfig );
    }
    
    // ローカルにたまっているデータを送信する間隔と種別
    // Background synchronization settings
    [[OffersKit sharedInstance] setBackgroundSyncTimeInterval:20.0f syncType:OffersKitSyncTypeSubmit];
    
    // SDKの初期化
    // Initial SDK
    [OffersCoupon setCouponsCapacity:100];
    
    // 開始する
    // Start
    [[OffersKit sharedInstance] start];
    
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
