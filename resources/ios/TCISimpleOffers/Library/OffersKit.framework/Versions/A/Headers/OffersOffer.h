//
//  OffersOffer.h
//  OffersKit
//
//  Created by Leonis&Co. on 2013/10/24.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import "OffersKit.h"

@interface OffersOffer : OffersObject

/**
 オファー数
 
 オファー数を返します
 
 @return 未読オファー数
 */
+(NSUInteger)offersCount;

/**
 未読オファー数
 
 未読のオファー数を返します
 
 @return 未読オファー数
 */
+(NSUInteger)unreadOffersCount;

/**
 オファー一覧
 
 APIサーバからオファー(クーポンおよびレコメンデーションの総称)の一覧を取得してOffersCouponあるいはOffersRecommendationインスタンスで構成される配列を返します。
 
 @param code メソッドの処理結果をあらわすステータスコードを格納するOffersKitStatusCode型の変数へのポインタ
 @return オファー情報の配列
 */
+(NSArray*)offersWithStatus:(OffersKitStatusCode*)code;

@end
