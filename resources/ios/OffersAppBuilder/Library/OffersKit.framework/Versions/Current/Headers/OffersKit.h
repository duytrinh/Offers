//
//  OffersKit.h
//  OffersKit
//
//  Created by Leonis & Co., Inc. on 2013/04/10.
//  Copyright (c) 2013年 Leonis & Co., Inc. All rights reserved.
//

#import "OffersObject.h"

#import "OffersKitStatusCode.h"

#import "OffersCoupon.h"
#import "OffersPreviewCoupon.h"

#import "OffersOffer.h"
#import "OffersRecommendation.h"

#import "OffersGroup.h"
#import "OffersCategory.h"
#import "OffersBlocking.h"

#import "OffersTemplate.h"

#import "OffersKitSyncType.h"

#import <Leonis/Leonis.h>

#pragma mark - OffersKit defination

/**
 OffersKitクラスはOffersKitフレームワークの根幹をなすサーバ接続情報等を管理します。
 シングルトンです。
 共有インスタンスの取得にはsharedInstanceメソッドを使用してください。
*/
@interface OffersKit : NSObject

#pragma mark - properties

/// APIサーバへの接続情報

/**
 APIサーバへの接続に使用するプロトコルが設定されます。

 setServerHost:protocol:apiVersion:applicationKey:で指定したプロトコルが設定されます。
 
 @see setServerHost:protocol:apiVersion:applicationKey:
 */
@property(nonatomic,readonly) NSString*               httpProtocol;

/**
 現在接続しているAPIサーバのホスト名が設定されます。

 setServerHost:protocol:apiVersion:applicationKey:で指定したホスト名が設定されます。

 @see setServerHost:protocol:apiVersion:applicationKey:
 */
@property(nonatomic,readonly) NSString*               applicationServerHost;

/**
 現在使用中のAPIバージョンが設定されます。
 
 setServerHost:protocol:apiVersion:applicationKey:で指定したAPIバージョンが設定されます。
 
 @see setServerHost:protocol:apiVersion:applicationKey:
 */
@property(nonatomic,readonly) NSString*               apiVersion;

/** 
 アプリケーションサーバのバージョンが設定されます。
 
 setServerHost:protocol:apiVersion:applicationKey:が成功するまでnilが設定されています。
 
 @see setServerHost:protocol:apiVersion:applicationKey:
*/
@property(nonatomic,readonly) NSString*               applicationServerVersion;

/** 
 アプリケーションサーバのステータスが設定されます。
 
 setServerHost:protocol:apiVersion:applicationKey:が成功するまでnilです。
 
 @see setServerHost:protocol:apiVersion:applicationKey:
 */
@property(nonatomic,readonly) NSUInteger              applicationServerStatus;

/**
 APIの各種情報が設定されます。

 setServerHost:protocol:apiVersion:applicationKey:が成功するまでnilとなります。
 
 @see setServerHost:protocol:apiVersion:applicationKey:
 */
@property(nonatomic,readonly) NSDictionary*           apiInfo;

/**
 サーバ定義のアプリ情報が設定されます。
 
 loadClientAppConfigメソッドで読み込まれます。loadClientAppConfigメソッドが失敗した場合はnilとなります。
 
 @see loadClientAppConfig
 */
@property(nonatomic,readonly) NSDictionary*           clientAppConfig;

/** 
 現在のAPI接続タイムアウトが設定されます。
 
 setRequestTimeoutInterval:メソッドで設定された値となります。デフォルト値は15秒です。
 
 @see setRequestTimeoutInterval:
 */
@property(nonatomic,readonly) double                  requestTimeoutInterval;

/** 
 サーバ時間計算時に使用される遅延時間です。
 
 @see setSystemDelay:
*/
@property(nonatomic,readonly) double                    systemDelay;

/** 
 プレビュー情報
 
 handleOpenURL:メソッドが成功すると、プレビュークーポン情報が格納されます。
 
 @see handleOpenURL:
*/
@property(nonatomic,strong) OffersPreviewCoupon*     previewCoupon;

