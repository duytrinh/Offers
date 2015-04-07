//
//  OffersKitStatusCode.h
//  OffersKit
//
//  Created by Leonis&Co. on 2013/06/14.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

typedef NS_ENUM(NSInteger, OffersKitStatusCode ) {
//typedef enum OffersKitStatusCodeEnum {
    // APIのステータスコード
    OffersKitStatusSuccess                      = 0,        // 成功しました
    OffersKitStatusNotFound                     = 100,      // 指定された条件ではサーバからデータを取得できませんでした
    OffersKitStatusNotPublished                 = 101,      // 未発行
    OffersKitStatusCampaignNotFound             = 151,      // 存在しないキャンペーン(コード間違い等)
    OffersKitStatusInvalidParameter             = 300,      // パラメータ不正
    OffersKitStatusAuthenticationFailed         = 400,      // 認証失敗
    OffersKitStatusIdentificationMismatch       = 410,      // 暗証番号不一致
    OffersKitStatusUserNotFound                 = 420,      // ユーザが見つかりません
    OffersKitStatusServerError                  = 500,      // サーバエラー
    OffersKitStatusUsedCoupon                   = 700,      // 使用済み
    OffersKitStatusAvailableLimitOver           = 701,      // 上限超過
    OffersKitStatusLockedCoupon                 = 702,      // ロックされていて使えないクーポン(再利用可能なクーポンで再利用可能になる時間になる前など)
    OffersKitStatusOutOfService                 = 710,      // 使用期間外
    OffersKitStatusInvalidCoupon                = 720,      // 無効化されたクーポン
    OffersKitStatusSuspendedCoupon              = 721,      // 一時停止中のクーポン
    OffersKitStatusUnavailableCampaign          = 751,      // 使用できないキャンペーン
    OffersKitStatusUsedCampaign                 = 752,      // 使用済みのキャンペーン
    OffersKitStatusOutOfServiceCampaign         = 753,      // 期間外のキャンペーン
    OffersKitStatusCampaignCouponRequestLockout = 754,      // キャンペーンクーポンのリクエスト失敗回数が多すぎてロックアウト
    OffersKitStatusInvalidatedCampaign          = 761,      // 失効したキャンペーン
    OffersKitStatusSuspendedCampaign            = 762,      // 一時停止中のキャンペーン
    OffersKitStatusNotModified                  = 800,      // 差分変更なし
    OffersKitStatusPleaseRetry                  = 950,      // 可能であれば、もう一度リクエスト
    OffersKitStatusUnknownError                 = 999,      // 未知のエラー
    // 内部SDKのエラー
    OffersKitStatusLocalDatabaseError           = -100,     // ローカルデータベースのエラー
    OffersKitStatusServerResponseError          = -200,     // サーバレスポンスエラー
    OffersKitStatusImplementError               = -256,     // アプリの実装エラー
    OffersKitStatusApplyFailed                  = -512,     // 消し込みエラー
    OffersKitStatusLocalLibraryError            = -1024,    // SDKのエラー
//} OffersKitStatusCode;
};
