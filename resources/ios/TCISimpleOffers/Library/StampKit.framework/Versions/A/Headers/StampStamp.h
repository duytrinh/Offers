//
//  StampStamp.h
//  StampKit
//
//  Created by Kaneko Yoshio on 2014/01/23.
//  Copyright (c) 2014年 Leonis&Co. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "StampImage.h"


/**
 
 スタンプ情報を管理します。

 */
@interface StampStamp : NSObject{
    
    //@package NSDictionary * dictionary;
    
}

/**
 
 スタンプID
 
 */
@property int stamp_id;


/**
 
 コンテンツID
 
 */
@property int content_id;


/**
 
 グループID
 
 */
@property int group_id;


/**
 
 タイトル
 
 */
@property NSString *title;


/**
 
 画像
 
 */
@property NSDictionary *image;


/**
 
 画像オフセット
 
 */
@property NSDictionary *image_offset;


/**
 
 座標情報のNSArray配列
 
 */
@property NSArray *coordinates;

/**
 
 許容誤差
 
 */
@property int tolerance;

/**
 
 画像情報
 
 */
@property StampImage *stampImage;


/**
 
 押印情報を作成します。
 
 @param x スタンプ座標
 @param y スタンプ座標
 @param angle スタンプ角度
 @param width スタンプ幅サイズ
 @param height スタンプ高さサイズ
 @param stamped_at スタンプ日時
 
 @return 押印情報の配列
 @deprecated groupsAndHistoriesWithBlockを利用してください。
 
*/
- (NSArray*) stamped:(int)x y:(int)y angle:(float) angle width:(int)width height:(int)height stamped_at:(double)stamped_at;


@end
