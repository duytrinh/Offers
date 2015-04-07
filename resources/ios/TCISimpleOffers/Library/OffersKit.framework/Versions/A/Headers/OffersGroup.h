//
//  OffersGroup.h
//  OffersKit
//
//  Created by Leonis&Co. on 2013/06/03.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import "OffersObject.h"
#import "OffersKitStatusCode.h"

@interface OffersGroup : OffersObject

#pragma mark - proparties

/// @name プロパティ

/**
 店舗情報の管理ID
 */
@property(nonatomic,assign) NSUInteger                      groupId;
/**
 店名
 */
@property(nonatomic,strong) NSString*                       name;
/**
 住所
 */
@property(nonatomic,strong) NSString*                       address;
/**
 経度
 */
@property(nonatomic,strong) NSString*                       latitude;
/**
 緯度
 */
@property(nonatomic,strong) NSString*                       longitude;
/**
 メールアドレス
 */
@property(nonatomic,strong) NSString*                       email;
/**
 Twitterアカウント
 */
@property(nonatomic,strong) NSString*                       twitter;
/**
 Facebook URL
 */
@property(nonatomic,strong) NSString*                       facebook;
/**
 WEBサイトURL
 */
@property(nonatomic,strong) NSString*                       website;
/**
 ブログURL
 */
@property(nonatomic,strong) NSString*                       blog;
/**
 店舗画像1のURL
 */
@property(nonatomic,strong) NSString*                       groupImage1UrlString;
/**
 店舗画像2のURL
 */
@property(nonatomic,strong) NSString*                       groupImage2UrlString;
/**
 電話番号
 */
@property(nonatomic,strong) NSString*                       phoneNumber;
/**
 営業時間を説明するテキスト
 */
@property(nonatomic,strong) NSString*                       openingHours;
/**
 最終更新日
 */
@property(nonatomic,strong) NSDate*                         updatedDate;
/**
 groupImage1UrlStringで示されたURLからダウンロードされ、キャッシュされたUIImage
 
 @see groupImage1UrlString loadImageWithCallbackBlock:
 */
@property(nonatomic,strong) UIImage*                        groupImage1;
/**
 groupImage2UrlStringで示されたURLからダウンロードされ、キャッシュされたUIImage
 
 @see groupImage2UrlString loadImageWithCallbackBlock:
 */
@property(nonatomic,strong) UIImage*                        groupImage2;


/// @name 店舗情報検索

/**
 管理IDから店舗名を取得
 
 指定された管理IDに対応する店舗情報を検索し、店舗名を取得します。
 
 @param groupId 店舗情報の管理ID
 
 @return メソッドが成功すると店舗名が返されます。失敗した場合はnilが返されます。
 */
+(NSString*)nameForId:(NSUInteger)groupId;

/**
 店舗名から対応する管理IDを取得
 
 指定された店舗名に対応する店舗情報を検索し、管理IDを取得します。
 
 @param name 店舗情報を検索するために使用する店舗名を指定します。
 
 @return メソッドが成功すると管理IDが返されます。失敗するとUINT_MAXが返されます。
 */
+(NSUInteger)idForName:(NSString*)name;

/**
 管理IDから店舗情報を取得
 
 指定された管理IDに対応する店舗情報を検索し、店舗名を取得します。
 
 @param groupId 店舗情報の管理ID
 
 @return メソッドが成功すると店舗情報が格納されたOffersGroupクラスのインスタンスが返されます。失敗した場合はnilが返されます。
 */
+(OffersGroup*)groupForId:(NSUInteger)groupId;

/// @name 店舗リスト取得

/**
 店舗リストの取得
 
 APIサーバから店舗リストをダウンロードします。
 
 @return メソッドが成功すると、OffersGroupクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
*/
+(NSArray*)groups;

/**
 店舗リストの取得
 
 APIサーバから店舗リストをダウンロードします。
 
 @param code 実行結果を表すステータスコードを格納するOffersKitStatusCode型変数へのポインタを指定します
 
 @return メソッドが成功すると、OffersGroupクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 
 @see OffersKitStatusCode

 */
+(NSArray*)groupsStatus:(OffersKitStatusCode*)code;

/**
 店舗リストの取得
 
 APIサーバから店舗リストをダウンロードします。メソッドが終了するとblockが実行されます。
 
 @param block メソッド終了時に呼び出されるブロックを指定します。このブロックはメソッドの実行結果をあらわすOffersKitStatusCode列挙のステータスコードとOffersGroupクラスのインスタンスが格納されたNSArrayを受け取ります。
 
 @see OffersKitStatusCode

 */
+(void)groupsWithCallbackBlock:(void(^)( OffersKitStatusCode code, NSArray* result ))block;

/**
 店舗リストの取得
 
 APIサーバまたはローカルキャッシュから店舗リストを取得します。
 
 @param onlineFlag 店舗リストを取得する際のAPIサーバへの接続の有無を指定します。真を指定するとAPIに接続します。偽を指定するとローカルにキャッシュされている店舗リストを返します。
 @return メソッドが成功すると、OffersGroupクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 */
+(NSArray*)groupsWithOnlineFlag:(BOOL)onlineFlag;

/**
 店舗リストの取得
 
 APIサーバまたはローカルキャッシュから店舗リストを取得します。
 
 @param onlineFlag 店舗リストを取得する際のAPIサーバへの接続の有無を指定します。真を指定するとAPIに接続します。偽を指定するとローカルにキャッシュされている店舗リストを返します。
 @param code 実行結果を表すステータスコードを格納するOffersKitStatusCode型変数へのポインタを指定します
 
 @return メソッドが成功すると、OffersGroupクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 
 @see OffersKitStatusCode

 */
