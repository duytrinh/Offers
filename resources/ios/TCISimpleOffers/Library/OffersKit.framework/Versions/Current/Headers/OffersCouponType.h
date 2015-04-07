//
//  OffersCouponType.h
//  OffersKit
//
//  Created by Leonis&Co. on 2013/05/31.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import "OffersObject.h"

typedef NS_ENUM( NSUInteger, OffersCouponTypeCode ) {
    OffersCouponTypeCodeUnknown,
    OffersCouponTypeCodeStripping,
    OffersCouponTypeCodeIdentification,
    OffersCouponTypeCodeStamp,
};

/**
 クーポンの種別を管理します。
*/
@interface OffersCouponType : OffersObject

/**
 クーポンタイプ名からクーポンタイプコードを取得します
 
 @param couponTypeName クーポンタイプ名
 @return 対応するクーポンタイプコード
 */
+(OffersCouponTypeCode)codeWithCouponTypeName:(NSString*)couponTypeName;

/**
 クーポンタイプコードからクーポンタイプ名を取得します
 
 @param couponTypeCode クーポンタイプコード
 @return 対応するクーポンタイプ名
 */
+(NSString*)couponTypeNameWithCouponTypeCode:(OffersCouponTypeCode)couponTypeCode;

@end
