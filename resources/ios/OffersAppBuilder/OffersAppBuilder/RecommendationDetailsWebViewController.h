//
//  RecommendationDetailsWebViewController.h
//  OffersAppBuilder
//
//  Created by Vu Nguyen on 3/26/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RecommendationDetailsWebViewController : UIViewController

@property (strong, nonatomic) IBOutlet UIWebView *webView;
@property (strong, nonatomic) NSString *strURL;

@end
