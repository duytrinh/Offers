//
//  DetailsOffersViewController.m
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 2/6/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import "DetailsOffersViewController.h"
#import <StampKit/StampKit.h>
#import <StampKit/StampContent.h>

@interface DetailsOffersViewController ()

@end

@implementation DetailsOffersViewController

StampGestureView *stampGestureView;
int content_id;

- (void)viewDidLoad {
    [super viewDidLoad];
    
    // Do any additional setup after loading the view.
    
    // Add Stamp view
    stampGestureView = [[StampGestureView alloc] init];
    stampGestureView.delegate = self;
    //stampGestureView.debug = true;
    stampGestureView.hidden = true;
    
    [self.view addSubview:stampGestureView];
    
}

-(void)viewDidAppear:(BOOL)animated{
    // Get informations
    [self.coupon loadDetailWithCallbackBlock:^( OffersKitStatusCode code ) {
        if( code == OffersKitStatusSuccess ) {
            UIActivityIndicatorView* indicatorView = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
            indicatorView.center = self.couponImageView.center;
            dispatch_async(dispatch_get_main_queue(), ^{
                [self.view addSubview:indicatorView];
                [indicatorView startAnimating];
            });
            NSLog(@"%@ --- %d --- %lu",self.coupon.applySuccessImageUrl, self.coupon.hasApplySuccessImage, self.coupon.couponTypeCode);
            [self.coupon loadImagesWithCallbackBlock:^(BOOL result) {
                dispatch_async(dispatch_get_main_queue(), ^{
                    self.couponImageView.image = self.coupon.image;
//                    if( [self.coupon isUsed] == YES && [self.coupon hasApplySuccessImage] == YES ) {
//                        self.couponImageView.image = self.coupon.applySuccessImage;
//                    }
                    if (self.coupon.couponTypeCode == OffersCouponTypeCodeStamp) {
                        self.applyImageView.image = [UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"check-mark-red" ofType:@"png"]];
                    }
                    [indicatorView stopAnimating];
                });
            }];
            
            dispatch_async(dispatch_get_main_queue(), ^{
                NSDateFormatter* formatter = [[NSDateFormatter alloc] init];
                formatter.locale = [[NSLocale alloc] initWithLocaleIdentifier:@"ja_JP"];
                formatter.calendar = [[NSCalendar alloc] initWithCalendarIdentifier:NSCalendarIdentifierGregorian];
                formatter.timeZone = [NSTimeZone timeZoneWithAbbreviation:@"JST"];
                formatter.dateFormat = @"yyyy年MM月dd日 HH:mm:ss";
                self.navigationItem.title = self.coupon.title;
                //self.titleLabel.text = [NSString stringWithFormat:@"(%@)%@", [OffersCategory nameForId:[OffersCategory idForName:self.coupon.category]], self.coupon.title];
                self.titleLabel.text = [NSString stringWithFormat:@"(%@)%@", self.coupon.category, self.coupon.title];
                self.detailTextView.text = [NSString stringWithFormat:@"%@\n(%@)",self.coupon.mainDescription, self.coupon.campaign];
                self.availableSpanLabel.text = [NSString stringWithFormat:
                                                @"利用期間:%@ 〜 %@\n%@残：%@\n",
                                                [formatter stringFromDate:self.coupon.availableFromDate],
                                                [formatter stringFromDate:self.coupon.availableToDate],
                                                [self.coupon deliveryStatus] == OffersCouponDeliveryStatusDelivery ? @"(配信中)" : @"",
                                                [self.coupon hasAvailableLimit] == YES ? self.coupon.quantity : @"無限"
                                                ];
                
                switch (self.coupon.couponTypeCode) {
                    case OffersCouponTypeCodeIdentification:
                    {
                        NSString* secret = [self.coupon secretForApplied];
                        if( secret != nil ) {
                            NSUInteger secretLength = [secret length];
                            NSUInteger hideCount = secretLength - 3;
                            NSString* openSecret = [secret substringFromIndex:hideCount];
                            NSString* maskString = [@"" stringByPaddingToLength:hideCount
                                                                     withString:@"X"
                                                                startingAtIndex:0];
                            NSString* maskedSecret = [NSString stringWithFormat:@"%@%@", maskString, openSecret];
                            self.titleLabel.text = [NSString stringWithFormat:@"[%@]%@",  maskedSecret, self.titleLabel.text];
                        }
                        [self.nowloadingIndicatorView stopAnimating];
                        break;
                    }
                    case OffersCouponTypeCodeStamp:
                    {
                        
                        switch (self.coupon.couponTypeCode) {
                            case OffersCouponTypeCodeIdentification:
                            {
                                //Stamp view
                                stampGestureView.hidden = false;
                                [[StampKit sharedManager] contentWithBlock:[NSDictionary dictionaryWithObject:[NSNumber numberWithUnsignedInteger:self.coupon.couponId] forKey:@"coupon_id"]
                                                                     block:^(NSDictionary *response, NSError *error) {
                                                                         StampContent *content = [response objectForKey:@"content"];
                                                                         content_id = content.content_id;
                                                                         [[StampKit sharedManager] groupsWithBlock:content_id block:^(NSDictionary *groups_response, NSError *groups_error) {
                                                                             [self.nowloadingIndicatorView stopAnimating];
                                                                             if(groups_error != nil){
                                                                                 [self.nowloadingIndicatorView stopAnimating];
                                                                             }else{
                                                                                 [self.nowloadingIndicatorView stopAnimating];
                                                                             }
                                                                         }];
                                                                     }];
                                break;
                            }
                            default:
                            {
                                [self.nowloadingIndicatorView stopAnimating];
                                break;
                            }
                        }
                        // Already read
                        [self.coupon setAlreadyRead];
                    }
                    default:
                    {
                        [self.nowloadingIndicatorView stopAnimating];
                        break;
                    }
                }
                // Already read
                [self.coupon setAlreadyRead];
            });
        }
        else {
            dispatch_async(dispatch_get_main_queue(), ^{
                UIAlertView* alertView = [[UIAlertView alloc] initWithTitle:@"Error"
                                                                    message:@"Download Error"
                                                                   delegate:nil
                                                          cancelButtonTitle:@"OK"
                                                          otherButtonTitles:nil
                                          ];
                [alertView show];
            });
        }
    }
     ];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