/** 
 参照するサムネイル画像の内部管理名が設定されます。
*/
@property(nonatomic,strong) NSString*                 thumbnailImageName;

#pragma mark - methods

/** 
 OffersKit共有インスタンスを取得します。
 
 @return OffersKit共有インスタンス
*/
+(OffersKit*)sharedInstance;

/**
 OffersKitの初期化が完了しているかどうかを返します。
 
 @return OffersKitが初期化されているかどうかを示す真偽値
 */
-(BOOL)isInitialized;

/** 
 APIサーバへの接続情報を設定します。
 
 @param hostName 接続するAPIサーバのホスト名
 @param httpProtocolString APIサーバに接続する際に使用するプロトコル。通常はhttpsとなります。
 @param apiVersion 使用するWeb APIのバージョン番号を文字列で指定します。
 @param applicationKey APIサーバ管理者から発行されたアプリケーションキーを指定します。
 */
-(void)setServerHost:(NSString*)hostName protocol:(NSString*)httpProtocolString apiVersion:(NSString*)versionString applicationKey:(NSString*)applicationKey;

/** 
 ローカルデータベースが指定したサイズ上限範囲内か判定します。
 @return サイズ上限を超過したかどうかを示す真偽値を返します。真ならば超過、偽ならば範囲内です。
 */
-(BOOL)isLocalDatabaseSizeOver;

/**
 リクエストタイムアウト設定
 
 リクエストタイムアウトまでの時間を設定します。
 
 @param requestTimeoutInterval リクエストタイムアウトまでの秒数を指定します。
 */
-(void)setRequestTimeoutInterval:(double)requestTimeoutInterval;

/**
 バックグラウンド同期設定
 
 @param interval 同期間隔を指定します。
 @param syncType 同期方法を指定します。現在は、OffersKitSyncTypeSubmitを指定してください。
 */
-(void)setBackgroundSyncTimeInterval:(NSTimeInterval)interval syncType:(OffersKitSyncType)syncType;

/*
 クーポンシステム標準時間からの遅延時間を設定します。
 デフォルト値は0.0fです。不要なら、変更なしで問題ありません。
 
 @param systemDelay クーポンシステム標準時間からの遅延時間を秒単位で指定します。
 
 @see systemDelay
 */
-(void)setSystemDelay:(double)systemDelay;

/** 
 OffersKitの動作開始
 
 @return OffersKitが正常に動作を開始したかどうかが返されます。
 */
-(BOOL)start;

/**
 OffersKitのリセット
 
 OffersKitが生成したキャッシュデータをリセットして初期状態に戻します。
 
 @return リセット成功の有無を表す真偽値です。真ならば成功、偽ならば失敗です。
 */
-(BOOL)resetAll;

/** 
 サーバ定義のアプリ設定
 
 APIサーバにアクセスして、現在サーバ上で定義されているアプリの設定を読み込みます。
 ただし、初期化時に適切なアプリケーションキーを設定していないと失敗します。
 
 @return アプリ設定の読み込み状態を表す真偽値です。真ならば成功、偽ならば失敗です。
 */
-(BOOL)loadClientAppConfig;

/** 
 クーポンシステムにおける現在時刻
 
 クーポンシステムにおける現在時刻を取得します。この時刻は、オンライン時は端末に依存しないAPIサーバ準拠の時刻となります。
 戻り値がnilのときは使用できません。
 
 @return クーポンシステムにおける現在時刻
 */
-(NSDate*)couponSystemDate;

/**
 アクセストークンのリクエスト
 
 サーバとの間で認証処理を行いアクセストークンを新規に取得します。
 
 @return メソッドの実行結果を示す結果コードです。
 @see authenticationToken
 */
-(OffersKitStatusCode)requestAuthenticationToken;

/**
 アクセストークンのリクエスト
 
 サーバとの間で認証処理を行いアクセストークンをリクエストします。
 
 @param block APIサーバからのレスポンスを受信したあとで呼ばれる処理を記述します。
 @see authenticationToken
 */
