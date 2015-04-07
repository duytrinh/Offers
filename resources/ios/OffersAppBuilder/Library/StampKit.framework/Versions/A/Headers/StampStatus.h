//
//  StampStatus.h
//  StampKit
//
//  Created by Kaneko Yoshio on 2014/01/23.
//  Copyright (c) 2014年 Leonis&Co. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "StampStatus.h"

/**
 
 APIのステータス情報を管理します。
 
 
 レスポンスステータス内容一覧
 
 0 (SUCCESS)　正常に処理が実行できた場合

 55 (INVALID_APPKEY) 未登録のアプリケーションキーを使用した場合

 100 (NOT_FOUND) 対象リソースが存在しなかった場合

 300 (INVALID_PARAMETER) リクエストパラメータが誤っている場合

 310 (INVALID_AUTH_TOKEN) 有効でない認証トークンを使用した場合

 500 (INTERNAL_SERVER_ERROR) サーバ上の処理でエラーが発生した場合

 999 (UNKNOWN_ERROR) 予期しないエラーが発生した場合
 
 */
@interface StampStatus : NSObject

/**
 
 ステータスコード
    
    0 : 正常終了
 
 */
@property int code;

/**
 
 ステータスメッセージ
 
 */
@property NSString *message;




@end
