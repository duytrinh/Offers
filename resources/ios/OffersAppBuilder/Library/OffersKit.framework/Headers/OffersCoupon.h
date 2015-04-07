//
//  OffersCoupon.h
//  OffersKit
//
//  Created by Leonis&Co. on 2013/05/31.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import "OffersObject.h"
#import "OffersCouponType.h"
#import "OffersKitStatusCode.h"

#define OffersCouponAvailableDateFinished                          (0.0f)
#define OffersCouponAvailableDateUnlimited                         (-999.999f)
#define OffersCouponAvailableDateError                             (-666.666f)

/** 配信ステータスの列挙
 */
typedef enum OffersCouponDeliveryStatusEnum {
    OffersCouponDeliveryStatusUnknown,             // 詳細情報が取り出されていないため、判別できませんでした。
    OffersCouponDeliveryStatusDelivery,            // 現在配信中です
    OffersCouponDeliveryStatusBeforeDelivery,      // 配信前です
    OffersCouponDeliveryStatusFinishedDelivery,    // 配信終了しています
} OffersCouponDeliveryStatus;


/**
 APIサーバから配信されたクーポンの情報を管理するためのクラスです。
 */
@interface OffersCoupon : OffersObject
{
    BOOL            _viewed;
    NSString*       _description;
}

#pragma mark - properties

/// @name クーポンの主要なプロパティ

/// 管理ID
@property(nonatomic, assign)    NSUInteger          couponId;                   
/// クーポンのタイトル
@property(nonatomic, strong)    NSString*           title;
/// クーポンの説明文(簡単な一文)が設定されます。この値は一覧表示などに使用されます。
@property(nonatomic, strong)    NSString*           mainDescription;
/// サムネイル画像のURLが文字列で設定されます。この値は主に一覧表示で表示する画像のダウンロードに使用します。
@property(nonatomic, strong)    NSString*           thumbnailImageUrlString;
/// クーポンが属するジャンルが文字列形式で設定されます。この値を元にOffersCategoryクラスに問い合わせすることができます。
@property(nonatomic, strong)    NSString*           category;                   
/// クーポンのタイプが文字列形式で設定されます
@property(nonatomic, strong)    NSString*           couponType;
/// クーポンを使用可能な店舗のリストが設定されます。
@property(nonatomic, strong)    NSArray*            shops;
// キャンペーンクーポンの場合のみ設定されます。キャンペーンの情報が設定されます。
@property(nonatomic, strong)    NSDictionary*       campaign;
/// クーポンを部分よみとりしたときの情報の最終更新日が設定されます。
@property(nonatomic, strong)    NSDate*             partialUpdatedDate;
/// クーポン情報の最終更新日が設定されます。
@property(nonatomic, strong)    NSDate*             updatedDate;

/// @name クーポンの使用期間

/// クーポンの使用可能開始日が設定されます。
@property(nonatomic, strong)    NSDate*             availableFromDate;
/// クーポンの使用可能終了日が設定されます。
@property(nonatomic, strong)    NSDate*             availableToDate;

/// @name クーポンの数量、上限
/// クーポンの使用可能上限数が設定されます。 -(BOOL)hasAvailableLimitの結果がYESのときのみ参照してください。それ以外のときに設定される値は未定義です。
@property(nonatomic, strong)    NSNumber*           availableLimit;
/// クーポンの残り枚数が設定されます。 -(BOOL)hasAvailableLimitの結果がYESのときのみ参照してください。それ以外のときに設定される値は未定義です。
@property(nonatomic, strong)    NSNumber*           quantity;
/// クーポンの残り枚数が更新された日時が設定されます。-(BOOL)hasAvailableLimitの結果がYESのときのみ参照してください。それ以外のときに設定される値は未定義です。
@property(nonatomic, strong)    NSDate*             quantityUpdatedDate;

/// @name 割引情報

/// クーポン掲載商品の通常価格が設定されます。(省略時はnil)
@property(nonatomic, strong)    NSNumber*           usualPrice;                 
/// クーポン使用によって割引が発生する場合の割引後の価格(省略時はnil)
@property(nonatomic, strong)    NSNumber*           discountPrice;
/// クーポン使用用によって割引が発生する場合の割引率(省略時はnil)
@property(nonatomic, strong)    NSString*           discountRate;

/// @name テンプレート関連

