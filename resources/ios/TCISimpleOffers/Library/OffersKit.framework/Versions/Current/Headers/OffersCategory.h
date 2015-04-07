//
//  OffersCategory.h
//  OffersKit
//
//  Created by Leonis&Co. on 2013/05/31.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import "OffersObject.h"
#import "OffersKitStatusCode.h"

// 日本語の表現としてはジャンル、またはカテゴリなどになります。

@interface OffersCategory : OffersObject

#pragma mark - proparties

/// @name プロパティ

/// 管理ID
@property(nonatomic,assign) NSUInteger                      categoryId;

/// ジャンル名
@property(nonatomic,strong) NSString*                       name;

/// ジャンルの最終更新日
@property(nonatomic,strong) NSDate*                         updatedDate;

#pragma mark - class methods

/// @name ジャンル情報検索

/**
 ジャンル管理IDからジャンル名を取得
 
 指定されたジャンル管理IDに対応するジャンル情報を検索し、ジャンル名を取得します。
 
 @param categoryId ジャンル情報の管理ID
 
 @return メソッドが成功するとジャンル名が返されます。失敗した場合はnilが返されます。
 */
+(NSString*)nameForId:(NSUInteger)categoryId;

/**
 ジャンル名から対応するジャンル管理IDを取得
 
 指定されたジャンル名に対応するジャンル情報を検索し、ジャンル管理IDを取得します。
 
 @param name ジャンル情報を検索するために使用するジャンル名を指定します。
 
 @return メソッドが成功すると管理IDが返されます。失敗するとUINT_MAXが返されます。
 */
+(NSUInteger)idForName:(NSString*)name;                                                                                                 

/// @name ジャンルリスト取得

/**
 ジャンルリストの取得
 
 APIサーバからジャンルリストをダウンロードします。
 
 @return メソッドが成功すると、OffersCategoryクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 */
+(NSArray*)categories;

/**
 ジャンルリストの取得
 
 APIサーバからジャンルリストをダウンロードします。
 
 @param code 実行結果を表すステータスコードを格納するOffersKitStatusCode型変数へのポインタを指定します
 
 @return メソッドが成功すると、OffersCategoryクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 */
+(NSArray*)categoriesStatus:(OffersKitStatusCode*)code;

/**
 ジャンルリストの取得
 
 APIサーバからジャンルリストをダウンロードします。メソッドが終了するとblockが実行されます。
 
 @param block メソッド終了時に呼び出されるブロックを指定します。このブロックはメソッドの実行結果をあらわすOffersKitStatusCode列挙のステータスコードとOffersCategoryクラスのインスタンスが格納されたNSArrayを受け取ります。
 */
+(void)categoriesWithCallbackBlock:(void(^)( OffersKitStatusCode code, NSArray* categories ))block;

/**
 ジャンルリストの取得
 
 APIサーバまたはローカルキャッシュからジャンルリストを取得します。
 
 @param onlineFlag ジャンルリストを取得する際のAPIサーバへの接続の有無を指定します。真を指定するとAPIに接続します。偽を指定するとローカルにキャッシュされているジャンルリストを返します。
 @return メソッドが成功すると、OffersCategoryクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 */
+(NSArray*)categoriesWithOnlineFlag:(BOOL)onlineFlag;

/**
 ジャンルリストの取得
 
 APIサーバまたはローカルキャッシュからジャンルリストを取得します。
 
 @param onlineFlag ジャンルリストを取得する際のAPIサーバへの接続の有無を指定します。真を指定するとAPIに接続します。偽を指定するとローカルにキャッシュされているジャンルリストを返します。
 @param code 実行結果を表すステータスコードを格納するOffersKitStatusCode型変数へのポインタを指定します

 @return メソッドが成功すると、OffersCategoryクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 */
+(NSArray*)categoriesWithOnlineFlag:(BOOL)onlineFlag status:(OffersKitStatusCode*)code;

/**
 ジャンルリストの取得
 
 APIサーバまたはローカルキャッシュからAPIサーバからジャンルリストをダウンロードします。メソッドが終了するとblockが実行されます。
 
 @param onlineFlag ジャンルリストを取得する際のAPIサーバへの接続の有無を指定します。真を指定するとAPIに接続します。偽を指定するとローカルにキャッシュされているジャンルリストを返します。
 @param block メソッド終了時に呼び出されるブロックを指定します。このブロックはメソッドの実行結果をあらわすOffersKitStatusCode列挙のステータスコードとOffersCategoryクラスのインスタンスが格納されたNSArrayを受け取ります。
 */
