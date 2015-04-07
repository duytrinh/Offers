//
//  OffersSampleCouponDetailViewController.m
//  OffersSampleApp
//
//  Created by Leonis&Co. on 2013/05/29.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import "OffersSampleCouponDetailViewController.h"

#import <OffersKit/OffersKit.h>
#import <OffersKit/OffersCategory.h>
#import <OffersKit/OffersTemplate.h>


#import <AudioToolbox/AudioServices.h>

#import <StampKit/StampKit.h>
#import <StampKit/StampContent.h>
#import <StampKit/StampGroup.h>
#import <StampKit/StampStamp.h>
#import <StampKit/StampImage.h>

@interface OffersSampleCouponDetailViewController ()

@end

@implementation OffersSampleCouponDetailViewController

@synthesize stampIsUsedView;
@synthesize mainDetailCouponView;
@synthesize showInputOffersPINView;
@synthesize switchOpenInputOffersPINView;
@synthesize closeOffersPINView;
@synthesize inputOffersPIN;

StampGestureView *stampGestureView;
int content_id;
NSArray *groups;

float heightOfWelcomeView;
BOOL doSwitchToSubView = false;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    [self.showInputOffersPINView setHidden:YES];
    
    self.navigationItem.title = self.coupon.title;
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemAction
                                                                                           target:self
                                                                                           action:@selector(actionButtonDown:)
                                              ];
    
    
    //スタンプView追加
    //init stampGestuewView
    // add Stamp view
    stampGestureView = [[StampGestureView alloc] init];
    stampGestureView.delegate = self;
    //stampGestureView.debug = true;
    stampGestureView.hidden = true;
    
    [self.mainDetailCouponView addSubview:stampGestureView];
    
    heightOfWelcomeView = self.welcomeView.frame.size.height; //get height of welcomeview for setting origin.y when it is hidden and showed with animation
    NSLog(@"welcome height= %f",heightOfWelcomeView);
}

-(void)viewWillAppear:(BOOL)animated
{
    [self.applyButton setHidden:YES];
    self.titleLabel.text        = self.coupon.title;
    self.detailTextView.text    = self.coupon.topDescription;
}