/// このクーポンのレイアウトが定義されているテンプレート情報のIDが設定されます。
@property(nonatomic, assign)    NSUInteger          templateId;
/// このクーポンのレイアウトが定義されているテンプレート情報の更新日時が設定されます。
@property(nonatomic, strong)    NSDate*             templateUpdateDate;         

/// @name 使用情報

/// すでに使用されたかどうかが設定されます。
@property(nonatomic, assign)    BOOL                isUsed;
/// 使用された日時が設定されます。isUsedがNOならnilとなります。
@property(nonatomic, strong)    NSDate*             usedDate;
/// 再利用が可能かどうかが設定されます。
@property(nonatomic, assign)    BOOL                reusable;
/// 次回再利用可能となるまでの使用時刻から必要な時間(秒数)
@property(nonatomic, strong)    NSNumber*           reuseIntervalSeconds;
/// 次回再利用可能となる時刻
@property(nonatomic, strong)    NSDate*             scheduledReusableTime;

/// @name 詳細情報

/// クーポンが配信開始された日時が設定されます。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, strong)    NSDate*             deliveryFromDate;
/// クーポンが配信終了となる日時が設定されます。nilのときは配信終了予定がないことを示します。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, strong)    NSDate*             deliveryToDate;
/// クーポン画像のURLが文字列形式で設定されます。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, strong)    NSString*           imageUrlString;
/// クーポンの暗証番号の配列が設定されます。クーポンのタイプが『暗証番号』のときのみ設定されます。それ以外のときはnilです。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, strong)    NSArray*            secrets;
/// クーポンを使用した後に表示するメッセージが設定されます。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, strong)    NSString*           afterUseMessage;
/// 使用可能数超過時のときに表示するメッセージが設定されます。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, strong)    NSString*           overAvailableMessage;
/// 使用期間外のときに表示するメッセージが設定されます。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, strong)    NSString*           unavailableTermMessage;
/// クーポンが失効した時に表示するメッセージが設定されます。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, strong)    NSString*           statusInvalidMessage;
/// クーポンの上半分に表示するメッセージが設定されます。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, strong)    NSString*           topDescription;
/// クーポンの下半分に表示するメッセージが設定されます。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, strong)    NSString*           bottomDescription;
/// クーポンの利用条件が設定されます。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, strong)    NSString*           useConditionDescription;
/// 消し込みがオンライン必須かどうかを示す真偽値が設定されます。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, assign)    BOOL                onlineRequired;
/// 消し込み成功時に表示するべき画像のURLが設定されます。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, strong)    NSString*           applySuccessImageUrl;
/// 詳細がよみこまれたかどうかを示す真偽値が設定されます。
/// @see loadDetail loadDetailWithOnlineFlag: loadDetailWithCallbackBlock: loadDetailWithOnlineFlag:withCallbackBlock:
@property(nonatomic, assign)    BOOL                isLoadedDetail;

/// @name 画像

/// クーポンの画像イメージが設定されます。
@property(nonatomic, strong)    UIImage*            image;
/// クーポンのサムネイル画像イメージが設定されます。
@property(nonatomic, strong)    UIImage*            thumbnailImage;
/// クーポン消し込み成功時に表示するべき画像イメージが設定されます
@property(nonatomic, assign)    UIImage*            applySuccessImage;

#pragma mark - class methods

/// @name クーポン数量取得

/** APIサーバと通信して、現在配布中のクーポン総数を取得します。APIサーバと通信できなかった場合はローカルにキャッシュされているクーポンの総数を返します。
 
 @return 配布中のクーポン総数
*/
+(NSUInteger)couponsCount;

/** 現在配布中のクーポン総数をネットワーク接続の有無を指定して取得します。
 
 @param onlineFlag ネットワーク接続状態をあらわす真偽値を指定します。APIサーバと通信してクーポンの総数を取得する場合は真を指定します。ローカルにキャッシュされているクーポンの総数のみを取得する場合は偽を指定してください。
 @return 配布中のクーポン総数
 */
+(NSUInteger)couponsCountWithOnlineFlag:(BOOL)onlineFlag;

/** 現在ローカルにキャッシュされているクーポン総数を取得します。
 
 @return 未読クーポン総数
 */
+(NSUInteger)unreadCouponsCount;

/// @name クーポンリスト取得

/** 
 現在配布されているクーポンの一覧を取得
 
 まずAPIサーバへの接続を試行し、成功した場合はサーバから配布されているクーポンの一覧を取得します。
 APIサーバへの接続が成功しなかったときは、キャッシュされているクーポンの一覧を取得します。
 
 @return OffersCouponクラスのインスタンスが格納されたNSArray
 */
