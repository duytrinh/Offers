//
//  OffersKitSyncType.h
//  OffersKit
//
//  Created by Leonis&Co. on 2013/08/26.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

typedef NS_ENUM(NSInteger, OffersKitSyncType ) {
    OffersKitSyncTypeAll              = 0xFF,                  // すべてを行う
    OffersKitSyncTypeUpdate           = 0x01,                  // 更新しかしない
    OffersKitSyncTypeDiff             = 0x02,                  // 新しいものをとってくる(非推奨)
    OffersKitSyncTypeSubmit           = 0x04,                  // ローカルにたまっている消し込み情報を送るだけ
} ;