+(NSArray*)groupsWithOnlineFlag:(BOOL)onlineFlag status:(OffersKitStatusCode*)code;                        

/**
 店舗リストの取得
 
 APIサーバまたはローカルキャッシュから店舗リストを取得します。メソッドが終了するとblockが実行されます。
 
 @param onlineFlag 店舗リストを取得する際にAPIサーバへの接続の有無を指定します。真を指定するとAPIに接続します。偽を指定するとローカルにキャッシュされている店舗リストを返します。
 @param block メソッド終了時に呼び出されるブロックを指定します。このブロックはOffersGroupクラスのインスタンスが格納されたNSArrayを受け取ります。 
 
 @see OffersKitStatusCode

 */
+(void)groupsWithOnlineFlag:(BOOL)onlineFlag
                  withCallbackBlock:(void(^)( OffersKitStatusCode code, NSArray* result ))block;

/// @name その他

/**
 ローカルキャッシュの最終更新日取得
 
 ローカルにキャッシュされた店舗情報の最終更新日を取得します。
 
 @return ローカルキャッシュの最終更新日取得
 */
+(NSDate*)lastUpdatedDate;

/**
 画像のキャッシュ
 
 groupImage1UrlStringおよびgroupImage2UrlStringを解析して画像をキャッシュします。
 成功するとgroupImage1とgroupImage2にそれぞれ対応するUIImageオブジェクトが設定されます。
 
 @param block 画像の読み込みが完了すると実行されるブロックを記述します。このブロックは画像の読み込み結果を表す真偽値resultを受け取ります。

 @see groupImage1UrlString
 @see groupImage2UrlString
 @see groupImage1 
 @see groupImage2

 */
-(void)loadImagesWithCallbackBlock:(void(^)( BOOL result ))block;

/// @name 以下のメソッドは、通常は使用しないでください。

/**
 店舗リストの取得
 
 APIサーバから店舗リストをダウンロードします。
 
 @param parameter APIに追加で与えるパラメータを指定します。
 
 @return メソッドが成功すると、OffersGroupクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 */
+(NSArray*)groupsWithParameter:(NSDictionary*)parameter;

/**
 店舗リストの取得
 
 APIサーバから店舗リストをダウンロードします。
 
 @param parameter APIに追加で与えるパラメータを指定します。
 @param code 実行結果を表すステータスコードを格納するOffersKitStatusCode型変数へのポインタを指定します
 
 @return メソッドが成功すると、OffersGroupクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。

 @see OffersKitStatusCode

 */
+(NSArray*)groupsWithParameter:(NSDictionary*)parameter status:(OffersKitStatusCode*)code;

/**
 店舗リストの取得
 
 APIサーバから店舗リストをダウンロードします。メソッドが終了するとblockが実行されます。
 
 @param parameter APIに追加で与えるパラメータを指定します。
 @param block メソッド終了時に呼び出されるブロックを指定します。このブロックはメソッドの実行結果をあらわすOffersKitStatusCode列挙のステータスコードとOffersGroupクラスのインスタンスが格納されたNSArrayを受け取ります。
 
 @see OffersKitStatusCode

 */
+(void)groupsWithParameter:(NSDictionary*)parameter
           withCallbackBlock:(void (^)(OffersKitStatusCode code, NSArray *))block
;

/**
 店舗リストの取得
 
 APIサーバから店舗リストをダウンロードします。メソッドが終了するとblockが実行されます。
 
 @param onlineFlag 店舗リストを取得する際のAPIサーバへの接続の有無を指定します。真を指定するとAPIに接続します。偽を指定するとローカルにキャッシュされている店舗リストを返します。
 @param parameter APIに追加で与えるパラメータを指定します。
 
 @return メソッドが成功すると、OffersGroupクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 */
+(NSArray*)groupsWithOnlineFlag:(BOOL)onlineFlag
                  withParameter:(NSDictionary*)parameter
;

/**
 店舗リストの取得
 
 APIサーバから店舗リストをダウンロードします。メソッドが終了するとblockが実行されます。
 
 @param onlineFlag 店舗リストを取得する際のAPIサーバへの接続の有無を指定します。真を指定するとAPIに接続します。偽を指定するとローカルにキャッシュされている店舗リストを返します。
 @param parameter APIに追加で与えるパラメータを指定します。
 @param code 実行結果を表すステータスコードを格納するOffersKitStatusCode型変数へのポインタを指定します
 
 @return メソッドが成功すると、OffersGroupクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 
 @see OffersKitStatusCode

 */
+(NSArray*)groupsWithOnlineFlag:(BOOL)onlineFlag
                  withParameter:(NSDictionary*)parameter
                         status:(OffersKitStatusCode*)code
;

/**
 店舗リストの取得
 
 APIサーバから店舗リストをダウンロードします。メソッドが終了するとblockが実行されます。
 
 @param onlineFlag 店舗リストを取得する際のAPIサーバへの接続の有無を指定します。真を指定するとAPIに接続します。偽を指定するとローカルにキャッシュされている店舗リストを返します。
 @param parameter APIに追加で与えるパラメータを指定します。
 @param block メソッド終了時に呼び出されるブロックを指定します。このブロックはメソッドの実行結果をあらわすOffersKitStatusCode列挙のステータスコードとOffersGroupクラスのインスタンスが格納されたNSArrayを受け取ります。
 
 @see OffersKitStatusCode

 */
+(void)groupsWithOnlineFlag:(BOOL)onlineFlag
              withParameter:(NSDictionary*)parameter
            withCallbackBlock:(void (^)(OffersKitStatusCode code, NSArray *))block
;

@end