+(NSArray*)coupons;

/**
 現在配布されているクーポンの一覧を取得
 
 まずAPIサーバへの接続を試行し、成功した場合はサーバから配布されているクーポンの一覧を取得します。APIサーバへの接続が成功しなかったときは、キャッシュされているクーポンの一覧を取得します。
 
 @param code 実行結果を格納する変数へのポインタ。APIサーバからクーポン一覧を取得できたとき、およびキャッシュされているクーポンの一覧を取得したときOffersKitStatusSuccessが設定されます。
 
 @return OffersCouponクラスのインスタンスが格納された配列
 
 @see OffersKitStatusCode

 */
+(NSArray*)couponsStatusCode:(OffersKitStatusCode*)code;

/**
 現在配布されているクーポンの一覧を取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。
 
 @param onlineFlag APIサーバへの接続の有無を指定する真偽値を指定します。APIサーバに接続してクーポン一覧を取得する場合は真、キャッシュされているクーポン一覧を取得する場合は偽を指定します。
 
 @return OffersCouponクラスのインスタンスが格納された配列
 
 */
+(NSArray*)couponsWithOnlineFlag:(BOOL)onlineFlag;

/**
 現在配布されているクーポンの一覧を取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。
 
 @param onlineFlag APIサーバへの接続の有無を指定する真偽値を指定します。APIサーバに接続してクーポン一覧を取得する場合は真、キャッシュされているクーポン一覧を取得する場合は偽を指定します。
 @param code 実行結果を格納するOffersKitStatusCode変数へのポインタ。APIサーバからクーポン一覧を取得できたときもキャッシュされているクーポンの一覧を取得したときもOffersKitStatusSuccessが設定されます。

 @return OffersCouponクラスのインスタンスが格納された配列
 
 @see OffersKitStatusCode
 
 */
+(NSArray*)couponsWithOnlineFlag:(BOOL)onlineFlag status:(OffersKitStatusCode*)code;

/**
 現在配布されているクーポンの一覧を範囲指定で取得
 
 APIサーバへ接続してクーポンの一覧を取得します。
 
 @param range 取得するクーポンの範囲をNSRangeで指定します。
 
 @return OffersCouponクラスのインスタンスが格納された配列
 */
+(NSArray*)couponsWithRange:(NSRange)range;

/**
 現在配布されているクーポンの一覧を範囲指定で取得
 
 APIサーバへ接続してクーポンの一覧を取得します。
 
 @param range 取得するクーポンの範囲をNSRangeで指定します。
 @param code 実行結果を格納するOffersKitStatusCode変数へのポインタ。APIサーバからクーポン一覧を取得できたときもキャッシュされているクーポンの一覧を取得したときもOffersKitStatusSuccessが設定されます。
 
 @return OffersCouponクラスのインスタンスが格納された配列
 
 @see OffersKitStatusCode

 */
+(NSArray*)couponsWithRange:(NSRange)range status:(OffersKitStatusCode*)code;

/**
 現在配布されているクーポンの一覧を範囲指定で取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。
 
 @param onlineFlag APIサーバへの接続の有無を指定する真偽値を指定します。APIサーバに接続してクーポン一覧を取得する場合は真、キャッシュされているクーポン一覧を取得する場合は偽を指定します。
 @param range 取得するクーポンの範囲をNSRangeで指定します。
 
 @return OffersCouponクラスのインスタンスが格納された配列
 */
+(NSArray*)couponsWithOnlineFlag:(BOOL)onlineFlag withRange:(NSRange)range;

/**
 現在配布されているクーポンの一覧を範囲指定で取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。
 
 @param onlineFlag APIサーバへの接続の有無を指定する真偽値を指定します。APIサーバに接続してクーポン一覧を取得する場合は真、キャッシュされているクーポン一覧を取得する場合は偽を指定します。
 @param range 取得するクーポンの範囲をNSRangeで指定します。
 @param code 実行結果を格納するOffersKitStatusCode変数へのポインタ。APIサーバからクーポン一覧を取得できたときもキャッシュされているクーポンの一覧を取得したときもOffersKitStatusSuccessが設定されます。
 
 @return OffersCouponクラスのインスタンスが格納された配列
 
 @see OffersKitStatusCode

 */
+(NSArray*)couponsWithOnlineFlag:(BOOL)onlineFlag withRange:(NSRange)range status:(OffersKitStatusCode*)code;

