//
//  StampKit.h
//  StampKit
//
//  Created by Kaneko Yoshio on 2014/01/21.
//  Copyright (c) 2014年 Leonis&Co. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 
 スタンプサーバーとのAPI通信とスタンプグループ情報と押印情報をローカル管理します。
 
 */
@interface StampKit : NSObject

/**
 
 StampKitの共有インスタンスを返します。
 
 @return 共有インスタンス
*/
+ (StampKit*)sharedManager;

/**

 APIサーバーの接続情報を設定します。
 
 @param host APIサーバーのホスト
 @param protocol APIサーバーのプロトコル
 @param apiVersion APIサーバーのバージョン
 @param applicationKey APIサーバーへ接続するアプリケーションキー
 
*/

-(void)setServerHost:(NSString*)host protocol:(NSString*)protocol apiVersion:(NSString*)apiVersion
      applicationKey:(NSString*)applicationKey;



/**
 
 リクエストタイムアウト秒（デフォルト:10）
 
 */
@property int requestTimeInterval;


/**
 
 認証時のユーザーIDを指定します。
 
 @param uid 外部で使用しているアカウントID
 
*/
-(void)setAuthUid:(NSString*)uid;


/**
 
 認証時のユーザーIDとトークンを指定します。
 
 @param uid 外部で使用しているアカウントID
 @param token 外部で使用しているアカウントトークン
 
*/
-(void)setAuthUid:(NSString*)uid token:(NSString*)token;



/**
 
 APIサーバーよりコンテンツ情報を取得します。
 
 @param external 外部コンテンツ情報
 @param block ブロック
 
 NSDictionary response:{"content"=>StampContent, "status"=>StampStatus}。
 NSError error:エラー情報を返します。ない場合はnil
 
*/
-(void)contentWithBlock:(NSDictionary*)external block:(void(^)(NSDictionary* response, NSError *error ))block;


/**
 
 APIサーバーからスタンプグループ情報とスタンプ利用履歴を取得し、ローカルDBを上書きします。
 
 @param content_id コンテンツID
 @param block ブロック
 
 NSDictionary groups_response:{"groups"=>StampGroupのNSArray配列（通信エラー発生時はローカル情報を返します。）,"status"=>StampStatus}。オフライン取得時はstatus要素なし。
 NSError groups_error:通信エラー及びレスポンスエラーの情報を返します。ない場合はnil
 NSDictionary histories_response:{"histories"=>押印情報のNSArray配列(通信エラー発生時はローカル情報を返します。),"status"=>StampStatus}。オフライン取得時はstatus要素なし。
 NSError histories_error:通信エラー及びレスポンスエラーの情報を返します。ない場合はnil
 
 */

-(void)groupsAndHistoriesWithBlock:(int)content_id block:(void(^)(NSDictionary* groups_response, NSError *groups_error, NSDictionary* histories_response, NSError *histories_error ))block;

/**
 
 ローカルDBよりコンテンツのスタンプグループ情報と押印情報を取得します。
 
 @param content_id コンテンツID
 @return NSDictionary {"groups"=>StampGroupのNSArray配列, "histories"=>押印情報のNSArray配列}
 
 */
-(NSDictionary*)localGroupsAndHistories:(int)content_id;


/**
 
 APIサーバーよりスタンプグループ情報を取得し、ローカルDBを上書きします。
 
 スタンプ画像が未取得の時はスタンプ画像の取得も同時に行います。
 
 @param content_id コンテンツID
 @param block ブロック
 
     NSDictionary response:{"groups"=>StampGroupのNSArray配列（通信エラー発生時はローカル情報を返します。）, "status"=>StampStatus}。オフライン取得時はstatus要素なし。
     NSError error:エラー情報を返します。ない場合はnil
 
*/
-(void)groupsWithBlock:(int)content_id block:(void(^)(NSDictionary* response, NSError *error))block;

/**
 
 ローカルDBよりスタンプグループ情報を取得します。
 
 @param content_id コンテンツID
 @return StampGroupのNSArray配列
 
 */
-(NSArray*)localGroups:(int)content_id;

/**
 
 スタンプグループ情報をAPIサーバーより取得した取得した日時を返します。
 
 @param content_id コンテンツID
 @return スタンプグループ情報をAPIサーバーより取得した日時
 
 */
