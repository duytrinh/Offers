//
//  Leonis.h
//  Leonis
//
//  Created by Leonis&Co. on 2013/11/01.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "LeonisTools.h"
#import "LeonisStatusCode.h"

@interface Leonis : NSObject

/**
 Leonis共通プラットフォームの共有インスタンスを取得します。
 
 @return Leonisプラットフォームの共有インスタンス
 */
+(Leonis*)sharedInstance;

/**
 Documentsパスを取得します。
 
 @return appのDocumentsパス
 */
+(NSString*)documentsPath;

/**
 APIサーバへの接続情報を設定します。
 
 @param hostName 接続するAPIサーバのホスト名
 @param httpProtocolString APIサーバに接続する際に使用するプロトコル。通常はhttpsとなります。
 @param apiVersion 使用するWeb APIのバージョン番号を文字列で指定します。
 @param applicationKey APIサーバ管理者から発行されたアプリケーションキーを指定します。
 */
-(void)setServerHost:(NSString*)hostName protocol:(NSString*)httpProtocolString apiVersion:(NSString*)versionString applicationKey:(NSString*)applicationKey;

/**
 APIサーバへのリクエストのタイムアウトを指定します。
 
 @param requestTimeoutInterval リクエストがタイムアウトするまでの時間を秒単位で指定します。
 */
-(void)setRequestTimeoutInterval:(double)requestTimeoutInterval;

/**
 アクセストークンのリクエスト
 
 サーバとの間で認証処理を行いアクセストークンを新規に取得します。
 
 @return メソッドの実行結果を示す結果コードです。
 @see authenticationToken
 */
-(LeonisStatus)requestAuthenticationToken;

/**
 アクセストークンのリクエスト
 
 サーバとの間で認証処理を行いアクセストークンをリクエストします。
 
 @param block APIサーバからのレスポンスを受信したあとで呼ばれる処理を記述します。
 @see authenticationToken
 */
-(void)requestAuthenticationTokenWithCallbackBlock:(void(^)(LeonisStatus code))block;

/**
 現在のアクセストークン確認
 
 現在登録されているAPIサーバへのアクセストークンを取得します。
 
 @return アクセストークン文字列
 @see requestAuthenticationToken requestAuthenticationTokenWithCallbackBlock:
 */
-(NSString*)authenticationToken;

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
 @param section　キーと値のペアを設定するセクション名を指定します
 */
-(void)setUserExtensionValue:(NSString*)value forKey:(NSString*)key withSection:(NSString*)section;

/**
 追加ユーザ情報の複数一括登録
 
 @param extentions 追加ユーザ情報のキーと値のペアで構成されたNSDictionaryをネストしたリソース名ごとのNSDictionaryを指定します
 e.g.
 @{
    @"group_name1": @{
        @"key1": @"value1",
        @"key2": @"value2",
    },
    @"group_name2": @{
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
-(void)setUserExtensionValue:(NSString*)value forKey:(NSString*)key withCallbackBlock:(void(^)(LeonisStatus code))block;

/**
 追加ユーザ情報の単一登録
 
 ユーザごとの追加情報を登録します。この情報は同じリソース名、同じキーが指定されると上書きされます。
 @param value 追加ユーザ情報の値を指定します。
 @param key 追加ユーザ情報の値を設定するキーを指定します。
 @param section　キーと値のペアを配置するセクション名を指定します
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 */
-(void)setUserExtensionValue:(NSString*)value forKey:(NSString*)key withSection:(NSString*)section withCallbackBlock:(void(^)(LeonisStatus code))block;

/**
 追加ユーザ情報の複数一括登録
 
 @param extensions 追加ユーザ情報のキーと値のペアで構成されたNSDictionaryをネストしたリソース名ごとのNSDictionaryを指定します。指定方法の例はsetUserExtensions:をご参照ください。
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 
 @return extensionsが正しくない場合、APIサーバとは通信せず偽が返されます。このときは、blockも実行されません。
 */
-(BOOL)setUserExtensions:(NSDictionary*)extentions withCallbackBlock:(void(^)( LeonisStatus code ))block;

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
 @param section　キーと値のペアを配置するセクション名を指定します
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 */
-(void)removeUserExtensionForKey:(NSString*)key withSection:(NSString*)section;

/**
 追加ユーザ情報の複数一括削除
 
 @param extensions 追加ユーザ情報のキーで構成されるNSArrayをネストしたリソース名ごとのNSDictionaryを指定します。
 e.g.
 @{
    @"group_name1": @[
        @"key1",
        @"key2"
    ],
    @"group_name2": @{
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
-(void)removeUserExtensionForKey:(NSString*)key withCallbackBlock:(void(^)(LeonisStatus code))block;

/**
 追加ユーザ情報の単一削除
 
 @param key 追加ユーザ情報の値を設定するキーを指定します。
 @param section　キーと値のペアを配置するセクション名を指定します
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 */
-(void)removeUserExtensionForKey:(NSString*)key withSection:(NSString*)section withCallbackBlock:(void(^)(LeonisStatus code))block;

/**
 追加ユーザ情報の複数一括削除
 
 @param extensions 追加ユーザ情報のキーで構成されるNSArrayをネストしたリソース名ごとのNSDictionaryを指定します。指定方法の例はremoveUserExtensions:をご参照ください。
 @param block APIサーバからレスポンスが返されたあとで実行する処理を記述したブロックを指定します。
 
 @return extensionsが正しくない場合、APIサーバとは通信せず偽が返されます。このときは、blockも実行されません。
 
 */
-(BOOL)removeUserExtensions:(NSDictionary*)extensions withCallbackBlock:(void(^)(LeonisStatus code))block;

@end