/**
 
 現在配布されているクーポンの一覧取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。その後、ブロックを実行します。
 
 @param block メソッドの実行後に呼び出される処理を記述したブロックを指定します。このブロックは実行結果を表すステータスコードと配布されているクーポンの一覧を受け取ります。
 
 @see OffersKitStatusCode
 
 */
+(void)couponsWithCallbackBlock:(void(^)(OffersKitStatusCode code, NSArray* coupons ))block;

/**
 
 現在配布されているクーポンの一覧取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。その後、ブロックを実行します。
 
 @param onlineFlag APIサーバへの接続の有無を指定する真偽値を指定します。APIサーバに接続してクーポン一覧を取得する場合は真、キャッシュされているクーポン一覧を取得する場合は偽を指定します。
 @param block メソッドの実行後に呼び出される処理を記述したブロックを指定します。このブロックは実行結果を表すステータスコードと配布されているクーポンの一覧を受け取ります。
 
 @see OffersKitStatusCode
 
 */
+(void)couponsWithOnlineFlag:(BOOL)onlineFlag withCallbackBlock:(void(^)(OffersKitStatusCode code, NSArray* coupons ))block;

/**
 
 現在配布されているクーポンを範囲指定して取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。その後、ブロックを実行します。
 
 @param range 取得するクーポンの範囲をNSRangeで指定します。
 @param block メソッドの実行後に呼び出される処理を記述したブロックを指定します。このブロックは実行結果を表すステータスコードと配布されているクーポンの一覧を受け取ります。

 @see OffersKitStatusCode

 */
+(void)couponsWithRange:(NSRange)range withCallbackBlock:(void(^)(OffersKitStatusCode code, NSArray* coupons ))block;

/**
 
 現在配布されているクーポンを範囲指定して取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。その後、ブロックを実行します。

 @param onlineFlag APIサーバへの接続の有無を指定する真偽値を指定します。APIサーバに接続してクーポン一覧を取得する場合は真、キャッシュされているクーポン一覧を取得する場合は偽を指定します。
 @param range 取得するクーポンの範囲をNSRangeで指定します。
 @param block メソッドの実行後に呼び出される処理を記述したブロックを指定します。このブロックは実行結果を表すステータスコードと配布されているクーポンの一覧を受け取ります。
 
 @see OffersKitStatusCode

 */
+(void)couponsWithOnlineFlag:(BOOL)onlineFlag withRange:(NSRange)range withCallbackBlock:(void(^)(OffersKitStatusCode code, NSArray* coupons ))block;

/**
 
 クーポンの一覧を店舗指定で取得する

 APIサーバへ接続して指定した店舗に関連するクーポンの一覧を取得します。
 
 @param shops OffersGroupクラスのインスタンスの配列

 @return OffersCouponクラスのインスタンスが格納された配列

 @warning このメソッドはオンライン状態でなければ失敗します。

 */
+(NSArray*)couponsForShops:(NSArray*)shops;

/**
 
 クーポンの一覧を店舗指定で取得する
 
 APIサーバへ接続して指定した店舗に関連するクーポンの一覧を取得します。
 
 @param shops OffersGroupクラスのインスタンスの配列
 @param code 実行結果を格納するOffersKitStatusCode変数へのポインタ。APIサーバからクーポン一覧を取得できたときのみOffersKitStatusSuccessが設定されます。
 
 @return OffersCouponクラスのインスタンスが格納された配列
 
 @warning このメソッドはオンライン状態でなければ失敗します。
 
 */
+(NSArray*)couponsForShops:(NSArray*)shops status:(OffersKitStatusCode*)code;

/**
 
 クーポンの一覧を店舗指定で取得する
 
 APIサーバへ接続して指定した店舗に関連するクーポンの一覧を取得後、与えられたブロックを実行します。
 
 @param shops OffersGroupクラスのインスタンスの配列
 @param block block メソッドの実行後に呼び出される処理を記述したブロックを指定します。このブロックは実行結果を表すステータスコードと配布されているクーポンの一覧を受け取ります。
 
 @warning このメソッドはオンライン状態でなければ失敗します。
 
 */
+(void)couponsForShops:(NSArray*)shops withCallbackBlock:(void(^)( OffersKitStatusCode code, NSArray* coupons ))block;

/// @name キャンペーンクーポン

