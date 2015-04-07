//
//  OffersRecommendation.h
//  OffersKit
//
//  Created by Leonis&Co. on 2013/10/23.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import "OffersKit.h"

typedef enum OffersRecommendationActionTypeEnum {
    OffersRecommendationActionOpen,    // レコメンデーションの詳細が表示された
    OffersRecommendationActionView,    // レコメンデーションのURLを開いた
} OffersRecommendationActionType;

typedef enum OffersRecommendationTypeEnum {
    OffersRecommendationTypeCodeUnknown,
    OffersRecommendationTypeCodeUrl,    // URL
    OffersRecommendationTypeCodeArticle,    // 記事
} OffersRecommendationTypeCode;

@interface OffersRecommendation : OffersObject
{
    NSString*       _description;
}

#pragma mark - properties

/// @name レコメンデーションの主要なプロパティ

/// 管理ID
@property(nonatomic,assign) NSUInteger  recommendationId;

/// 名前
@property(nonatomic,strong) NSString*   name;

/// レコメンデーションの説明文(簡単な一文)が設定されます。
@property(nonatomic,strong) NSString*   mainDescription;

///レコメンデーションのタイプ
@property(nonatomic,strong) NSString*   type;

///レコメンデーションの内容
@property(nonatomic,strong) NSString*   content;

/// レコメンデーションするURL文字列(deprecated)
@property(nonatomic,strong) NSString*   urlString DEPRECATED_ATTRIBUTE;

/// 配信開始日時
@property(nonatomic,strong) NSDate*     deliveryFromDate;

/// 配信終了日時
@property(nonatomic,strong) NSDate*     deliveryToDate;

/// 既読の有無
@property(nonatomic,assign) BOOL        unread;

/// 更新日時
@property(nonatomic,strong) NSDate*     updatedDate;

/// レコメンデーションを部分よみとりしたときの情報の最終更新日が設定されます。
@property(nonatomic,strong) NSDate*     partialUpdatedDate;

/// 配布店舗
@property(nonatomic,strong) NSArray*    shops;

/// レコメンデーションのサムネイル画像
@property(nonatomic,strong) NSString*   thumbnailImageUrlString;
@property(nonatomic,strong) UIImage*    thumbnailImage;

/// レコメンデーションのオリジナルサイズの画像
/// インスタンスに対してloadDetailメソッドを実行し、詳細を読み込むと設定されます。
@property(nonatomic,strong) NSString*   imageUrlString;
@property(nonatomic,strong) UIImage*    image;

/// 詳細情報がすでに読み込まれているかどうか
@property(nonatomic,assign) BOOL        isLoadedDetail;

/**
 レコメンデーション一覧
 
 APIサーバからレコメンデーションの一覧を取得してOffersRecommendationインスタンスの配列を返します。
 
 @return レコメンデーション情報の配列
 */
+(NSArray*)recommendations;

/**
 レコメンデーション一覧
 
 APIサーバからレコメンデーションの一覧を取得してOffersRecommendationインスタンスの配列を返します。
 
 @param code メソッドの処理結果をあらわすステータスコードを格納するOffersKitStatusCode型の変数へのポインタ
 @return レコメンデーション情報の配列
 */
+(NSArray*)recommendationsWithStatus:(OffersKitStatusCode*)code;

/**
 
 現在配布されているレコメンデーションの一覧取得
 
 APIサーバへの接続の有無を指定してレコメンデーションの一覧を取得します。その後、ブロックを実行します。
 
 @param block メソッドの実行後に呼び出される処理を記述したブロックを指定します。このブロックは実行結果を表すステータスコードと配布されているレコメンデーションの一覧を受け取ります。
 
 @see OffersKitStatusCode
 
 */
+(void)recommendationsWithCallbackBlock:(void(^)(OffersKitStatusCode code, NSArray* recommendations ))block;

/**
 現在配布されているクーポンの一覧を取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。
 
 @param onlineFlag APIサーバへの接続の有無を指定する真偽値を指定します。APIサーバに接続してクーポン一覧を取得する場合は真、キャッシュされているクーポン一覧を取得する場合は偽を指定します。
 @param parameter WebAPIに送信する追加のパラメータを指定します。
 
 @return OffersRecommendationクラスのインスタンスが格納された配列
 
 */