+(void)categoriesWithOnlineFlag:(BOOL)onlineFlag withCallbackBlock:(void(^)( OffersKitStatusCode code, NSArray* categories ))block;

/// @name その他のメソッド

/**
 ローカルキャッシュの最終更新日取得
 
 ローカルにキャッシュされたジャンル情報の最終更新日を取得します。
 
 @return ローカルキャッシュの最終更新日取得
 */
+(NSDate*)lastUpdatedDate;

/// @name 以下のメソッドは通常は使用しないでください。

/**
 ジャンルリストの取得
 
 APIサーバからジャンルリストを取得します。メソッドが終了するとblockが実行されます。
 
 @param parameter APIに追加で与えるパラメータを指定します。
 
 @return メソッドが成功すると、OffersCategoryクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 */
+(NSArray*)categoriesWithParameter:(NSDictionary*)parameter;

/**
 ジャンルリストの取得
 
 APIサーバあるいはローカルキャッシュからジャンルリストを取得します。メソッドが終了するとblockが実行されます。
 
 @param onlineFlag 店舗リストを取得する際のAPIサーバへの接続の有無を指定します。真を指定するとAPIに接続します。偽を指定するとローカルにキャッシュされているジャンルリストを返します。
 @param parameter APIに追加で与えるパラメータを指定します。
 @param code 実行結果を表すステータスコードを格納するOffersKitStatusCode型変数へのポインタを指定します
 
 @return メソッドが成功すると、OffersCategoryクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 */
+(NSArray*)categoriesWithOnlineFlag:(BOOL)onlineFlag withParameter:(NSDictionary *)parameter status:(OffersKitStatusCode*)code;

/**
 ジャンルリストの取得
 
 APIサーバからジャンルリストを取得します。メソッドが終了するとblockが実行されます。
 
 @param parameter APIに追加で与えるパラメータを指定します。
 @param block メソッド終了時に呼び出されるブロックを指定します。このブロックはメソッドの実行結果をあらわすOffersKitStatusCode列挙のステータスコードとOffersCategoryクラスのインスタンスが格納されたNSArrayを受け取ります。

 */
+(void)categoriesWithParameter:(NSDictionary*)parameter withCallbackBlock:(void (^)(OffersKitStatusCode code, NSArray * categories))block;

/**
 ジャンルリストの取得
 
 APIサーバあるいはローカルキャッシュからジャンルリストを取得します。メソッドが終了するとblockが実行されます。
 
 @param onlineFlag 店舗リストを取得する際のAPIサーバへの接続の有無を指定します。真を指定するとAPIに接続します。偽を指定するとローカルにキャッシュされているジャンルリストを返します。
 @param parameter APIに追加で与えるパラメータを指定します。
 
 @return メソッドが成功すると、OffersCategoryクラスのインスタンスが格納されたNSArrayが返されます。失敗したときはnilが返されます。
 */
+(NSArray*)categoriesWithOnlineFlag:(BOOL)onlineFlag withParameter:(NSDictionary*)parameter;

/**
 ジャンルリストの取得
 
 APIサーバあるいはローカルキャッシュから店舗リストを取得します。メソッドが終了するとblockが実行されます。
 
 @param onlineFlag ジャンルリストを取得する際のAPIサーバへの接続の有無を指定します。真を指定するとAPIに接続します。偽を指定するとローカルにキャッシュされているジャンルリストを返します。
 @param parameter APIに追加で与えるパラメータを指定します。
 @param block メソッド終了時に呼び出されるブロックを指定します。このブロックはメソッドの実行結果をあらわすOffersKitStatusCode列挙のステータスコードとOffersCategoryクラスのインスタンスが格納されたNSArrayを受け取ります。
 */
+(void)categoriesWithOnlineFlag:(BOOL)onlineFlag withParameter:(NSDictionary*)parameter withCallbackBlock:(void (^)(OffersKitStatusCode code, NSArray *categories))block;

@end