/** 
 
 キャンペーンクーポン数取得
 
 APIサーバと通信して、指定されたキャンペーンコードに対応して配布されるクーポンの総数を取得します。
 
 @param campaignCodes キャンペーンコードを指定します。入力されたキャンペーンコードに『-』などの区切り文字がある場合は、分割して配列の各要素に設定してください。
 @return キャンペーンコードに対応して配布されるクーポンの総数
 
 @warning このメソッドはオンライン状態でなければ失敗します。

 */
+(NSUInteger)campaignCouponsCountWithCampaignCode:(NSArray*)campaignCodes;

/**
 
 キャンペーンクーポンリスト取得
 
 APIサーバと通信して、指定されたキャンペーンコードに対応して配布されるクーポンのリストを取得します。
 
 @param campaignCodes キャンペーンコードを指定します。入力されたキャンペーンコードに『-』などの区切り文字がある場合は、分割して配列の各要素に設定してください。
 @param code 実行結果が格納されるOffersKitStatusCode変数のアドレスを指定します。
 
 @return キャンペーンコードに対応して配布されるクーポンのリスト。OffersCouponクラスのインスタンスが格納されたNSArrayが返されます。

 @warning このメソッドはオンライン状態でなければ失敗します。
 
 @see OffersKitStatusCode

*/
+(NSArray*)campaignCouponsWithCampaignCode:(NSArray*)campaignCodes status:(OffersKitStatusCode*)code;

/**
 
 キャンペーンクーポンリスト取得
 
 APIサーバと通信して、指定されたキャンペーンコードに対応して配布されるクーポンのリストを取得します。
 
 @param campaignCodes キャンペーンコードを指定します。入力されたキャンペーンコードに『-』などの区切り文字がある場合は、分割して配列の各要素に設定してください。
 @param block メソッド実行後に処理する内容を記述したブロックを指定します。このブロックはメソッドの実行結果を表すOffersKitStatusCodeとキャンペーンクーポンのリストを受け取ります。
 
 @warning このメソッドはオンライン状態でなければ失敗します。
 
 @see OffersKitStatusCode

 */
+(void)campaignCouponsWithCampaignCode:(NSArray*)campaignCodes withCallbackBlock:(void(^)(OffersKitStatusCode code, NSArray* coupons ))block;


/// @name 特殊配布クーポンのアンロック

/**
 
 限定配布クーポンのアンロック
 
 APIサーバと通信して、初回登録日時限定、ターゲットリストなどに登録されている、そのユーザに対して限定的に配布されているクーポンをアンロックし、対象クーポンの一覧を取得します。
 
 @param code 実行結果が格納されるOffersKitStatusCode変数のアドレスを指定します。
 
 @return 限定配布クーポンのリスト。OffersCouponクラスのインスタンスが格納されたNSArrayが返されます。
 
 @warning このメソッドはオンライン状態でなければ失敗します。
 
 @see OffersKitStatusCode
 
 */

+(NSArray*)unlockLimitedCouponsStatus:(OffersKitStatusCode*)code;

/**
 
 限定配布クーポンのアンロック
 
 APIサーバと通信して、初回登録日時限定、ターゲットリストなどに登録されている、そのユーザに対して限定的に配布されているクーポンをアンロックし、対象クーポンの一覧を取得します。
 
 @param block メソッド実行後に処理する内容を記述したブロックを指定します。このブロックはメソッドの実行結果を表すOffersKitStatusCodeと限定配布クーポンのリストを受け取ります。
 
 @warning このメソッドはオンライン状態でなければ失敗します。
 
 @see OffersKitStatusCode
 
 */

+(void)unlockLimitedCouponsWithCallbackBlock:(void(^)(OffersKitStatusCode code, NSArray* coupons ))block;

#pragma mark - instance methods

/// @name クーポンの状態確認

/**
 使用可能上限枚数の有無判定
 
 OffersCouponインスタンスに使用可能数上限枚数が設定されているかどうか判定します。
 
 @return クーポンの使用可能上限枚数が設定されていれば真、そうでなければ偽が返されます。
 
 */
-(BOOL)hasAvailableLimit;

/**
 配信状態取得
 
 @return クーポンの配信状態
 
 @see deliveryFromDate deliveryToDate
 */
-(OffersCouponDeliveryStatus)deliveryStatus;

/**
 使用可能期間内の判定
 
 @return クーポンが現在使用可能期間内であれば真、そうでなければ偽が返されます。
 
 @see availableFromDate availableToDate
 */