+(NSArray*)recommendationsWithOnlineFlag:(BOOL)onlineFlag withParameter:(NSDictionary*)parameter;

/**
 現在配布されているクーポンの一覧を取得
 
 APIサーバへの接続の有無を指定してクーポンの一覧を取得します。
 
 @param onlineFlag APIサーバへの接続の有無を指定する真偽値を指定します。APIサーバに接続してクーポン一覧を取得する場合は真、キャッシュされているクーポン一覧を取得する場合は偽を指定します。
 @param parameter WebAPIに送信する追加のパラメータを指定します。
 @param code 実行結果を格納するOffersKitStatusCode変数へのポインタ。APIサーバからクーポン一覧を取得できたときもキャッシュされているクーポンの一覧を取得したときもOffersKitStatusSuccessが設定されます。
 
 @return OffersRecommendationクラスのインスタンスが格納された配列
 
 @see OffersKitStatusCode
 
 */
+(NSArray*)recommendationsWithOnlineFlag:(BOOL)onlineFlag withParameter:(NSDictionary*)parameter status:(OffersKitStatusCode*)code;

/**
 現在配布されているレコメンデーションの一覧を取得
 
 APIサーバへ接続してクーポンの一覧を取得します。
 
 @param parameter WebAPIに送信する追加のパラメータを指定します。
 @param block メソッドの実行後に呼び出される処理を記述したブロックを指定します。このブロックは実行結果を表すステータスコードと配布されているレコメンデーションの一覧を受け取ります。
 
 @see OffersKitStatusCode
 
 */
+(void)recommendationsWithParameter:(NSDictionary*)parameter withCallbackBlock:(void(^)(OffersKitStatusCode code, NSArray* recommendations ))block;

/**
 現在配布されているレコメンデーションの一覧を取得
 
 APIサーバへの接続の有無を指定してレコメンデーションの一覧を取得します。
 
 @param onlineFlag APIサーバへの接続の有無を指定する真偽値を指定します。APIサーバに接続してクーポン一覧を取得する場合は真、キャッシュされているレコメンデーション一覧を取得する場合は偽を指定します。
 @param parameter WebAPIに送信する追加のパラメータを指定します。
 @param block メソッドの実行後に呼び出される処理を記述したブロックを指定します。このブロックは実行結果を表すステータスコードと配布されているレコメンデーションの一覧を受け取ります。
 
 @see OffersKitStatusCode
 
 */
+(void)recommendationsWithOnlineFlag:(BOOL)onlineFlag withParameter:(NSDictionary*)parameter withCallbackBlock:(void(^)( OffersKitStatusCode code, NSArray* recommendations ))block;

/**
 レコメンデーションの詳細読み込み
 
 APIサーバからレコメンデーションの詳細を取得し、インスタンスに設定します。
 
 @param originalImageUrlString オリジナルとなる画像URLの文字列表現
 @param name APIサーバで定義されているサムネイル名
 @return メソッドの処理結果をあらわすステータスコード
 */
-(OffersKitStatusCode)loadDetail;

/**
 レコメンデーションに対するアクションの登録
 
 APIサーバからレコメンデーションの一覧を取得してOffersRecommendationインスタンスの配列を返します。
 
 @param code ステータスコードを格納するOffersKitStatusCode型の変数へのポインタ
 @return メソッドの処理結果をあらわすステータスコード
 */
-(OffersKitStatusCode)addAction:(OffersRecommendationActionType)actionType;

/**
 タイプ判別
 
 レコメンデーションのタイプ(URL／記事)などを識別するためのコードを取得します。typeプロパティの値は変更される可能性があるため、基本的にレコメンデーションタイプの判別はこのメソッドを使用してください。
 
 @return レコメンデーションタイプを表す、OffersRecommendationTypeCode列挙の値
 
 @see type
 @see OffersRecommendationTypeCode
 
 */
-(OffersRecommendationTypeCode)recommendationTypeCode;

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

/**
 未読レコメンデーション数の取得
 
 未読のレコメンデーション総数を取得します。
 
 @return 未読レコメンデーション総数
 */
+(NSUInteger)unreadRecommendationsCount;

/**
 レコメンデーションの総数取得
 
 @return レコメンデーション総数
 */

+(NSUInteger)recommendationsCount;

@end
