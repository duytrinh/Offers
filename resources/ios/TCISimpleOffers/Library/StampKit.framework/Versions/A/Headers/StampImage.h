//
//  StampImage.h
//  StampKit
//
//  Created by Kaneko Yoshio on 2014/03/17.
//  Copyright (c) 2014年 Kaneko Yoshio. All rights reserved.
//

#import <Foundation/Foundation.h>

#import <UIKit/UIKit.h>

/**
 
 スタンプ画像を管理します。
 
 */
@interface StampImage : NSObject

/**
 
 画像ID
 
*/
@property int image_id;

/**
 
 URL
 
*/
@property NSString *url;

/**
 
 画像
 
 */
@property UIImage *image;

@end
