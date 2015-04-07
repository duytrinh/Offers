//
//  OffersStampCard.h
//  OffersKit
//
//  Created by Kaneko Yoshio on 2014/10/06.
//  Copyright (c) 2014年 Leonis&Co. All rights reserved.
//

#import "OffersObject.h"
#import "OffersKitStatusCode.h"

@interface OffersStampCard : OffersObject



+(NSDictionary*)presentsWithStampCardId:(int)stamp_card_id withStatus:(OffersKitStatusCode*)code;

+(void)presentsWithStampCardId:(int)stamp_card_id withCallbackBlock:(void(^)(OffersKitStatusCode code, NSDictionary* presents ))block;

@end