-(BOOL)isAvailable;

/**
 タイプ判別
 
 クーポンのタイプ(もぎとり／暗証番号)などを識別するためのコードを取得します。couponTypeプロパティの値は変更される可能性があるため、基本的にクーポンタイプの判別はこのメソッドを使用してください。
 
 @return クーポンタイプを表す、OffersCouponTypeCode列挙の値
 
 @see couponType
 @see OffersCouponTypeCode
 
 */
-(OffersCouponTypeCode)couponTypeCode;

/**
 使用可能残り時間取得
 
 使用可能残り時間を取得します。
 
 @return 使用可能残り時間。使用可能期間が終了しているときは0.0fが返ります。無限あるいは計測不能時は負数が返ります。
 */
-(NSTimeInterval)remainingTime;

/// @name 詳細情報読み込み

/**
 クーポン詳細情報の読み込み
 
 APIサーバに接続してクーポンの詳細情報を読み込みます。
 
 @return 読み込みが成功したときは真、失敗したときは偽が返ります。
 
 @see isLoadedDetail

 */
-(BOOL)loadDetail;

/**
 クーポン詳細情報の読み込み
 
 APIサーバへの接続の有無を指定してクーポンの詳細情報を読み込みます。

 @param onlineFlag 真を指定するとAPIサーバに接続して詳細情報を読み込みます。偽を指定するとローカルキャッシュから詳細情報を読み込みます。
 
 @return 読み込みが成功したときは真、失敗したときは偽が返ります。
 
 @see isLoadedDetail

 */
-(BOOL)loadDetailWithOnlineFlag:(BOOL)onlineFlag;

/**
 クーポン詳細情報の読み込み
 
 APIサーバからクーポンの詳細情報を読み込みます。
 
 @param code メソッドの詳細な実行結果を格納するOffersKitStatusCode型の変数へのポインタを指定します。
 
 @return 読み込みが成功したときは真、失敗したときは偽が返ります。
 
 @see isLoadedDetail
 @see OffersKitStatusCode

 */
-(BOOL)loadDetailStatus:(OffersKitStatusCode*)code;

/**
 クーポン詳細情報の読み込み
 
 APIサーバへの接続の有無を指定してクーポンの詳細情報を読み込みます。
 
 @param onlineFlag 真を指定するとAPIサーバに接続して詳細情報を読み込みます。偽を指定するとローカルキャッシュから詳細情報を読み込みます。
 @param code メソッドの詳細な実行結果を格納するOffersKitStatusCode型の変数へのポインタを指定します。
 
 @return 読み込みが成功したときは真、失敗したときは偽が返ります。
 
 @see isLoadedDetail
 @see OffersKitStatusCode

 */
-(BOOL)loadDetailWithOnlineFlag:(BOOL)onlineFlag status:(OffersKitStatusCode*)code;

/**
 クーポン詳細情報の読み込み
 
 APIサーバに接続してクーポンの詳細情報を読み込みます。
 
 @param block メソッドの実行後に処理する内容を記述したブロックを指定します。このブロックはメソッドの実行結果を表すステータスコードを受け取ります。
 
 @see isLoadedDetail
 @see OffersKitStatusCode

 */
-(void)loadDetailWithCallbackBlock:(void(^)( OffersKitStatusCode code ))block;

/**
 クーポン詳細情報の読み込み
 
 APIサーバへの接続の有無を指定してクーポンの詳細情報を読み込みます。
 
 @param onlineFlag 真を指定するとAPIサーバに接続して詳細情報を読み込みます。偽を指定するとローカルキャッシュから詳細情報を読み込みます。
 @param block メソッドの実行後に処理する内容を記述したブロックを指定します。このブロックはメソッドの実行結果を表すステータスコードを受け取ります。
 
 @see isLoadedDetail
 @see OffersKitStatusCode

 */
-(void)loadDetailWithOnlineFlag:(BOOL)onlineFlag withCallbackBlock:(void(^)( OffersKitStatusCode code ))block;

/// @name 既読設定

/**
 クーポンの既読状態を取得
 
 クーポンが現在既読状態となっているかどうかを取得します。
 
 @return 現在のクーポンの既読状態
 */
-(BOOL)alreadyRead;

/**
 クーポンを既読に設定
 
 クーポンを既読状態にします。
 */
-(void)setAlreadyRead;

/**
 クーポンが未読かどうか判定
 
 クーポンが現在未読状態かどうかを取得します。
 
 @return クーポンが未読ならばYES、既読ならばNOが返されます。
 */
