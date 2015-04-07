//
//  OffersBlocking.h
//  OffersKit
//
//  Created by Leonis&Co. on 2013/06/03.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import "OffersObject.h"

/**
 APIサーバからの読み込みを拒否する情報を管理します。
 OffersBlockingクラスはシングルトンのため、sharedInstanceメソッドで共有インスタンスを取得してください。
 */
@interface OffersBlocking : OffersObject

/** サーバから最新の情報が読み込まれたかどうかを示します
 */
@property(nonatomic,assign) BOOL                loaded;

/** おしらせ機能が有効かどうかを示します
 */
@property(nonatomic,assign) BOOL                newsEnabled;

/** push通知が有効かどうかを示します。
 */
@property(nonatomic,assign) BOOL                pushEnabled;

/** 
 共有インスタンスの取得
 
 @return 共有インスタンス
 */
+(OffersBlocking*)sharedInstance;

/**
 現在サーバに登録されているブロッキング情報を読み込みます
 
 @return メソッドの実行結果を示す結果コードです。
 */
-(OffersKitStatusCode)loadBlocking;

/** 
 現在のジャンルのブロック情報一覧を取得します。
 
 @return ジャンルのブロック情報が格納されたNSArrayオブジェクト
 */
-(NSArray*)categoryBlockingList;

/**
 特定ジャンルのブロック
 
 特定ジャンルについて、IDを使ってブロックの設定／解除を行います
 
 @param categoryId ジャンルの管理IDを指定します。
 @param blocking YESを指定すると、categoryIdに対応するジャンルのブロックを有効にします。NOを指定すると、categoryIdに対応するジャンルのブロックを解除します。

 @return メソッドの実行結果を表す真偽値です。真ならば成功、偽ならば失敗です。
 */
-(BOOL)setCategoryBlockingWithCategoryId:(NSUInteger)categoryId blocking:(BOOL)blocking;

/**
 特定ジャンルのブロック
 
 特定ジャンルについて、ジャンル名を使ってブロックの設定／解除を行います

 @param categoryName ジャンル名を文字列で指定します。
 @param blocking YESを指定すると、categoryNameに対応するジャンルのブロックを有効にします。NOを指定すると、categoryNameに対応するジャンルのブロックを解除します。
 
 @return メソッドの実行結果を表す真偽値です。真ならば成功、偽ならば失敗です。
 */
-(BOOL)setCategoryBlockingWithCategoryName:(NSString*)categoryName blocking:(BOOL)blocking;

/**
 ジャンルブロックの一括変更
 
 ジャンルのブロック情報を指定します。
 
 @param blockingList ブロック情報を配列で設定します。配列にはジャンル名が格納されます。
 
 @return メソッドの実行結果を表す真偽値です。真なら成功、偽なら失敗です。
 */
-(BOOL)setCategoryBlocking:(NSArray*)blockingList;

/**
 現在の状態を保存
 
 現在の状態を保存し、APIサーバに送信します。
 
 @return メソッドの実行結果をあらわすステータスコード
 */
-(OffersKitStatusCode)save;

@end