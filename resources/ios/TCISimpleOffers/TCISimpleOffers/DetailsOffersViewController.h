//
//  DetailsOffersViewController.h
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 2/6/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <OffersKit/OffersKit.h>
#import <StampKit/StampGestureView.h>

@interface DetailsOffersViewController : UIViewController<StampGestureViewDelegate>

@property(nonatomic,strong) OffersCoupon* coupon;

@property(nonatomic,weak) IBOutlet UILabel*                     titleLabel;
@property(nonatomic,weak) IBOutlet UILabel*                     availableSpanLabel;
@property(nonatomic,weak) IBOutlet UITextView*                  detailTextView;

@property(nonatomic,weak) IBOutlet UIImageView*                 couponImageView;

@property(nonatomic,weak) IBOutlet UIImageView*                 applyImageView;

@property(nonatomic,weak) IBOutlet UIActivityIndicatorView*     nowloadingIndicatorView;

@end