-(BOOL)unread;

/// @name ライブ情報

/**
 クーポンライブ情報の読み込み
 
 APIサーバへの接続の有無を指定してクーポンの詳細情報を読み込みます。
 
 @param onlineFlag 真を指定するとAPIサーバに接続して詳細情報を読み込みます。偽を指定するとローカルキャッシュから詳細情報を読み込みます。
 @param block メソッドの実行後に処理する内容を記述したブロックを指定します。このブロックはメソッドの実行結果を表すステータスコードを受け取ります。
 
 @warning このメソッドは当該インスタンスについて、WebAPIの/coupons/:id/liveの実行結果として返される値のみを更新します。
 
 @see OffersKitStatusCode

 */
-(void)loadLiveDataWithCallbackBlock:(void(^)( OffersKitStatusCode code ) ) block;

/// @name クーポンの消し込み

/**
 クーポンの消し込み
 
 APIサーバと通信してクーポンを消し込みます。
 
 @return メソッドの実行結果をあらわすステータスコードが返されます。
 
 @see applyWithSecret: onlineRequired
 
 @warnning 暗証番号タイプのクーポンの消し込みにはapplyWithSecret:を使用してください。
 
 当該クーポンのonlineRequiredプロパティが真の場合、オフライン状態では消し込みできません。
 
 @see OffersKitStatusCode

 */
-(OffersKitStatusCode)apply;

/**
 暗証番号クーポンの消し込み
 
 APIサーバと通信して暗証番号クーポンを消し込みます。
 
 @param secret クーポン消し込みにに使用する暗証番号を文字列で指定します。
 
 @return メソッドの実行結果をあらわすステータスコードが返されます。
 
 @see apply onlineRequired
 
 @warnning 当該クーポンのonlineRequiredプロパティが真の場合、オフライン状態では消し込みできません。
 
 @see OffersKitStatusCode

 */
-(OffersKitStatusCode)applyWithSecret:(NSString*)secret;

/**
 クーポンの消し込み
 
 APIサーバと通信してクーポンを消し込みます。
 
 @param parameters WebAPIに送信するパラメータをNSDictionaryで指定します。
 
 @return メソッドの実行結果をあらわすステータスコードが返されます。
 
 @see onlineRequired
 
 @warnning 当該クーポンのonlineRequiredプロパティが真の場合、オフライン状態では消し込みできません。
 
 @see OffersKitStatusCode

 */
-(OffersKitStatusCode)applyWithParameters:(NSDictionary*)parameters;

/**
 消し込みに使用された暗証番号の取得
 
 @return 消し込みに使用された暗証番号が返されます。失敗したときはnilが返されます。
 
 @see applyWithSecret:
 */
-(NSString*)secretForApplied;

/**
 消し込み成功時の画像の有無
 
 @return 消し込み成功時に表示するべき画像がある場合は真が返されます。
 
 @see applySuccessImageUrl applySuccessImage
 */
-(BOOL)hasApplySuccessImage;

#pragma - mark other methods

/// @name その他のメソッド

/**
 クーポン情報の検索
 
 管理IDに対応するクーポン情報を取得します。
 
 @param couponId 管理ID
 
 @return 管理IDに対応するOffersCouponクラスのインスタンスが返されます。メソッドが失敗するとnilが返されます。
 */
+(OffersCoupon*)couponForId:(NSUInteger)couponId;

/**
 ローカルキャッシュの最終更新日取得
 
 ローカルにキャッシュされたクーポン情報の最終更新日を取得します。
 
 @return ローカルキャッシュの最終更新日取得
 */
+(NSDate*)lastUpdatedDate;

/**
 保持可能なクーポン数
 
 現在設定されているローカルキャッシュで保持可能なクーポンの最大数を取得します。
 
 @return ローカルキャッシュで保持可能なクーポンの最大数。無限のときは、UINT_MAXが返されます。
 */
+(NSUInteger)couponsCapacity;

/**
 保持可能なクーポン数設定
 
 ローカルキャッシュで保持可能なクーポンの最大数を設定します。
 
 @param capacity ローカルキャッシュで保持可能なクーポンの最大数を指定します。無限のときは、UINT_MAXを指定してください。
 */
+(void)setCouponsCapacity:(NSUInteger)capacity;

