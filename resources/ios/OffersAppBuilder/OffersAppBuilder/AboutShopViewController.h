//
//  AboutShopViewController.h
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 2/3/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AboutShopViewController : UIViewController<UIScrollViewDelegate, UIWebViewDelegate>{
    
}

@property (strong, nonatomic) IBOutlet UIWebView *webView;
@property (strong, nonatomic) IBOutlet UIToolbar *toolbar;
@property bool isFullScreen;
@property (strong, nonatomic) NSString *urlLink;

@property(nonatomic,weak) IBOutlet UIActivityIndicatorView*     nowloadingIndicatorView;

-(IBAction)webViewReload:(id)sender;
-(IBAction)webViewGoBack:(id)sender;
-(IBAction)webViewForWard:(id)sender;
-(IBAction)webViewFullScreen:(id)sender;

@end
