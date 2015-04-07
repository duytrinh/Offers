//
//  OffersPreviewCoupon.h
//  OffersKit
//
//  Created by Leonis&Co. on 2013/06/17.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import "OffersCoupon.h"

/**
 クーポンのプレビュー情報を管理します。基本的に継承元であるOffersCouponと同じメソッドを利用できますが、クーポン一覧、詳細読み込み、消し込みといったクーポンに対する操作は使用できません。

 プレビュー用のテンプレート情報はOffersCouponのtemplateIdおよびtemplateUpdateDateではなく、templateInfoを参照してください。
 */
@interface OffersPreviewCoupon : OffersCoupon

/**
 クーポンのテンプレート情報
 
 loadPreviewメソッド実行後に当該クーポンのテンプレート情報が格納されます。
 
 @see loadPreview
 
 */
@property(nonatomic,strong)     NSDictionary*       templateInfo;

/**
 クーポンプレビュー情報読み込み
 
 APIサーバと通信し、クーポンのプレビュー情報を取得します。
 
 @return メソッドの実行結果を表す真偽値です。真ならば成功、偽ならば失敗です。
 
 @warning このメソッドはAPIサーバと通信できない場合失敗します。
 */
-(BOOL)loadPreview;

@end