/**
 画像のキャッシュ
 
 imageUrlStringおよびthumbnailImageUrlStringを解析して画像をキャッシュします。
 成功するとimageとthumbnailImageにそれぞれ対応するUIImageオブジェクトが設定されます。
 
 @param block 画像の読み込みが完了すると実行されるブロックを記述します。このブロックは画像の読み込み結果を表す真偽値resultを受け取ります。
 
 @see imageUrlString
 @see thumbnailImageUrlString 
 @see image
 @see thumbnailImage
 
 */
-(void)loadImagesWithCallbackBlock:(void(^)( BOOL result ))block;


/// @name 以下のメソッドについては、通常は使用しないでください

/**
 現在配布されているクーポンの一覧を取得
 
 APIサーバへ接続してクーポンの一覧を取得します。
 
 @param parameter WebAPIに送信する追加のパラメータを指定します。
 
 @return OffersCouponクラスのインスタンスが格納された配列
 
 */
+(NSArray*)couponsWithParameter:(NSDictionary*)parameter;

/**
 現在配布されているクーポンの一覧を取得
 
 APIサーバへ接続してクーポンの一覧を取得します。
 
 @param parameter WebAPIに送信する追加のパラメータを指定します。
 @param code 実行結果を格納するOffersKitStatusCode変数へのポインタ。APIサーバからクーポン一覧を取得できたときもキャッシュされているクーポンの一覧を取得したときもOffersKitStatusSuccessが設定されます。
 
 @return OffersCouponクラスのインスタンスが格納された配列

 @see OffersKitStatusCode

 */
+(NSArray*)couponsWithParameter:(NSDictionary*)parameter status:(OffersKitStatusCode*)code;

/**
 現在配布されているクーポンの一覧を取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。
 
 @param onlineFlag APIサーバへの接続の有無を指定する真偽値を指定します。APIサーバに接続してクーポン一覧を取得する場合は真、キャッシュされているクーポン一覧を取得する場合は偽を指定します。
 @param parameter WebAPIに送信する追加のパラメータを指定します。
 
 @return OffersCouponクラスのインスタンスが格納された配列
 
 */
+(NSArray*)couponsWithOnlineFlag:(BOOL)onlineFlag withParameter:(NSDictionary*)parameter;

/**
 現在配布されているクーポンの一覧を取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。
 
 @param onlineFlag APIサーバへの接続の有無を指定する真偽値を指定します。APIサーバに接続してクーポン一覧を取得する場合は真、キャッシュされているクーポン一覧を取得する場合は偽を指定します。
 @param parameter WebAPIに送信する追加のパラメータを指定します。
 @param code 実行結果を格納するOffersKitStatusCode変数へのポインタ。APIサーバからクーポン一覧を取得できたときもキャッシュされているクーポンの一覧を取得したときもOffersKitStatusSuccessが設定されます。
 
 @return OffersCouponクラスのインスタンスが格納された配列
 
 @see OffersKitStatusCode
 
 */
+(NSArray*)couponsWithOnlineFlag:(BOOL)onlineFlag withParameter:(NSDictionary*)parameter status:(OffersKitStatusCode*)code;

/**
 現在配布されているクーポンの一覧を取得
 
 APIサーバへ接続してクーポンの一覧を取得します。
 
 @param parameter WebAPIに送信する追加のパラメータを指定します。
 @param block メソッドの実行後に呼び出される処理を記述したブロックを指定します。このブロックは実行結果を表すステータスコードと配布されているクーポンの一覧を受け取ります。

 @see OffersKitStatusCode

 */
+(void)couponsWithParameter:(NSDictionary*)parameter withCallbackBlock:(void(^)(OffersKitStatusCode code, NSArray* coupons ))block;

/**
 現在配布されているクーポンの一覧を取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。
 
 @param onlineFlag APIサーバへの接続の有無を指定する真偽値を指定します。APIサーバに接続してクーポン一覧を取得する場合は真、キャッシュされているクーポン一覧を取得する場合は偽を指定します。
 @param parameter WebAPIに送信する追加のパラメータを指定します。
 @param block メソッドの実行後に呼び出される処理を記述したブロックを指定します。このブロックは実行結果を表すステータスコードと配布されているクーポンの一覧を受け取ります。
 
 @see OffersKitStatusCode

 */
+(void)couponsWithOnlineFlag:(BOOL)onlineFlag withParameter:(NSDictionary*)parameter withCallbackBlock:(void(^)( OffersKitStatusCode code, NSArray* coupons ))block;

@end