-(void)requestAuthenticationTokenWithCallbackBlock:(void(^)(OffersKitStatusCode code))block;

/**
 現在のアクセストークン確認

 現在登録されているAPIサーバへのアクセストークンを取得します。
 
 @return アクセストークン文字列
 @see requestAuthenticationToken requestAuthenticationTokenWithCallbackBlock:
 */
-(NSString*)authenticationToken;

/**
 ネットワーク接続状態の確認
 
 @return ネットワーク接続状態を表す真偽値です。真ならばネットワーク接続状態、偽ならば切断されています。
 */
-(BOOL)isOnline;

/**
 対応するURLスキームを開き、APIサーバからプレビュークーポン情報受信
 
 APIサーバから受信したプレビュークーポン情報は正しく処理されるとpreviewCouponプロパティに設定されます。失敗した場合や、このメソッドが呼ばれる前はpreviewCouponプロパティはnilです。

 @param url 事前にAPIサーバに登録されたURLスキームが指定されたURL
 @return ただしく処理されたかどうかを示す真偽値です。真ならば成功、偽ならば失敗です。
 @see previewCoupon
 */
-(BOOL)handleOpenURL:(NSURL*)url;

/**
 アプリケーションサーバ情報の読み込み
 
 @return Web APIのステータスコード
 */
-(OffersKitStatusCode)setApplicationServerInfo;

/** 
 追加サービス情報の登録
 
 @param externalInfo あらかじめAPIサーバで定義された追加情報のキーと値のペアで構成されたNSDictionary
 */
-(void)setExternalInfo:(NSDictionary*)externalInfo;

/**
 追加サービス情報の登録
 
 @param externalInfo あらかじめAPIサーバで定義された追加情報のキーと値のペアで構成されたNSDictionary
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 */
-(void)setExternalInfo:(NSDictionary*)externalInfo withCallbackBlock:(void(^)( OffersKitStatusCode code ))block;

/**
 追加ユーザ情報の単一登録
 
 ユーザごとの追加情報を登録します。この情報は同じキーが指定されると上書きされます。
 
 このメソッドは内部的にリソース名"OffersExtensions"を使用します。
 
 @param value 追加ユーザ情報の値を指定します。
 @param key 追加ユーザ情報の値を設定するキーを指定します。
 */
-(void)setUserExtensionValue:(NSString*)value forKey:(NSString*)key;

/**
 追加ユーザ情報の単一登録
 
 ユーザごとの追加情報を登録します。この情報は同じリソース名、同じキーが指定されると上書きされます。
 
 @param value 追加ユーザ情報の値を指定します。
 @param key 追加ユーザ情報の値を設定するキーを指定します。
 @param resourceName　キーと値のペアを設定するセクション名を指定します
 */
-(void)setUserExtensionValue:(NSString*)value forKey:(NSString*)key withResourceName:(NSString*)resourceName;

/**
 追加ユーザ情報の複数一括登録
 
 @param extentions 追加ユーザ情報のキーと値のペアで構成されたNSDictionaryをネストしたリソース名ごとのNSDictionaryを指定します
 example 
 @{
    @"resource_name1": @{
        @"key1": @"value1",
        @"key2": @"value2",
    },
    @"resource_name2": @{
        @"key1": @"value1"
    }
 }
 
 @return extensionsが正しくない場合、APIサーバとは通信せず偽が返されます。

 */
-(BOOL)setUserExtensions:(NSDictionary*)extentions;

/**
 追加ユーザ情報の単一登録
 
 ユーザごとの追加情報を登録します。この情報は同じキーが指定されると上書きされます。
 
 このメソッドは内部的にリソース名"OffersExtensions"を使用します。
 
 @param value 追加ユーザ情報の値を指定します。
 @param key 追加ユーザ情報の値を設定するキーを指定します。
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 */
-(void)setUserExtensionValue:(NSString*)value forKey:(NSString*)key withCallbackBlock:(void(^)(OffersKitStatusCode code))block;

