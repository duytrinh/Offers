//
//  AboutShopViewController.m
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 2/3/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import "AboutShopViewController.h"
#import "Constant.h"

@interface AboutShopViewController ()

@end

@implementation AboutShopViewController
@synthesize webView;


- (void)viewDidLoad {
    [super viewDidLoad];
    
    webView.delegate  = self;
//    UIStoryboard storyboard = [UIStoryboard storyboardWithName:@"MainStoryboard" bundle:nil];
//    NSString *identifier = @"SecondController"; // you need to set identifier in storyboard
//    SecondViewController *controller = [storyboard instantiateViewControllerWithIdentifier:identifier];
    NSString *restorationId = self.restorationIdentifier;
    NSLog(@"restoration id = %@", restorationId);
    
    if([restorationId isEqual:@"shopRestoration"])
    {
        self.navigationItem.title = @"Shops list";
        self.urlLink = URLSHOPLIST;
        
    } else if([restorationId isEqual:@"groupRestoration"]){
        
        self.navigationItem.title = @"Group list";
        self.urlLink = URLMYPAGE;//@"https://www.google.com/?gws_rd=ssl";
    }
    
    
    self.webView.scrollView.delegate = self;
    
    NSLog(@"url = %@",self.urlLink);
    
    // Do any additional setup after loading the view.
    NSURL *url = [NSURL URLWithString:self.urlLink];
    NSURLRequest *requestObj = [NSURLRequest requestWithURL:url];
    [webView loadRequest:requestObj];
    
    [self normalScreen];
}

- (void)webViewDidStartLoad:(UIWebView *)webView
{
    [self.nowloadingIndicatorView stopAnimating];
}

- (void)webViewDidFinishLoad:(UIWebView *)webView
{
    [self.nowloadingIndicatorView stopAnimating];
}

- (void)webViewDidFailLoadWithError:(NSError *)error
{
    [self.nowloadingIndicatorView stopAnimating];
}

-(void)viewWillAppear:(BOOL)animated{
    //[self hideTabBar];
    self.isFullScreen = false;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)webViewReload:(id)sender{
    NSLog(@"reload webview");
    
    [webView reload];
}

-(IBAction)webViewGoBack:(id)sender{
    NSLog(@"goback webview");
    
    [webView goBack];
}

-(IBAction)webViewForWard:(id)sender{
    NSLog(@"go forward webview");

    [webView goForward];
}

-(IBAction)webViewFullScreen:(id)sender{
    NSLog(@"fullscreen webview");
    
    if(self.isFullScreen){
        
        [self normalScreen];
    }
    else{
        
        [self fullScreen];
    }
}

- (void)normalScreen {
    
    self.isFullScreen = false;
    [self showTabBarWithAnimation];
}

- (void)fullScreen {
    
    self.isFullScreen = true;
    [self hideTabBarWithAnimation];
}

- (void)hideTabBarWithAnimation {
    
    UINavigationBar *navigationBar = self.navigationController.navigationBar;
    UITabBar *tabBar = self.tabBarController.tabBar;
    UIView *parent = tabBar.superview; // UILayoutContainerView
    UIView *content = [parent.subviews objectAtIndex:0];  // UITransitionView
    UIView *window = parent.superview;
    
    [UIView animateWithDuration: 0.5
        animations:^{
            //hide navigation bar
            CGRect navFrame = navigationBar.frame;
            navFrame.origin.y = -self.navigationController.navigationBar.frame.size.height;
            navigationBar.frame = navFrame;
                         
            //hide tab bar
            CGRect tabFrame = tabBar.frame;
            tabFrame.origin.y = CGRectGetMaxY(window.bounds);
            tabBar.frame = tabFrame;
                         
            //full screen webview
            content.frame = window.bounds;
            webView.frame = content.frame;
                         
            //hide tool bar
            self.toolbar.hidden = YES;
        }];
}

- (void)showTabBarWithAnimation {

    UINavigationBar *navigationBar = self.navigationController.navigationBar;
    UITabBar *tabBar = self.tabBarController.tabBar;
    UIView *parent = tabBar.superview; // UILayoutContainerView
    UIView *content = [parent.subviews objectAtIndex:0];  // UITransitionView
    UIView *window = parent.superview;
    
    [UIView animateWithDuration:0.5
        animations:^{
            //hide navigation bar
            CGRect navFrame = navigationBar.frame;
            navFrame.origin.y = self.navigationController.navigationBar.frame.size.height-25;
            navigationBar.frame = navFrame;
                         
            //show tab bar
            CGRect tabFrame = tabBar.frame;
            tabFrame.origin.y = CGRectGetMaxY(window.bounds) - CGRectGetHeight(tabBar.frame);
            tabBar.frame = tabFrame;
                         
            //set normal webview height
            //and show web view
            CGRect contentFrame = content.frame;
            contentFrame.size.height -= tabFrame.size.height;// - self.toolbar.frame.size.height;
            contentFrame.size.height -= self.toolbar.frame.size.height;
            contentFrame.size.height -= self.navigationController.navigationBar.frame.size.height+20;
            contentFrame.origin.y =  self.navigationController.navigationBar.frame.size.height +20;//set position is below navigation bar
            webView.frame = contentFrame;
                        
            //show tool bar
            self.toolbar.hidden = NO;
        }];
}

- (void)scrollViewWillBeginDragging:(UIScrollView *)scrollView {
    
    //doing something before user scroll web view
    NSLog(@"begin scroll");
    
}


- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView {
    
    //do something when user end scroll web view
    if (scrollView.contentOffset.y == 0) {
        // TOP
        [self normalScreen];
    }
    NSLog(@"end scroll");
    
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    //doing something when use scroll web view
    return;
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
