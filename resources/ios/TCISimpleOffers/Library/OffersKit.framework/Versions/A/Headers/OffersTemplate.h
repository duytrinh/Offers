//
//  OffersTemplate.h
//  OffersKit
//
//  Created by Leonis&Co. on 2013/07/22.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import "OffersObject.h"

/**
 テンプレートの情報を管理します。
 */
@interface OffersTemplate : OffersObject

/**
 テンプレート情報の取り出し
 
 テンプレート情報を取得します。キャッシュが存在すればそれを使用します。
 
 updatedDateを確認し、キャッシュの更新が必要であればAPIサーバから最新のテンプレート情報をダウンロードして差し替えたものを返します。
 
 @param templateId テンプレート情報の管理ID。[OffersCoupon templateId]の戻り値を使用します。
 @param updatedDate テンプレート情報の更新チェック日時。[OffersCoupon templateUpdatedDate]の戻り値を使用します。
 
 @return メソッドが成功するとテンプレート情報が格納されたNSDictionaryが返されます。失敗するとnilが返されます。
 */
+(NSDictionary*)templateInfoForId:(NSUInteger)templateId withUpdateDate:(NSDate*)updatedDate;

@end
