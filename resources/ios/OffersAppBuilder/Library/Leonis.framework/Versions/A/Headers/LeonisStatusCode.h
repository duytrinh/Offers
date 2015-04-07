//
//  LeonisStatusCode.h
//  Leonis
//
//  Created by Leonis&Co. on 2014/05/30.
//  Copyright (c) 2014年 Leonis&Co. All rights reserved.
//

typedef NS_ENUM( NSUInteger, LeonisStatus ) {
    LeonisStatusSuccess                 = 0,
    LeonisStatusNotFound                = 100,
    LeonisStatusInvalidParameter        = 300,
    LeonisStatusAuthenticationFailured  = 400,
    LeonisStatusForbidden               = 403,
    LeonisStatusInvalidUser             = 408,
    LeonisStatusUnkownUser              = 420,
    LeonisStatusInternalServerError     = 500,
    LeonisStatusPleaseRetry             = 950,
    LeonisStatusServerResponseError     = -1,
    LeonisStatusLocalLibraryError       = -100,
};