-(void)viewDidAppear:(BOOL)animated
{
    // 詳細情報を取得する
    // Get informations
    [self.coupon loadDetailWithCallbackBlock:^( OffersKitStatusCode code ) {
        if( code == OffersKitStatusSuccess ) {
            UIActivityIndicatorView* indicatorView = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
            indicatorView.center = self.couponImageView.center;
            dispatch_async(dispatch_get_main_queue(), ^{
                [self.view addSubview:indicatorView];
                [indicatorView startAnimating];
            });

            [self.coupon loadImagesWithCallbackBlock:^(BOOL result) {
                dispatch_async(dispatch_get_main_queue(), ^{
                    self.couponImageView.image = self.coupon.image;
                    
                    self.applyImageView.image = self.coupon.applySuccessImage;
                    
                    if( [self.coupon isUsed] == YES && [self.coupon hasApplySuccessImage] == YES ) {
                        self.applyImageView.image = self.coupon.applySuccessImage;
                    }
                    [indicatorView stopAnimating];
                });
            }];

            dispatch_async(dispatch_get_main_queue(), ^{
                NSDateFormatter* formatter = [[NSDateFormatter alloc] init];
                formatter.locale = [[NSLocale alloc] initWithLocaleIdentifier:@"ja_JP"];
                formatter.calendar = [[NSCalendar alloc] initWithCalendarIdentifier:NSGregorianCalendar];
                formatter.timeZone = [NSTimeZone timeZoneWithAbbreviation:@"JST"];
                formatter.dateFormat = @"yyyy年MM月dd日 HH:mm:ss";
                self.navigationItem.title = self.coupon.title;
                //self.titleLabel.text = [NSString stringWithFormat:@"(%@)%@", [OffersCategory nameForId:[OffersCategory idForName:self.coupon.category]], self.coupon.title];
                self.titleLabel.text = [NSString stringWithFormat:@"(%@)%@", self.coupon.category, self.coupon.title];
                
                //show text html at text view
                NSString *detailsHTMLText = [NSString stringWithFormat:@"%@\n(%@)",self.coupon.mainDescription, self.coupon.campaign];
                NSAttributedString *attributedString = [[NSAttributedString alloc] initWithData:[detailsHTMLText dataUsingEncoding:NSUnicodeStringEncoding] options:@{ NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType } documentAttributes:nil error:nil];
                self.detailTextView.attributedText = attributedString;
                
                /**
                 **  LABEL CONTENT
                 **     String format         :   利用期間      <=>     Time use
                 **     Delivery status       :   配信中        <=>    "With letter"
                 **     Coupon quantity       :   無限          <=>     Infinite
                 **
                 */
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
                        [self changeViewStyle];
                        
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
                        //スタンプ
                        //Stamp view
                        stampGestureView.hidden = false;
                        
                        [[StampKit sharedManager] contentWithBlock:[NSDictionary dictionaryWithObject:[NSNumber numberWithUnsignedInteger:self.coupon.couponId] forKey:@"coupon_id"]
                                                             block:^(NSDictionary *response, NSError *error) {
                                                                 
                                                                 StampContent *content = [response objectForKey:@"content"];
                                                                 content_id = content.content_id;
                                                                 
                                                                 [[StampKit sharedManager] groupsWithBlock:content_id block:^(NSDictionary *groups_response, NSError *groups_error) {
                                                                     
                                                                     [self.nowloadingIndicatorView stopAnimating];
                                                                     
                                                                     if(groups_error != nil){
                                                                         
                                                                         NSLog(@"groupsWithBlock.error:%@", groups_error);
                                                                         
                                                                         
                                                                         [self.nowloadingIndicatorView stopAnimating];
                                                                         
                                                                     }else{
                                                                         
                                                                         groups = [groups_response objectForKey:@"groups"];
                                                                         
                                                                         
                                                                         [self changeViewStyle];
                                                                         
                                                                         [self.nowloadingIndicatorView stopAnimating];
                                                                         
                                                                         
                                                                     }
                                                                     
                                                                     
                                                                 }];
                                                                 
                                                             }];
                        break;
                    }
                    default:
                    {
                        [self changeViewStyle];
                        [self.nowloadingIndicatorView stopAnimating];
                        break;
                    }
                }
                // 既読
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

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)applyButtonDown:(id)sender
{
    if( [self.coupon couponTypeCode] == OffersCouponTypeCodeIdentification ) {
        // 暗証番号入力させる
        // Input coupon type code identification
        /**
         **  MESSAGE ALERT CONTENT
         **     Title         :   暗証番号を入力してください      <=>     Please input PIN
         **
         */
        UIAlertView* alertView = [[UIAlertView alloc] initWithTitle:@"暗証番号を入力してください"
                                                            message:nil
                                                           delegate:self
                                                  cancelButtonTitle:@"OK"
                                                  otherButtonTitles:nil
                                  ];
        alertView.alertViewStyle = UIAlertViewStylePlainTextInput;
        [alertView show];
    }
    else {
        // 普通に処理する
        // Handling offers
        OffersKitStatusCode code = [self.coupon apply];
        /**
         **  MESSAGE ALERT CONTENT
         **     Title         :   結果                <=>     Result
         **     Message       :   正常に処理されました。 <=>     Process successful.
         **                       失敗しました。        <=>     Failed.
         **
         */
        UIAlertView* alertView = [[UIAlertView alloc] initWithTitle:[NSString stringWithFormat:@"結果(Code: %lu)", (unsigned long)code]
                                                            message:code == OffersKitStatusSuccess ? @"正常に処理されました。" : @"失敗しました。"
                                                           delegate:nil
                                                  cancelButtonTitle:@"OK"
                                                  otherButtonTitles:nil
                                  ];
        //[alertView show]; //no need show diaog for user can see
        //
        if( code == OffersKitStatusSuccess && [self.coupon hasApplySuccessImage] == YES ) {
            // self.coupon.applySuccessImageで画像の取得もしくは、self.coupon.applySuccessImageUrlで画像のURLを取得できます
            // Get images in self.coupon.applySuccessImage or get URL of images in self.coupon.applySuccessImageURL
            [self.applyImageView setImage:self.coupon.applySuccessImage];
        }
        
        //
        [self changeViewStyle];
    }
}

-(void)alertView:(UIAlertView*)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    /**
     **  Check title alert view equal with String
     **     String          :   暗証番号を入力してください  <=> Please input PIN
     */
    if( [alertView.title isEqualToString:@"暗証番号を入力してください"] == YES ) {
        dispatch_async(dispatch_get_main_queue(), ^{
            NSString* inputIdentification = [[alertView textFieldAtIndex:0] text];
            if( [inputIdentification length] > 0 && [self.coupon.secrets containsObject:inputIdentification] == YES ) {
                // 消し込み開始
                // Start
                OffersKitStatusCode code = [self.coupon applyWithSecret:inputIdentification];
                /**
                 **  MESSAGE ALERT CONTENT
                 **     Title         :   結果                <=>     Result
                 **     Message       :   正常に処理されました。 <=>     Process successful.
                 **                       失敗しました。        <=>     Failed.
                 **
                 */
                UIAlertView* alertView = [[UIAlertView alloc] initWithTitle:[NSString stringWithFormat:@"結果(Code: %lu)", (unsigned long)code]
                                                                    message:code == OffersKitStatusSuccess ? @"正常に処理されました。" : @"失敗しました。"
                                                                   delegate:nil
                                                          cancelButtonTitle:@"OK"
                                                          otherButtonTitles:nil
                                          ];
                //[alertView show]; //no need show dialog for user can see
                //
                if( code == OffersKitStatusSuccess && [self.coupon hasApplySuccessImage] == YES ) {
                    // self.coupon.applySuccessImageで画像の取得もしくは、self.coupon.applySuccessImageUrlで画像のURLを取得できます
                    // Get images in self.coupon.applySuccessImage or get URL of images in self.coupon.applySuccessImageURL
                    [self.applyImageView setImage:self.coupon.applySuccessImage];
                }

            }
            else {
                /**
                 **  MESSAGE ALERT CONTENT
                 **     Title         :   暗証番号エラー                <=>     PIN error
                 **     Message       :   暗証番号が一致しませんでした    <=>     PIN not match
                 **
                 */
                UIAlertView* alertView = [[UIAlertView alloc] initWithTitle:@"暗証番号エラー"
                                                                    message:@"暗証番号が一致しませんでした"
                                                                   delegate:nil
                                                          cancelButtonTitle:@"OK"
                                                          otherButtonTitles:nil
                                          ];
                //[alertView show]; //no need show diaog for user can see
            }
            //
            [self changeViewStyle];
        } );
    }
}

-(void)changeViewStyle
{
    //self.coupon.isUsed = false; //code for debug
    
    //[self.applyButton setEnabled:NO];
    [self.switchOpenInputOffersPINView setSelected:NO];
    
    [self.applyButton setSelected:NO];
    if( [self.coupon isUsed] == YES ) {
        self.applyButton.titleLabel.textAlignment = NSTextAlignmentCenter;
        /**
         **  APPLY BUTTON CONTENT
         **     Title         :   使用済                <=>     "Apply"
         **
         */
        [self.applyButton setTitle:[NSString stringWithFormat:@"%@",@"使用済"] forState:UIControlStateNormal];
        self.detailTextView.text = [NSString stringWithFormat:@"%@", self.coupon.usedDate];
        self.view.backgroundColor = [UIColor lightGrayColor];
        
        
        //スタンプ
        // Stamp
        if( self.coupon.couponTypeCode == OffersCouponTypeCodeStamp ){
            
            //グループ最初のスタンプを押印済みにする
            // Create a group Stamp exists
            StampGroup *group = [groups objectAtIndex:0];
            StampStamp *stamp = [[group stamps] objectAtIndex:0];
            
            NSMutableArray *histories = [NSMutableArray array];
            NSMutableDictionary *history = [NSMutableDictionary dictionary];
            
            CGSize size = stamp.stampImage.image.size;
            CGPoint toPoint = CGPointMake(self.view.frame.size.width/2, self.view.frame.size.height/2);
            
            [history setObject:[[NSNumber alloc] initWithInt:stamp.content_id] forKey:@"content_id"];
            [history setObject:[[NSNumber alloc] initWithInt:stamp.group_id] forKey:@"group_id"];
            [history setObject:[[NSNumber alloc] initWithInt:stamp.stamp_id] forKey:@"stamp_id"];
            [history setObject:[[NSNumber alloc] initWithInt:stamp.stampImage.image_id] forKey:@"image_id"];
            [history setObject:stamp.stampImage.url forKey:@"image_url"];
            [history setObject:[[NSNumber alloc] initWithFloat:toPoint.x] forKey:@"x"];
            [history setObject:[[NSNumber alloc] initWithFloat:toPoint.y] forKey:@"y"];
            [history setObject:[[NSNumber alloc] initWithFloat:size.width / 2] forKey:@"width"];
            [history setObject:[[NSNumber alloc] initWithFloat:size.height/ 2] forKey:@"height"];
            [history setObject:[[NSNumber alloc] initWithFloat:0] forKey:@"angle"];
            [history setObject:[[NSNumber alloc] initWithDouble:[self.coupon.usedDate timeIntervalSince1970]] forKey:@"stamped_at"];
            
            [histories addObject:history];
            
            [[StampKit sharedManager] setLocalHistoriesWithBlock:content_id histories:histories block:^(NSDictionary *response, NSError *error) {
                
                //ローカルDBより取得します
                // Get from local database
                NSArray * histories = [[StampKit sharedManager] localHistories:content_id];
                
                [stampGestureView setGroupsWithHistories:groups histories:histories];
            }];
            
        }
        
    }
    else if( [self.coupon isAvailable] == NO ) {
        self.applyButton.titleLabel.textAlignment = NSTextAlignmentCenter;
        /**
         **  APPLY BUTTON CONTENT
         **     Title         :   期間外                <=>     Out of period
         **
         */
        [self.applyButton setTitle:[NSString stringWithFormat:@"%@",@"期間外"] forState:UIControlStateNormal];
        self.view.backgroundColor = [UIColor lightGrayColor];
    }
    else {
        self.applyButton.titleLabel.textAlignment = NSTextAlignmentCenter;
        [self.applyButton setTitle:[NSString stringWithFormat:@"%@",self.coupon.couponType] forState:UIControlStateNormal];
            UIColor* backgroundColor = [UIColor whiteColor]; // parseできなかったときの色
            NSDictionary* templateInfo = [OffersTemplate templateInfoForId:self.coupon.templateId withUpdateDate:self.coupon.templateUpdateDate];
            if( templateInfo != nil ) {
                NSString* backgroundColorText = [[templateInfo  objectForKey:@"background"] objectForKey:@"color"];
                if( backgroundColorText != nil ) {
                    UIColor* tmpColor = getColorWithColorTable( backgroundColorText, [NSDictionary dictionaryWithObject:[UIColor colorWithRed:0.6f green:0.6f blue:1.0f alpha:1.0f] forKey:@"blue"] );
                    if( tmpColor != nil ) {
                        backgroundColor = tmpColor;
                    }
                }
            }
            self.view.backgroundColor = backgroundColor;
        
        //スタンプ
        // Stamp
        if( self.coupon.couponTypeCode == OffersCouponTypeCodeStamp ){
            
            [stampGestureView setGroupsWithHistories:groups histories:nil];
        }
    }
    
    //self.coupon.isUsed = true; //code for debug

    [self actionClose];

    if(self.coupon.isUsed)
    {
        [self.switchOpenInputOffersPINView setEnabled:false];
        [self.mainDetailCouponView addSubview:stampGestureView];
        [self.mainDetailCouponView bringSubviewToFront:stampGestureView];
        
        [self.mainDetailCouponView bringSubviewToFront:self.welcomeView];
        //[self.mainDetailCouponView bringSubviewToFront: switchOpenInputOffersPINView];//code for debug
        
        if(doSwitchToSubView == true )
        {
            [self performSelector:@selector(showWelcomeView) withObject:self afterDelay:0.5 ];
        }
    }
    
    //self.coupon.isUsed = false; //code for debug
}

-(void)actionButtonDown:(id)sender
{
    /**
     **  MESSAGE ALERT CONTENT
     **     Title         :   お店一覧               <=>     Shop list
     **
     */
    UIAlertView* alertView = [[UIAlertView alloc] initWithTitle:@"お店一覧"
                                                        message:[[self shopNameList] componentsJoinedByString:@"\n"]
                                                       delegate:nil
                                              cancelButtonTitle:@"OK"
                                              otherButtonTitles:nil
                              ];
    [alertView show];
}

-(NSArray*)shopNameList
{
    NSMutableArray* list = [NSMutableArray array];
    for( NSDictionary* shopInfo in [self.coupon shops] ) {
        [list addObject:[NSString stringWithFormat:@"%@ => %@", [shopInfo objectForKey:@"id"], [shopInfo objectForKey:@"name"]]];
    }
    return list;
}

-(void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context
{
    NSLog( @"hey!" );
}

//スタンプデリゲート
// Stamp delegate
-(void) gestureSuccess:(NSDictionary*)result{
    
    if(!self.coupon.isUsed){
        
        AudioServicesPlaySystemSound(kSystemSoundID_Vibrate);

        [self applyButtonDown:nil];
       
        [self switchOpenInputOffersPINChanged:nil];
    }
}

-(void)matchStamp:(NSDictionary *)result{
    
    AudioServicesPlaySystemSound(kSystemSoundID_Vibrate);
    
}

-(void)beforeStamp:(NSDictionary*)result{
    
    CGSize size = [[result objectForKey:@"size"] CGSizeValue];
    
    //オプション設定
    // Setting options
    CGSize toSize = CGSizeMake(size.width/2, size.height/2);
    CGPoint toPoint = CGPointMake(self.view.frame.size.width/2, self.view.frame.size.height/2);
    
    NSMutableDictionary *options = [NSMutableDictionary dictionary];
    [options setObject:[NSValue valueWithCGSize:toSize] forKey:@"size"];
    [options setObject:[NSValue valueWithCGPoint:toPoint] forKey:@"point"];
    
    [stampGestureView setStampedOption:options];
}


-(void)afterStamp:(NSDictionary*)result{
    
    if(!self.coupon.isUsed){
        [self applyButtonDown:nil];
        
        [self switchOpenInputOffersPINChanged:nil];
    }
}

- (IBAction) switchOpenInputOffersPINChanged:(id)sender
{
    doSwitchToSubView = true;
    
    if ([self.switchOpenInputOffersPINView isOn]) {
        
        //show subview view
        [self.showInputOffersPINView setHidden:NO];
        
        [self.inputOffersPIN setHidden:NO];
        [self.closeOffersPINView setHidden:NO];
        
        //スタンプ
        if(self.coupon.couponTypeCode == OffersCouponTypeCodeStamp)
        {
            [self.inputOffersPIN setHidden:YES];
        }
        
        //add this stampview for detect action add stamp is used
        [stampGestureView removeFromSuperview];
        [self.mainDetailCouponView addSubview:stampGestureView];
        
        [self.mainDetailCouponView bringSubviewToFront:self.closeOffersPINView];
        [self.mainDetailCouponView bringSubviewToFront:self.inputOffersPIN];
        
    } else {
        
        NSLog(@"The Switch is Off");
    }
    
    //[self showWelcomeView];
}

-(IBAction)closeInputOffersPIN:(id)sender{
    
    doSwitchToSubView = false;
    
    [self actionClose];
    
   }

-(void)actionClose{
    

    [self.showInputOffersPINView setHidden:YES]; //hide subview input PIN
    
    [self.switchOpenInputOffersPINView setOn:NO animated: YES ]; //reset switch OFF open subView for input PIN
    
    [self.inputOffersPIN setHidden:YES]; //hide button input PIN
    
    [self.closeOffersPINView setHidden:YES]; //hide button close sub view
    
    [stampGestureView removeFromSuperview]; //remove stampGetureView
}

-(IBAction)openDialogInputOffersPIN:(id)sender{
    
    [self applyButtonDown:nil];
}

-(IBAction)dimissButtonDown:(id)sender{
    NSLog(@"dimiss button down action");
    
    [self hideWelcomeView];
}

- (void)hideWelcomeView{
    
    NSLog(@"hide welcome view");
    
    [self.welcomeView setHidden:NO];
    
    UITabBar *tabBar = self.tabBarController.tabBar;
    
    [UIView animateWithDuration: 1.0
                     animations:^{
                         
                         CGRect tempFrame = self.welcomeView.frame;
                         tempFrame.origin.y = heightOfWelcomeView + tabBar.frame.size.height*2;//self.welcomeView.frame.size.height;
                         
                         NSLog(@"hide welcomeview position y = %f",tempFrame.origin.y);
                         
                         self.welcomeView.frame = tempFrame;
                         
                     }];
}

- (void)showWelcomeView {
    
    NSLog(@"show welcome view");
    
    [self.welcomeView setHidden:NO];
    
    UITabBar *tabBar = self.tabBarController.tabBar;
    
    [UIView animateWithDuration:1.0
                     animations:^{
                         
                         CGRect tempFrame = self.welcomeView.frame;
                         tempFrame.origin.y = self.welcomeView.frame.size.height + tabBar.frame.size.height*2+10;
                         
                         NSLog(@"show welcomeview position y = %f",tempFrame.origin.y);
                         
                         self.welcomeView.frame = tempFrame;

                     }];
}


@end
