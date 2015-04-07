//
//  OffersSampleCouponDetailViewController.h
//  OffersSampleApp
//
//  Created by Leonis&Co. on 2013/05/29.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import <UIKit/UIKit.h>

#import <OffersKit/OffersCoupon.h>

#import <StampKit/StampGestureView.h>

@interface OffersSampleCouponDetailViewController : UIViewController<StampGestureViewDelegate>

@property(nonatomic,strong) OffersCoupon* coupon;

@property(nonatomic,weak) IBOutlet UILabel*                     titleLabel;
@property(nonatomic,weak) IBOutlet UILabel*                     availableSpanLabel;
@property(nonatomic,weak) IBOutlet UITextView*                  detailTextView;
@property(nonatomic,weak) IBOutlet UIButton*                    applyButton;

@property(nonatomic,weak) IBOutlet UIButton*                    closeOffersPINView;

@property(nonatomic,weak) IBOutlet UIButton*                    inputOffersPIN;

@property(nonatomic,weak) IBOutlet UIButton*                    dimissButton;

@property(nonatomic,weak) IBOutlet UIImageView*                 couponImageView;

@property(nonatomic,weak) IBOutlet UIImageView*                 applyImageView;

@property(nonatomic,weak) IBOutlet UIActivityIndicatorView*     nowloadingIndicatorView;

@property(nonatomic,nonatomic) IBOutlet UIView*                      stampIsUsedView;

@property(nonatomic,nonatomic) IBOutlet UIView*                      mainDetailCouponView;

@property(nonatomic,nonatomic) IBOutlet UIView*                      showInputOffersPINView;

@property(nonatomic,nonatomic) IBOutlet UISwitch*                    switchOpenInputOffersPINView;

@property(nonatomic,nonatomic) IBOutlet UIView*                      welcomeView;

-(IBAction)applyButtonDown:(id)sender;
-(IBAction)switchOpenInputOffersPINChanged:(id)sender;
-(IBAction)closeInputOffersPIN:(id)sender;
-(IBAction)openDialogInputOffersPIN:(id)sender;

-(IBAction)dimissButtonDown:(id)sender;

-(void)actionClose;

@end