/**
 追加ユーザ情報の単一登録
 
 ユーザごとの追加情報を登録します。この情報は同じリソース名、同じキーが指定されると上書きされます。
 @param value 追加ユーザ情報の値を指定します。
 @param key 追加ユーザ情報の値を設定するキーを指定します。
 @param resourceName　キーと値のペアを配置するセクション名を指定します
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 */
-(void)setUserExtensionValue:(NSString*)value forKey:(NSString*)key withResourceName:(NSString*)resourceName withCallbackBlock:(void(^)(OffersKitStatusCode code))block;

/**
 追加ユーザ情報の複数一括登録
 
 @param extensions 追加ユーザ情報のキーと値のペアで構成されたNSDictionaryをネストしたリソース名ごとのNSDictionaryを指定します。指定方法の例はsetUserExtensions:をご参照ください。
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。

 @return extensionsが正しくない場合、APIサーバとは通信せず偽が返されます。このときは、blockも実行されません。
 
 */
-(BOOL)setUserExtensions:(NSDictionary*)extentions withCallbackBlock:(void(^)( OffersKitStatusCode code ))block;

/**
 追加ユーザ情報の単一削除
 
 ユーザごとの追加情報を削除します。
 
 このメソッドは内部的にリソース名"OffersExtensions"を対象として削除処理を行います。
 
 @param key 追加ユーザ情報の値を設定するキーを指定します。
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 */
-(void)removeUserExtensionForKey:(NSString*)key;

/**
 追加ユーザ情報の単一削除
 
 @param key 追加ユーザ情報の値を設定するキーを指定します。
 @param resourceName　キーと値のペアを配置するセクション名を指定します
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 */
-(void)removeUserExtensionForKey:(NSString*)key withResourceName:(NSString*)resourceName;

/**
 追加ユーザ情報の複数一括削除
 
 @param extensions 追加ユーザ情報のキーで構成されるNSArrayをネストしたリソース名ごとのNSDictionaryを指定します。
 example
 @{
    @"resource_name1": @[
        @"key1",
        @"key2"
    ],
    @"resource_name2": @{
        @"key1"
    ]
 }

 @return extensionsが正しくない場合、APIサーバとは通信せず偽が返されます。このときは、blockも実行されません。

 */
-(BOOL)removeUserExtensions:(NSDictionary*)extensions;

/**
 追加ユーザ情報の単一削除
 
 ユーザごとの追加情報を削除します。
 
 このメソッドは内部的にリソース名"OffersExtensions"を対象として削除処理を行います。
 
 @param key 追加ユーザ情報の値を設定するキーを指定します。
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 */
-(void)removeUserExtensionForKey:(NSString*)key withCallbackBlock:(void(^)(OffersKitStatusCode code))block;

/**
 追加ユーザ情報の単一削除
 
 @param key 追加ユーザ情報の値を設定するキーを指定します。
 @param resourceName　キーと値のペアを配置するセクション名を指定します
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 */
-(void)removeUserExtensionForKey:(NSString*)key withResourceName:(NSString*)resourceName withCallbackBlock:(void(^)(OffersKitStatusCode code))block;

/**
 追加ユーザ情報の複数一括削除
 
 @param extensions 追加ユーザ情報のキーで構成されるNSArrayをネストしたリソース名ごとのNSDictionaryを指定します。指定方法の例はremoveUserExtensions:をご参照ください。
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 
 @return extensionsが正しくない場合、APIサーバとは通信せず偽が返されます。このときは、blockも実行されません。

 */
-(BOOL)removeUserExtensions:(NSDictionary*)extensions withCallbackBlock:(void(^)(OffersKitStatusCode code))block;

/**
 画像のURL変換
 
 APIサーバで定義されているサムネイル名とそのサイズに対応したサムネイル画像のURLを生成します。
 
 @param originalImageUrlString オリジナルとなる画像URLの文字列表現
 @param name APIサーバで定義されているサムネイル名
 @return nameで指定されたサムネイル画像のURL文字列
 */
+(NSString*)convertImageUrlString:(NSString*)originalImageUrlString withThumbnailName:(NSString*)name;

@end