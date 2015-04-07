//
//  StampGroup.h
//  StampKit
//
//  Created by Kaneko Yoshio on 2014/02/19.
//  Copyright (c) 2014年 Kaneko Yoshio. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 
 スタンプグループ情報を管理します。
 
 */
@interface StampGroup : NSObject

/**
 
 スタンプグループID
 
 */
@property int group_id;

/**
 
 コンテンツID
 
 */
@property int content_id;

/**
 
 名前
 
 */
@property NSString *name;

/**
 
 スタンプ情報の配列
 
 */
@property NSMutableArray *stamps;



@end
