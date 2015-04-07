//
//  StampStamped.h
//  StampKit
//
//  Created by Kaneko Yoshio on 2014/01/29.
//  Copyright (c) 2014年 Kaneko Yoshio. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "StampStamp.h"
#import "StampImage.h"


/**
 
 スタンプの押印情報を管理します。
 
 */
@interface StampStamped : NSObject

/**
 
 コンテンツID
 
 */
@property int content_id;

/**
 
 スタンプグループID
 
 */
@property int group_id;

/**
 
 スタンプID
 
 */
@property int stamp_id;

/**
 
 スタンプ画像オブジェクト
 
 */
@property StampImage *stampImage;

/**
 
 押印角度
 
 */
@property float angle;

/**
 
 押印X座標
 
 */
@property float x;

/**
 
 押印Y座標
 
 */
@property float y;

/**
 
 押印画像の幅
 
 */
@property float width;

/**
 
 押印画像の高さ
 
 */
@property float height;


/**
 
 スタンプ押印日付
 
 */
@property NSString *stamped_at;


@property NSDictionary * image;


@property NSString * uid;

/**
 
 スタンプ押印データ作成日付
 
 */
@property NSString *created_at;

@end