-(double)hasGroups:(int) content_id;

/**
 
 APIサーバーより押印情報を取得し、ユーザーのローカルDBを上書きします。
 
 オフラインなどにより、ローカルの押印リクエストが完了していない場合、オフライン時の押印情報がローカルから無くなります。
 押印リクエストが完了後に実行することで最新の利用情報が取得できます。
 
 スタンプ画像が未取得の時はスタンプ画像の取得も同時に行います。
 
 @param content_id 取得するコンテンツID
 
 @param block ブロック
 
    NSDictionary response: {"histories"=>押印情報のNSArray配列(通信エラー発生時はローカル情報を返します。), "status"=>StampStatus}。オフライン取得時はstatus要素なし。
    NSError error:エラー情報を返します。ない場合はnil
 
*/
-(void)historiesWithBlock:(int)content_id block:(void(^)(NSDictionary* response, NSError *error ))block;




/**
 
 ローカルDBよりユーザーの押印情報を取得します。
 
 @param content_id 取得するコンテンツID
 
 @return 押印情報のNSArray配列
 
 */
-(NSArray*)localHistories:(int)content_id;



/**
 
 指定したユーザーの押印情報をAPIサーバーより取得した日時を返します。
 
 @param content_id コンテンツID
 @return 押印情報をAPIサーバーより取得した日時
 
 */
-(double)hasHistories:(int) content_id;

/**
 
 現在のユーザー押印情報をAPIサーバーより取得した日時を返します。
 
 @param content_id コンテンツID
 @param uid ユーザーID
 
 @return 押印情報をAPIサーバーより取得した日時
 
*/
-(double)hasHistories:(int) content_id uid:(NSString*)uid;

/**
 
 ローカルDBよりユーザーの押印情報を削除します。
 
 @param content_id コンテンツID
 @param group_id グループID
 @param stamp_id スタンプID
 @param stamped_at スタンプ押印日付
 
 @return 押印情報のNSArray配列
 
 */
-(NSArray*)removeLocalHistory:(int)content_id group_id:(int)group_id stamp_id:(int)stamp_id stamped_at:(NSString*)stamped_at;


/**
 
 ローカルDBへユーザーの押印情報を登録
 
 @param stampedParams スタンプ情報
 @param image image情報
 
  @return 押印情報のNSArray配列
*/
-(NSArray*)addLocalHistory:(NSDictionary*)stampedParams withImage:(NSDictionary*)image;


/**
 
 スタンプグループ情報をロカルDBへ登録します
 
 スタンプ画像が未取得の時はスタンプ画像の取得も同時に行います。
 
 @param content_id コンテンツID
 @param groups スタンプグループ情報のオブジェクト配列
 
  @param block ブロック
    NSDictionary response: {"histories"=>StampGroupのNSArray配列}
    NSError error:エラー情報を返します。ない場合はnil
 
 */
-(void)setLocalGroupsWithBlock:(int)content_id groups:(NSArray *)groups block:(void (^)(NSDictionary *response, NSError *error))block;

/**
 
 ユーザーの押印情報をローカルDBへ保存します

 スタンプ画像が未取得の時はスタンプ画像の取得も同時に行います。
 
 @param content_id コンテンツID
 @param histories 押印情報のオブジェクト配列:[{"content_id"=>コンテンツID,"group_id"=>スタンプグループID,"stamp_id"=>スタンプID,"image_id"=>スタンプ画像ID,"image_url"=>スタンプ画像URL,"x"=>押印座標X,"y"=>押印座標Y,"width"=>画像幅,"height"=>画像高さ,"angle"=>画像角度,"stamped_at"=>押印日時}]
 
 @param block ブロック
    NSDictionary response: {"histories"=>押印情報のNSArray配列}
    NSError error:エラー情報を返します。ない場合はnil
 
 */
-(void)setLocalHistoriesWithBlock:(int)content_id histories:(NSArray *)histories block:(void (^)(NSDictionary *response, NSError *error))block;

/**
 
 APIサーバーへローカルに溜まっているスタンプ押印情報を送信します。
 
 @param block ブロック
 
 NSDictionary response: {"status"=>StampStatus}。
 NSError error:エラー情報を返します。ない場合はnil
 
 */
-(void)queueWithBlock:(void(^)(NSDictionary* response, NSError *error))block;

@end
